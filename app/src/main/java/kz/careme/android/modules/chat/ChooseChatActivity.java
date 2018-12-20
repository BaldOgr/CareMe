package kz.careme.android.modules.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.CareMeApp;
import kz.careme.android.R;
import kz.careme.android.model.Const;
import kz.careme.android.model.Kid;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.kids.KidsAdapter;
import kz.careme.android.modules.kids.OnKidClick;

public class ChooseChatActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_chat);
        ButterKnife.bind(this);
        initializeActionBar(true, "");


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        KidsAdapter adapter = new KidsAdapter(CareMeApp.getCareMeComponent().getProfiler().getKids(), new OnKidClick() {
            @Override
            public void onClick(Kid kid) {
                startActivity(ChatActivity.getParentIntent(ChooseChatActivity.this, Const.TYPE_PARENT, kid));
            }
        }, false);

        mRecyclerView.setAdapter(adapter);
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
