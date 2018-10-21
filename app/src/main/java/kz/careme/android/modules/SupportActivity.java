package kz.careme.android.modules;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.R;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_call_support)
    public void onClick(){
        startActivity(new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:+7(700)000-00-00")));
    }
}
