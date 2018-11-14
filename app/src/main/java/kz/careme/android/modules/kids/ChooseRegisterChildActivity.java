package kz.careme.android.modules.kids;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.R;
import kz.careme.android.model.Const;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.parent_main.MainActivity;
import kz.careme.android.modules.login.register.RegisterActivity;

public class ChooseRegisterChildActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_register_child);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.skip)
    void onClick() {
        startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @OnClick(R.id.register)
    void onClickRegister() {
        startActivity(new Intent(this, RegisterActivity.class)
                .putExtra(Const.ACTION, Const.ACTION_REGISTER_KID)
                .putExtra(Const.ACCOUNT_TYPE, Const.TYPE_CHILD));
    }
}
