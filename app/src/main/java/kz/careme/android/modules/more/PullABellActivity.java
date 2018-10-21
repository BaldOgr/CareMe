package kz.careme.android.modules.more;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.OnClick;
import kz.careme.android.R;
import kz.careme.android.modules.BaseActivity;

public class PullABellActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_abell);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }

    @OnClick(R.id.button_send_signal)
    public void onButtonClick() {
        Toast.makeText(this, "Sending signal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
