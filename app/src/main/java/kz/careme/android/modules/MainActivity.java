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
import android.widget.Toast;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.R;
import kz.careme.android.modules.chat.ChatActivity;
import kz.careme.android.modules.chat.ChatFragment;
import kz.careme.android.modules.kids.MyKidsFragment;
import kz.careme.android.modules.login.ChooseLoginActivity;
import kz.careme.android.modules.more.MoreFragment;
import kz.careme.android.modules.more.places.PlacesFragment;
import kz.careme.android.modules.settings.SettingsFragment;
import kz.careme.android.modules.subscribe.SubscribeFragment;

public class MainActivity extends BaseActivity implements ChangeBehaviorListener {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    @BindView(R.id.map)
    MapView mapView;

    private final Point TARGET_LOCATION = new Point(59.945933, 30.320045);

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private BottomSheetBehavior mBottomSheetBehavior;
    private Toast toast;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mapView.getMap().move(
                new CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);
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
                        startActivity(new Intent(MainActivity.this, ChatActivity.class));
                        return false;
                }
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                return true;
            }
        });
        mBottomNavigationView.setSelectedItemId(R.id.action_my_kids);
        toast = Toast.makeText(this, R.string.press_again_for_exit, Toast.LENGTH_SHORT);

    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    private void showFragment(String tag) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        {
            Fragment fragment1 = mFragmentManager.findFragmentByTag(PlacesFragment.TAG);
            if (fragment1 != null && fragment1.isVisible()) {
                mCurrentFragment = fragment1;
            }
        }
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
                    fragment = ChatFragment.newInstance(this);
                    transaction.add(R.id.bottom_sheet_behavior_content, fragment, tag);
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
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStack();
            return;
        }
        if (System.currentTimeMillis() - time <= 500) {
            super.onBackPressed();
        } else {
            time = System.currentTimeMillis();
            toast.show();
        }
    }

    @Override
    public void changeBehaviorPeekSize(int size) {
        mBottomSheetBehavior.setPeekHeight(size);
    }
}
