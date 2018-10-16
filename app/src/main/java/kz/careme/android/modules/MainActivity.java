package kz.careme.android.modules;

import android.content.Intent;
import android.os.Bundle;

import kz.careme.android.R;
import kz.careme.android.modules.login.ChooseLoginActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (profiler.getName() == null || profiler.getName().isEmpty()) {
            startActivity(new Intent(this, ChooseLoginActivity.class));
            finish();
        }
    }
}
