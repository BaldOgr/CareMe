package kz.careme.android.modules.chat;

import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.gson.Gson;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.R;
import kz.careme.android.model.Const;
import kz.careme.android.model.Kid;
import kz.careme.android.model.Message;
import kz.careme.android.modules.BaseActivity;

public class ChatActivity extends BaseActivity implements ChatView {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.smiles)
    ImageView mSmiles;

    @BindView(R.id.message)
    EditText mMessage;

    @BindView(R.id.chips)
    ChipGroup chipGroup;

    @BindView(R.id.sound_record)
    ImageView mRecordSound;

    @BindView(R.id.send_message)
    ImageView mSendMessage;

    @InjectPresenter
    ChatPresenter presenter;

    private Kid kid;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        initializeActionBar(true, "");

        String kidStr = getIntent().getStringExtra(Const.KID);
        kid = new Gson().fromJson(kidStr, Kid.class);
        if (kid.getSessionId() == null) {
            Log.e("ChatActivity", "Kid id can not be -1");
            finish();
        }

        Set<String> stringSet = getSharedPreferences(Const.SHARED_PREFERENCES, MODE_PRIVATE)
                .getStringSet(Const.CHIPS, null);

        if (stringSet != null) {
            for (String s : stringSet) {
                Chip chip = new Chip(this);
                chip.setText(s);
                chipGroup.addView(chip, new ChipGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }

        mMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mRecordSound.setVisibility(View.GONE);
                    mSendMessage.setVisibility(View.VISIBLE);
                    mRecordSound.animate()
                            .scaleX(0)
                            .scaleY(0)
                            .setDuration(100)
                            .start();
                    mSendMessage.animate()
                            .scaleX(1)
                            .scaleY(1)
                            .setDuration(100)
                            .start();
                } else {
                    mRecordSound.setVisibility(View.VISIBLE);
                    mSendMessage.setVisibility(View.GONE);
                    mRecordSound.animate()
                            .scaleX(1)
                            .scaleY(1)
                            .setDuration(100)
                            .start();
                    mSendMessage.animate()
                            .scaleX(0)
                            .scaleY(0)
                            .setDuration(100)
                            .start();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        presenter.getMessage(kid);
        adapter = new ChatAdapter();
    }

    @OnClick(R.id.send_message)
    public void onSendMessageClick() {
        Toast.makeText(this, "On SendMessage Click1", Toast.LENGTH_SHORT).show();
        String message = mMessage.getText().toString();
        mMessage.setText("");
        presenter.sendMessage(kid, message);
    }

    @OnClick(R.id.sound_record)
    public void onRecordSoundClick() {
        Toast.makeText(this, "On RecordSound Click1", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void messageAdded() {
        for (int i = 0; i < adapter.getItemCount(); i++) {
            Message message = adapter.getMessages().get(i);
            if (message.isLoading()) {
                message.setLoading(true);
                final int finalI = i;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemChanged(finalI);
                    }
                });
                break;
            }
        }
    }

    @Override
    public void messageLoaded(List<Message> messages) {
        adapter.setMessages(messages);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick(R.id.expand_chips)
    public void onExpandButtonClick(View v) {
        if (chipGroup.isSingleSelection()) {
            v.animate().rotation(180).setDuration(150).start();
            chipGroup.setSingleSelection(false);
        } else {
            v.animate().rotation(0).setDuration(150).start();
            chipGroup.setSingleSelection(true);
        }
    }
}
