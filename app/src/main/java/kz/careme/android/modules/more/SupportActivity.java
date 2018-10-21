package kz.careme.android.modules.more;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.R;
import kz.careme.android.modules.BaseActivity;

public class SupportActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        ButterKnife.bind(this);

        initializeActionBar(true, "");
    }

    @OnClick(R.id.button_call_support)
    public void onClick(){
        startActivity(new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:+7(700)000-00-00")));
    }
}
