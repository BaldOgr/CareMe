package kz.careme.android.modules.login.register;

import android.content.Intent;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.R;
import kz.careme.android.model.dialog_util.DialogUtil;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.login.login.LoginActivity;

@UiThread
public class RegisterActivity extends BaseActivity implements RegisterView {
    @BindView(R.id.layout_email)
    TextInputLayout mLayoutEmail;

    @BindView(R.id.layout_password)
    TextInputLayout mLayoutPassword;

    @BindView(R.id.edit_text_email)
    TextInputEditText mEditTextEmail;

    @BindView(R.id.edit_text_password)
    TextInputEditText mEditTextPassword;

    @InjectPresenter
    RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initializeActionBar(true, "");

        mEditTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mLayoutEmail.setError(null);
            }
        });
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
                DialogUtil.showDialog(this, getString(R.string.loading));
                presenter.reg(mEditTextEmail.getText().toString(), mEditTextPassword.getText().toString());
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {
        DialogUtil.closeDialog(this);
    }

    @Override
    public void showError(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLayoutEmail.setError(message);
            }
        });
    }

    @Override
    public void nextActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
