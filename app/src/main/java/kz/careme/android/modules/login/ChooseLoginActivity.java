package kz.careme.android.modules.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.R;
import kz.careme.android.model.Const;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.login.login.LoginActivity;
import kz.careme.android.modules.login.register.RegisterActivity;

public class ChooseLoginActivity extends BaseActivity {

    @BindView(R.id.sign_up)
    Button mRegButton;

    private int mAccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login);

        ButterKnife.bind(this);

        mAccountType = getIntent().getIntExtra(Const.ACCOUNT_TYPE, Const.TYPE_PARENT);

        if (mAccountType != Const.TYPE_PARENT) {
            mRegButton.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.login)
    public void onLoginClick(View v) {
        startActivity(new Intent(this, LoginActivity.class).putExtra(Const.ACCOUNT_TYPE, mAccountType));
    }

    @OnClick(R.id.sign_up)
    public void onRegisterClick(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
