package kz.careme.android.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.R;
import kz.careme.android.modules.chat.ChatActivity;
import kz.careme.android.modules.chat.ChatFragment;
import kz.careme.android.modules.kids.MyKidsFragment;
import kz.careme.android.modules.login.ChooseLoginActivity;
import kz.careme.android.modules.more.MoreFragment;
import kz.careme.android.modules.settings.SettingsFragment;
import kz.careme.android.modules.subscribe.SubscribeFragment;

public class MainActivity extends BaseActivity implements ChangeBehaviorListener {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private BottomSheetBehavior mBottomSheetBehavior;

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
        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_behavior));
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_my_kids:
                        showFragment(MyKidsFragment.TAG);
                        break;
                    case R.id.action_settings:
                        showFragment(SettingsFragment.TAG);
                        break;
                    case R.id.action_subscribe:
                        showFragment(SubscribeFragment.TAG);
                        break;
                    case R.id.action_more:
                        showFragment(MoreFragment.TAG);
                        break;
                    case R.id.action_chat:
                        showFragment(ChatFragment.TAG);
                        break;
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
                    fragment = SubscribeFragment.newInstance(this);
                    transaction.add(R.id.bottom_sheet_behavior_content, fragment, tag);
                    break;

                case SettingsFragment.TAG:
                    fragment = SettingsFragment.newInstance(this);
                    transaction.add(R.id.bottom_sheet_behavior_content, fragment, tag);
                    break;

                case MoreFragment.TAG:
                    fragment = MoreFragment.newInstance(this);
                    transaction.add(R.id.bottom_sheet_behavior_content, fragment, tag);
                    break;

                case ChatFragment.TAG:
                    startActivity(new Intent(this, ChatActivity.class));
                    break;

                default:
                    fragment = MyKidsFragment.newInstance(this);
                    transaction.add(R.id.bottom_sheet_behavior_content, fragment, tag);
                    break;
            }
        }
        if (mCurrentFragment != null) {
            transaction.hide(mCurrentFragment);
        }
        transaction
                .show(fragment)
                .commit();
        mCurrentFragment = fragment;
    }

    @Override
    public void changeBehaviorPeekSize(int size) {
        mBottomSheetBehavior.setPeekHeight(size);
    }
}
