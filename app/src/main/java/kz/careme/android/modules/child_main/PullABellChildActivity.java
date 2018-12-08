package kz.careme.android.modules.child_main;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kz.careme.android.R;

public class PullABellChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_abell_child);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Uri notify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Ringtone r = RingtoneManager.getRingtone(this, notify);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
