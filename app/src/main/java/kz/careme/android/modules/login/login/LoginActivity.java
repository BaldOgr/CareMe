package kz.careme.android.modules.login.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.R;
import kz.careme.android.model.Account;
import kz.careme.android.model.Const;
import kz.careme.android.model.dialog_util.DialogUtil;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.account_type.ChooseAccountTypeActivity;

public class LoginActivity extends BaseActivity implements LoginView, TextWatcher {

    @BindView(R.id.layout_email)
    TextInputLayout mLayoutEmail;

    @BindView(R.id.layout_password)
    TextInputLayout mLayoutPassword;

    @BindView(R.id.edit_text_email)
    TextInputEditText mEditTextEmail;

    @BindView(R.id.edit_text_password)
    TextInputEditText mEditTextPassword;

    @InjectPresenter
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);
        initializeActionBar(true, "");
        mEditTextEmail.addTextChangedListener(this);
        mEditTextPassword.addTextChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                DialogUtil.showDialog(this, "Loading");
                presenter.auth(mEditTextEmail.getText().toString(), mEditTextPassword.getText().toString());
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter = null;
    }

    @Override
    public void startActivity() {
        startActivity(new Intent(this, ChooseAccountTypeActivity.class));
    }

    @UiThread
    @Override
    public void closeDialog() {
        DialogUtil.closeDialog(LoginActivity.this);
    }

    @Override
    public void showIncorrectLogin() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLayoutEmail.setError(getString(R.string.login_error));
                mLayoutPassword.setError(getString(R.string.login_error));
            }
        });

    }

    @Override
    public void saveAccount(Account account) {
        getSharedPreferences("CareMe", Context.MODE_PRIVATE)
                .edit()
                .putString(Const.EMAIL, account.getEmail())
                .putString(Const.PASSWORD, account.getPassword())
                .apply();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mLayoutEmail.setError(null);
        mLayoutPassword.setError(null);
    }
}
