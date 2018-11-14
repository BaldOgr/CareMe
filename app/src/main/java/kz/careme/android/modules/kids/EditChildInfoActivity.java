package kz.careme.android.modules.kids;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.CareMeApp;
import kz.careme.android.R;
import kz.careme.android.model.Const;
import kz.careme.android.model.Kid;
import kz.careme.android.model.dialog_util.DialogUtil;
import kz.careme.android.model.event.KidEditedEvent;
import kz.careme.android.modules.BaseActivity;

public class EditChildInfoActivity extends BaseActivity implements EditChildInfoView{

    @InjectPresenter
    EditChildInfoPresenter presenter;

    @BindView(R.id.text_input_name)
    TextInputLayout mInputLayoutName;

    @BindView(R.id.text_input_last_name)
    TextInputLayout mInputLayoutLastName;

    @BindView(R.id.edit_text_name)
    TextInputEditText mEditTextName;

    @BindView(R.id.edit_text_last_name)
    TextInputEditText mEditTextLastName;

    private Kid kid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child_info);
        initializeActionBar(true, "");
        ButterKnife.bind(this);

        kid = new Gson().fromJson(getIntent().getStringExtra(Const.KID), Kid.class);
        if (kid.getId() <= 0) {
            Log.e("EditChildInfoActivity", "Child Id can not be 0");
            finish();
        }
        setFields(kid);
//        presenter.getKidInfo(kidId);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                DialogUtil.showDialog(this, getString(R.string.loading));
                String name = mEditTextName.getText().toString();
                String lastName = mEditTextLastName.getText().toString();
                presenter.onDonePressed(kid.getId(), name, lastName);
                break;

            case android.R.id.home:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void kidEdited() {
        DialogUtil.closeDialog(this);
        finish();
    }

    @Override
    public void setFields(Kid kid) {
        mEditTextName.setText(kid.getName());
        mEditTextLastName.setText(kid.getLastname());
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showError() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.error_repeat_later)
                .setCancelable(true)
                .show();

    }
}
