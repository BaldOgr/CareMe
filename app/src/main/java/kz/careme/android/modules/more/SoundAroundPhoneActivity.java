package kz.careme.android.modules.more;

import android.os.Bundle;
import android.view.MenuItem;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.R;
import kz.careme.android.modules.BaseActivity;


//TODO: реализовать принятие данных после записи на телефоне ребенка
public class SoundAroundPhoneActivity extends BaseActivity implements SoundAroundPhoneView {

    @InjectPresenter
    SoundAroundPhonePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_around_phone);

        ButterKnife.bind(this);

        initializeActionBar(true, "");
    }

    @OnClick(R.id.button_record_sound)
    public void onButtonClick() {
        presenter.startRecording(1L);
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

}
