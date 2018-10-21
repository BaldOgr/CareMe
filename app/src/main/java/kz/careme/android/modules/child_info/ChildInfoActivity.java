package kz.careme.android.modules.child_info;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.R;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.more.PullABellActivity;
import kz.careme.android.modules.more.SoundAroundPhoneActivity;

public class ChildInfoActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView mChildImage;

    @BindView(R.id.child_first_name)
    TextView mChildFirstName;

    @BindView(R.id.child_second_name)
    TextView mChildSecondName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_info);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.button_send_signal)
    public void onSendSignalClick() {
        startActivity(new Intent(this, PullABellActivity.class));
    }

    @OnClick(R.id.button_microphone)
    public void onSoundAroundPhoneClick() {
        startActivity(new Intent(this, SoundAroundPhoneActivity.class));
    }
}
