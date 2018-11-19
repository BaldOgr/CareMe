package kz.careme.android.modules.more;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.R;
import kz.careme.android.model.Const;
import kz.careme.android.modules.BaseActivity;


//TODO: реализовать принятие данных после записи на телефоне ребенка
public class SoundAroundPhoneActivity extends BaseActivity implements SoundAroundPhoneView {

    @InjectPresenter
    SoundAroundPhonePresenter presenter;

    private int kidId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_around_phone);

        ButterKnife.bind(this);

        initializeActionBar(true, "");

        kidId = getIntent().getIntExtra(Const.KID_ID, -1);
        if (kidId == -1) {
            throw new IllegalArgumentException("Kid ID cannot be -1");
        }
    }

    @OnClick(R.id.button_record_sound)
    public void onButtonClick() {
        presenter.startRecording(kidId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void recordingStarted() {
        Toast.makeText(this, "Recording started!", Toast.LENGTH_SHORT).show();
    }
}
