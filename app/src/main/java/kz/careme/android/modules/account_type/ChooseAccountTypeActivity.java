package kz.careme.android.modules.account_type;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.R;
import kz.careme.android.modules.BaseActivity;

import static kz.careme.android.model.Const.ACCOUNT_TYPE;
import static kz.careme.android.model.Const.TYPE_CHILD;
import static kz.careme.android.model.Const.TYPE_PARENT;

public class ChooseAccountTypeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account_type);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.parent)
    public void onParentButtonClick(View v) {
        startWriteCodeActivity(new Intent().putExtra(ACCOUNT_TYPE, TYPE_PARENT));
    }

    @OnClick(R.id.child)
    public void onChildButtonClick(View v) {
        startWriteCodeActivity(new Intent().putExtra(ACCOUNT_TYPE, TYPE_CHILD));
    }

    private void startWriteCodeActivity(Intent intent) {
        startActivity(intent.setComponent(new ComponentName(this, WriteCodeActivity.class)));
    }

}
