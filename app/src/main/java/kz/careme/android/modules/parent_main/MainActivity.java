package kz.careme.android.modules.parent_main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.ui_view.ViewProvider;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.BuildConfig;
import kz.careme.android.R;
import kz.careme.android.common.IconStyleGenerator;
import kz.careme.android.model.Kid;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.chat.ChatFragment;
import kz.careme.android.modules.chat.ChooseChatActivity;
import kz.careme.android.modules.kids.MyKidsFragment;
import kz.careme.android.modules.more.MoreFragment;
import kz.careme.android.modules.more.places.PlacesFragment;
import kz.careme.android.modules.settings.SettingsFragment;
import kz.careme.android.modules.subscribe.SubscribeFragment;

public class MainActivity extends BaseActivity implements ChangeBehaviorListener, MapActivityView, UserLocationObjectListener {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    @BindView(R.id.map)
    MapView mapView;

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private BottomSheetBehavior mBottomSheetBehavior;
    private Toast toast;
    private long time;
    private UserLocationLayer userLocationLayer;
    private Map<Kid, PlacemarkMapObject> kidsOnMap = new HashMap<>();
    private boolean doMoving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mapView.getMap().getUserLocationLayer().setEnabled(true);
        mapView.getMap().setDebugInfoEnabled(BuildConfig.DEBUG);
        mapView.getMap().addInputListener(new InputListener() {
            @Override
            public void onMapTap(com.yandex.mapkit.map.Map map, Point point) {
                doMoving = false;
            }

            @Override
            public void onMapLongTap(com.yandex.mapkit.map.Map map, Point point) {
                doMoving = false;
            }
        });
        userLocationLayer = mapView.getMap().getUserLocationLayer();
        userLocationLayer.setObjectListener(this);
        userLocationLayer.setAutoZoomEnabled(false);

        mFragmentManager = getSupportFragmentManager();
        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_behavior));
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                                startActivity(new Intent(MainActivity.this, ChooseChatActivity.class));
                                return false;
                        }
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        return true;
                    }
                });
        mBottomNavigationView.setSelectedItemId(R.id.action_my_kids);
        toast = Toast.makeText(this, R.string.press_again_for_exit, Toast.LENGTH_SHORT);

        CameraPosition cameraPosition = mapView.getMap().getCameraPosition();

        mapView.getMap().move(new CameraPosition(cameraPosition.getTarget(), 15,
                cameraPosition.getAzimuth(), cameraPosition.getTilt()));
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

    @OnClick(R.id.plus)
    public void onPlusClick(View view) {
        CameraPosition cameraPosition = mapView.getMap().getCameraPosition();
        mapView.getMap().move(new CameraPosition(cameraPosition.getTarget(), cameraPosition.getZoom() + 1, cameraPosition.getAzimuth(), cameraPosition.getTilt()),
                new Animation(Animation.Type.LINEAR, 0.1f), null);
    }

    @OnClick(R.id.minus)
    public void onMinusClick(View view) {
        CameraPosition cameraPosition = mapView.getMap().getCameraPosition();
        mapView.getMap().move(
                new CameraPosition(cameraPosition.getTarget(), cameraPosition.getZoom() - 1, cameraPosition.getAzimuth(), cameraPosition.getTilt()),
                new Animation(Animation.Type.LINEAR, 0.1f), null);
    }

    @OnClick(R.id.user_location)
    public void onUserLocationClick(View view) {
        doMoving = true;
        mapView.getMap().move(userLocationLayer.cameraPosition(),
                new Animation(Animation.Type.LINEAR, 0.5f), null);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
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
                    fragment = MyKidsFragment.newInstance(this, this);
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

    @Override
    public void setMarker(final Point point, final Kid kid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (kidsOnMap.containsKey(kid)) {
                    PlacemarkMapObject placemarkMapObject = kidsOnMap.get(kid);
                    mapView.getMap().getMapObjects().remove(placemarkMapObject);
                }
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setImageResource(R.drawable.ic_logogreen);
                PlacemarkMapObject placemarkMapObject = mapView.getMap().getMapObjects().addPlacemark(
                        point,
                        new ViewProvider(imageView),
                        IconStyleGenerator.getIconStyle()
                );
                kidsOnMap.put(kid, placemarkMapObject);
            }
        });
    }

    // convert to circle bitmap
    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    @Override
    public void onObjectAdded(UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float) (mapView.getWidth() * 0.5), (float) (mapView.getHeight() * 0.5)),
                new PointF((float) (mapView.getWidth() * 0.5), (float) (mapView.getHeight() * 0.83)));

        userLocationView.getPin().setIcon(ImageProvider.fromResource(
                this, R.drawable.ic_map_marker));
        CompositeIcon pinIcon = userLocationView.getPin().useCompositeIcon();

        pinIcon.setIcon(
                "icon",
                ImageProvider.fromResource(this, R.drawable.ic_map_marker),
                new IconStyle().setAnchor(new PointF(0f, 0f))
                        .setRotationType(RotationType.ROTATE)
                        .setZIndex(0f)
                        .setScale(1f)
        );

        pinIcon.setIcon(
                "pin",
                ImageProvider.fromResource(this, R.drawable.ic_map_marker),
                new IconStyle().setAnchor(new PointF(0.5f, 0.5f))
                        .setRotationType(RotationType.ROTATE)
                        .setZIndex(1f)
        );

    }

    @Override
    public void onObjectRemoved(UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(UserLocationView userLocationView, ObjectEvent objectEvent) {

    }
}
