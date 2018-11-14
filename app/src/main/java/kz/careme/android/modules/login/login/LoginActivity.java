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

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.CareMeApp;
import kz.careme.android.R;
import kz.careme.android.model.Account;
import kz.careme.android.model.Const;
import kz.careme.android.model.dialog_util.DialogUtil;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.child_main.ChildMainActivity;
import kz.careme.android.modules.account_type.WriteCodeActivity;
import kz.careme.android.modules.kids.ChooseRegisterChildActivity;

public class LoginActivity extends BaseActivity implements LoginView, TextWatcher {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

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

    private int mAccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);
        initializeActionBar(true, "");
        if (getIntent().getBooleanExtra(Const.ACTION_AUTH, false)) {
            Account account = CareMeApp.getCareMeComponent().getProfiler().getAccount();
            presenter.auth(account.getEmail(), account.getPassword());
            DialogUtil.showDialog(this, "Loading");
        }
        mEditTextEmail.addTextChangedListener(this);
        mEditTextPassword.addTextChangedListener(this);
        mAccountType = getIntent().getIntExtra(Const.ACCOUNT_TYPE, Const.TYPE_PARENT);
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
                String email = mEditTextEmail.getText().toString();
                String password = mEditTextPassword.getText().toString();
                if (!checkFields(email, password)) break;
                if (mAccountType == Const.TYPE_PARENT) {
                    presenter.auth(email, password);
                } else {
                    presenter.authKid(email, password);
                }
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkFields(String email, String password) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        boolean check = true;
        if (!matcher.find()) {
            mLayoutEmail.setError("Введите корректный email");
            check = false;
        }
        if (password.length() <= 5) {
            mLayoutPassword.setError("Пароль должен содержать не менее 6 символов");
            check = false;
        }
        return check;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter = null;
    }

    @Override
    public void startWriteCodeActivity() {
        if (mAccountType == Const.TYPE_PARENT) {
            startActivity(new Intent(this, ChooseRegisterChildActivity.class));
        } else {
            startActivity(new Intent(this, WriteCodeActivity.class).putExtra(Const.ACCOUNT_TYPE, Const.TYPE_CHILD));
        }
    }

    @Override
    public void startChildMainActivity(int parentId, int childId) {
        getSharedPreferences("CareMe", MODE_PRIVATE)
                .edit()
                .putInt(Const.PARENT_ID, parentId)
                .putInt(Const.CHILD_ID, childId)
                .apply();
        startActivity(new Intent(this, ChildMainActivity.class));
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
                .putInt(Const.ACCOUNT_TYPE, account.getAccountType())
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
