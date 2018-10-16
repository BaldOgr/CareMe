package kz.careme.android.modules.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.R;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.login.login.LoginActivity;
import kz.careme.android.modules.login.register.RegisterActivity;

public class ChooseLoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.login)
    public void onLoginClick(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.sign_up)
    public void onRegisterClick(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
