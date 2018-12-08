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

public class PullABellActivity extends BaseActivity implements PullABellView {

    @InjectPresenter
    PullABellPresenter presenter;

    private String kidSessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_abell);
        ButterKnife.bind(this);
        initializeActionBar(true, "");

        kidSessionId = getIntent().getStringExtra(Const.KID_SESSION_ID);

        if (kidSessionId == null || kidSessionId.isEmpty()) {
            throw new IllegalArgumentException("kid_session_id can not be null");
        }
    }

    @OnClick(R.id.button_send_signal)
    public void onButtonClick() {
        presenter.pullABell(kidSessionId);
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
    public void signalSended() {

    }
}
