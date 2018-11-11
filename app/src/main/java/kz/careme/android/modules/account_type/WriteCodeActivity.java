package kz.careme.android.modules.account_type;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.R;
import kz.careme.android.model.Const;
import kz.careme.android.model.dialog_util.DialogUtil;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.ChildMainActivity;
import kz.careme.android.modules.MainActivity;

import static kz.careme.android.model.Const.ACCOUNT_TYPE;

public class WriteCodeActivity extends BaseActivity implements WriteCodeView {

    @BindView(R.id.first_message)
    TextView mFirstMessage;

    @BindView(R.id.second_message)
    TextView mSecondMessage;

    @BindView(R.id.code_layout)
    LinearLayout mCodeLayout;

    @InjectPresenter
    WriteCodePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_code);
        ButterKnife.bind(this);
        initializeActionBar(true, "");
        int mAccountType = getIntent().getIntExtra(ACCOUNT_TYPE, Const.TYPE_CHILD);
        switch (mAccountType) {
            case Const.TYPE_CHILD:
                changeTextToChild();
                addEditTextListeners();
                break;
            case Const.TYPE_PARENT:
                DialogUtil.showDialog(this, "Loading");
                changeTextToParent();
                generateCode(getIntent().getIntExtra(Const.KID_ID, -1));
                break;
        }
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
//                startActivity(new Intent(this, MainActivity.class));
                break;

            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeTextToChild() {
        mFirstMessage.setText(R.string.text_sync_child_phone_with_parent);
        mSecondMessage.setText(R.string.text_show_child_code);
    }

    private void generateCode(int kidId) {
        presenter.getParentCode(kidId);
    }

    private void changeTextToParent() {
        mFirstMessage.setText(R.string.text_sync_parent_phone_with_child);
        mSecondMessage.setText(R.string.text_enter_child_code);
    }

    private void addEditTextListeners() {
        for (int i = 0; i < mCodeLayout.getChildCount(); i++) {
            final int finalI = i;
            ((EditText) mCodeLayout.getChildAt(i)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count == 0 && finalI != 0) {
                        mCodeLayout.getChildAt(finalI - 1).requestFocus();
                        return;
                    }
                    if (count == 1 && finalI != mCodeLayout.getChildCount() - 1) {
                        mCodeLayout.getChildAt(finalI + 1).requestFocus();
                        return;
                    }
                    if (count == 1 && finalI == mCodeLayout.getChildCount() - 1) {
                        requestCode();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    private void requestCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < mCodeLayout.getChildCount(); i++) {
            code.append(
                    ((EditText) mCodeLayout.getChildAt(i)).getText().toString()
            );
        }
        requestCode(code.toString());
    }

    private void requestCode(String code) {
        DialogUtil.showDialog(this, "Loading");
        presenter.activateCode(code);
    }

    @Override
    public void setCode(final int code) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String codeStr = String.valueOf(code);
                for (int i = 0; i < 4; i++) {
                    ((EditText) mCodeLayout.getChildAt(i)).setText(String.valueOf(codeStr.charAt(i)));
                    mCodeLayout.getChildAt(i).setEnabled(false);
                }
                dismissDialog();
            }
        });
    }

    @Override
    public void dismissDialog() {
        DialogUtil.closeDialog(this);
    }

    @Override
    public void showError(String error) {
        new AlertDialog.Builder(this)
                .setTitle(error)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ;
                    }
                }).show();
    }

    @Override
    public void onCodeActivated(int parentId, int childId) {
        getSharedPreferences("CareMe", MODE_PRIVATE)
                .edit()
                .putInt(Const.PARENT_ID, parentId)
                .putInt(Const.CHILD_ID, childId)
                .apply();
        startActivity(new Intent(this, ChildMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
}
