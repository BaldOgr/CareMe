package kz.careme.android.modules.preloader;

import android.content.Intent;
import android.os.Bundle;

import kz.careme.android.R;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.login.ChooseLoginActivity;

public class PreloaderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(PreloaderActivity.this, ChooseLoginActivity.class));
                finish();
            }
        }).start();
    }
}
