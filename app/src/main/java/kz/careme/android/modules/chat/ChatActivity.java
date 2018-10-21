package kz.careme.android.modules.chat;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.resources.TextAppearance;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.R;
import kz.careme.android.modules.BaseActivity;

public class ChatActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.smiles)
    ImageView mSmiles;

    @BindView(R.id.message)
    EditText mMessage;

    @BindView(R.id.chips)
    ChipGroup chipGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        for (int i = 0; i < 10; i++) {
            Chip chip = new Chip(this);
            chip.setText("asdadsad");
            chipGroup.addView(chip, i, new ChipGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

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
