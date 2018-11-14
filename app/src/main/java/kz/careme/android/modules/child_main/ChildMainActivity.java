package kz.careme.android.modules.child_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.R;
import kz.careme.android.model.Const;
import kz.careme.android.modules.chat.ChatActivity;

public class ChildMainActivity extends AppCompatActivity implements ChildMainActivityView {

    @InjectPresenter
    ChildMainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.alarm)
    public void onAlarmClick() {
        Toast.makeText(this, "Alarm Click!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fab_chat)
    public void onChatClick() {
        int parentId = getSharedPreferences(Const.SHARED_PREFERENCES, MODE_PRIVATE)
                .getInt(Const.PARENT_ID, -1);
        startActivity(
                new Intent(this, ChatActivity.class)
                        .putExtra(Const.ACCOUNT_TYPE, Const.TYPE_CHILD)
                        .putExtra(Const.PARENT_ID, parentId)
        );
    }
}
