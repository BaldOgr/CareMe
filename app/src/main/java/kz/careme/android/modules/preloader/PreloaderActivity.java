package kz.careme.android.modules.preloader;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import com.arellomobile.mvp.presenter.InjectPresenter;

import kz.careme.android.CareMeApp;
import kz.careme.android.R;
import kz.careme.android.model.Const;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.ChildMainActivity;
import kz.careme.android.modules.MainActivity;
import kz.careme.android.modules.account_type.ChooseAccountTypeActivity;
import kz.careme.android.modules.login.ChooseLoginActivity;
import kz.careme.android.modules.service.MyService;

public class PreloaderActivity extends BaseActivity implements PreloaderView {

    @InjectPresenter
    PreloaderPresenter preloaderPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader);

    }

    @Override
    protected void onResume() {
        super.onResume();
        preloaderPresenter.register();

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
            startActivity(new Intent(this, ChooseAccountTypeActivity.class));
            preloaderPresenter.unsubscribe();
            finish();
        }
    }

    @Override
    public void startChooseAccountTypeActivity() {
        startActivity(new Intent(this, ChooseAccountTypeActivity.class));
        finish();
        preloaderPresenter.unsubscribe();
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        preloaderPresenter.unsubscribe();
    }

    @Override
    public void startChildMainActivity() {
        startActivity(new Intent(this, ChildMainActivity.class));
        finish();
        preloaderPresenter.unsubscribe();
    }
}
