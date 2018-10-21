package kz.careme.android.modules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.R;
import kz.careme.android.modules.chat.ChatActivity;
import kz.careme.android.modules.chat.ChatFragment;

public class ChildMainActivity extends AppCompatActivity {

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
        startActivity(new Intent(this, ChatActivity.class));
    }
}
