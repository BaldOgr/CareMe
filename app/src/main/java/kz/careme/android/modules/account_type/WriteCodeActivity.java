package kz.careme.android.modules.account_type;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.R;
import kz.careme.android.model.Const;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.MainActivity;

import static kz.careme.android.model.Const.ACCOUNT_TYPE;

public class WriteCodeActivity extends BaseActivity {

    @BindView(R.id.first_message)
    TextView mFirstMessage;

    @BindView(R.id.second_message)
    TextView mSecondMessage;

    @BindView(R.id.code_layout)
    LinearLayout mCodeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_code);
        ButterKnife.bind(this);
        initializeActionBar(true, "");
        int mAccountType = getIntent().getIntExtra(ACCOUNT_TYPE, 1);
        switch (mAccountType) {
            case Const.TYPE_PARENT:
                changeTextToParent();
                addEditTextListeners();
                break;
            case Const.TYPE_CHILD:
                changeTextToChild();
                generateCode();
                break;
        }
    }

    private void changeTextToChild() {
        mFirstMessage.setText(R.string.text_sync_child_phone_with_parent);
        mSecondMessage.setText(R.string.text_show_child_code);
    }

    @SuppressLint("SetTextI18n")
    private void generateCode() {
        for (int i = 0; i < mCodeLayout.getChildCount(); i++) {
            ((EditText) mCodeLayout.getChildAt(i)).setText("" + (int) (Math.random() * 10));
            mCodeLayout.getChildAt(i).setEnabled(false);
        }
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
        Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
    }
}
