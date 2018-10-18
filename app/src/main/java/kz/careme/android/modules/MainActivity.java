package kz.careme.android.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.R;
import kz.careme.android.modules.kids.MyKidsFragment;
import kz.careme.android.modules.login.ChooseLoginActivity;
import kz.careme.android.modules.subscribe.SubscribeFragment;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (profiler.getName() == null || profiler.getName().isEmpty()) {
            startActivity(new Intent(this, ChooseLoginActivity.class));
            finish();
            return;
        }
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_my_kids:
                        showFragment(MyKidsFragment.TAG);
                        break;
                    case R.id.action_subscribe:
                        showFragment(SubscribeFragment.TAG);
                }
                return true;
            }
        });
        mBottomNavigationView.setSelectedItemId(R.id.action_my_kids);

    }

    private void showFragment(String tag) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (mCurrentFragment == fragment && fragment != null) {
            return;
        }
        if (fragment == null) {
            switch (tag) {
                case SubscribeFragment.TAG:
                    fragment = SubscribeFragment.newInstance();
                    transaction.add(R.id.bottom_sheet_behavior_content, fragment, tag);
                    break;
                default:
                    fragment = MyKidsFragment.newInstance();
                    transaction.add(R.id.bottom_sheet_behavior_content, fragment, tag);
                    break;
            }
        }
        if (mCurrentFragment != null) {
            transaction.hide(mCurrentFragment);
        }
        transaction.show(fragment).commit();
        mCurrentFragment = fragment;
    }
}
