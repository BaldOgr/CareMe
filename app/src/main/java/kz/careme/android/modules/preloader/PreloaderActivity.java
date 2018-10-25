package kz.careme.android.modules.preloader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;

import kz.careme.android.CareMeApp;
import kz.careme.android.R;
import kz.careme.android.model.Const;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.login.ChooseLoginActivity;
import kz.careme.android.modules.service.MyService;

public class PreloaderActivity extends BaseActivity implements PreloaderView {

    @InjectPresenter
    PreloaderPresenter preloaderPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader);

        SharedPreferences preferences = getSharedPreferences(Const.SHARED_PREFERENCES, MODE_PRIVATE);
        String email = preferences.getString(Const.EMAIL, "");
        String password = preferences.getString(Const.PASSWORD, "");
        int accountType = preferences.getInt(Const.ACCOUNT_TYPE, Const.TYPE_PARENT);

        if (!email.isEmpty() && !password.isEmpty()) {
            if (accountType == Const.TYPE_PARENT) {
                preloaderPresenter.auth(email, password);
            } else {
                preloaderPresenter.authKid(email, password);
            }
        } else {
            startActivity(new Intent(this, ChooseLoginActivity.class));
            finish();
        }
    }

    @Override
    public void startLoginActivity() {
        startActivity(new Intent(this, ChooseLoginActivity.class));
    }
}
