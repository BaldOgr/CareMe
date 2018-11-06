package kz.careme.android.modules.child_info;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.R;
import kz.careme.android.model.Const;
import kz.careme.android.model.Kid;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.chat.ChatActivity;
import kz.careme.android.modules.kids.EditChildInfoActivity;
import kz.careme.android.modules.more.PullABellActivity;
import kz.careme.android.modules.more.SoundAroundPhoneActivity;

public class ChildInfoActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView mChildImage;

    @BindView(R.id.child_first_name)
    TextView mChildFirstName;

    @BindView(R.id.child_second_name)
    TextView mChildSecondName;

    private Kid kid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_info);
        ButterKnife.bind(this);

        initializeActionBar(true, "");
        String kidStr = getIntent().getStringExtra(Const.KID);
        kid = new Gson().fromJson(kidStr, Kid.class);

        if (kid.getAvatar() != null && !kid.getAvatar().isEmpty())
            Picasso.get()
                    .load(kid.getAvatar())
                    .into(mChildImage);
        mChildFirstName.setText(kid.getName());
        mChildSecondName.setText(kid.getLastname());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.child_info, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_edit:
                startActivity(new Intent(this, EditChildInfoActivity.class).putExtra(Const.KID, new Gson().toJson(kid)));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.button_a_to_b)
    public void onAToBClick() {
        startActivity(new Intent(this, PullABellActivity.class).putExtra(Const.KID_ID, kid.getId()));
    }

    @OnClick(R.id.button_chat)
    public void onChatClick() {
        startActivity(new Intent(this, ChatActivity.class).putExtra(Const.KID_ID, kid.getId()));
    }

    @OnClick(R.id.button_send_signal)
    public void onSendSignalClick() {
        startActivity(new Intent(this, PullABellActivity.class).putExtra(Const.KID_ID, kid.getId()));
    }

    @OnClick(R.id.button_microphone)
    public void onSoundAroundPhoneClick() {
        startActivity(new Intent(this, SoundAroundPhoneActivity.class).putExtra(Const.KID_ID, kid.getId()));
    }
}
