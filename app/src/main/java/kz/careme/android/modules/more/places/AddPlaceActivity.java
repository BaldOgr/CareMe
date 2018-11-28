package kz.careme.android.modules.more.places;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.search.Response;
import com.yandex.mapkit.search.SearchManager;
import com.yandex.mapkit.search.SearchManagerType;
import com.yandex.mapkit.search.SearchOptions;
import com.yandex.mapkit.search.Session;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.R;
import kz.careme.android.model.Const;
import kz.careme.android.model.dialog_util.DialogUtil;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.parent_main.MainActivity;

public class AddPlaceActivity extends BaseActivity implements AddPlaceView {

    @BindView(R.id.place_address)
    TextView mPlaceAddress;

    @BindView(R.id.place_name)
    TextView mPlaceName;

    @BindView(R.id.seekbar)
    SeekBar mSeekBar;

    @BindView(R.id.seekbar_value)
    TextView mSeekBarValue;

    @BindView(R.id.map)
    MapView mapView;

    @InjectPresenter
    AddPlacePresenter presenter;
    private PlacemarkMapObject placeMark;
    private int kidId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        ButterKnife.bind(this);
        initializeActionBar(true, "");

//        mapView.getMap().getUserLocationLayer().setEnabled(true);
//        mapView.getMap().getUserLocationLayer().setAutoZoomEnabled(true);

        kidId = getIntent().getIntExtra(Const.KID_ID, 1);
        if (kidId == -1) {
            throw new IllegalArgumentException("KidId cannot be -1");
        }

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSeekBarValue.setText(getString(R.string.seekbar_value, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBar.setProgress(200);
        initMapView();
    }

    private void initMapView() {
//        MapKitFactory.getInstance().createLocationManager().requestSingleUpdate(new LocationListener() {
//            @Override
//            public void onLocationUpdated(Location location) {
//                mapView.getMap().move(new CameraPosition(location.getPosition(), location.getAccuracy().floatValue(), 0, 0));
//                mapView.getMap().getMapObjects().clear();
//                mapView.getMap().getMapObjects().addPlacemark(location.getPosition(),
//                        ImageProvider.fromBitmap(MainActivity.drawableToBitmap(getResources().getDrawable(R.drawable.ic_map_marker))));
//            }
//
//            @Override
//            public void onLocationStatusUpdated(LocationStatus locationStatus) {
//            }
//        });

        mapView.getMap().getMapObjects().clear();
        mapView.getMap().addTapListener(new GeoObjectTapListener() {
            @Override
            public boolean onObjectTap(GeoObjectTapEvent geoObjectTapEvent) {
                geoObjectTapEvent.getGeoObject().getGeometry().get(0).getPoint();
                return false;
            }
        });
        final Geocoder geocoder = new Geocoder(AddPlaceActivity.this);
        mapView.getMap().addInputListener(new InputListener() {
            @Override
            public void onMapTap(Map map, Point point) {
            }

            @Override
            public void onMapLongTap(final Map map, final Point point) {
                map.getMapObjects().clear();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<Address> fromLocation = geocoder.getFromLocation(point.getLatitude(), point.getLongitude(), 1);
                            if (!fromLocation.isEmpty()) {
                                renameAddress(fromLocation.get(0));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                PlacemarkMapObject placemarkMapObject = map.getMapObjects().addPlacemark(point,
                        ImageProvider.fromBitmap(MainActivity.drawableToBitmap(getResources().getDrawable(R.drawable.ic_map_marker))));
                AddPlaceActivity.this.placeMark = placemarkMapObject;
            }
        });

    }

    @OnClick(R.id.place_name)
    public void onPlaceNameClick(View v) {
        final EditText placeName = new EditText(AddPlaceActivity.this);
        new AlertDialog.Builder(AddPlaceActivity.this)
                .setTitle(R.string.place_name)
                .setView(placeName)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPlaceName.setText(placeName.getText());
                    }
                })
                .show();
    }

    private void renameAddress(final Address address) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPlaceAddress.setText(address.getAddressLine(0));
            }
        });
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                if (mPlaceName.getText().toString().isEmpty()) {
                    Toast.makeText(this, R.string.need_to_set_place_name, Toast.LENGTH_SHORT).show();
                    onPlaceNameClick(mPlaceName);
                    return super.onOptionsItemSelected(item);
                }
                DialogUtil.showDialog(this, getString(R.string.loading));
                presenter.addPlace(mPlaceName.getText().toString(), placeMark.getGeometry(), mSeekBar.getProgress(), kidId);

                break;
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.closeDialog(AddPlaceActivity.this);
                Toast.makeText(AddPlaceActivity.this, R.string.place_saved, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onFailure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.closeDialog(AddPlaceActivity.this);
                Toast.makeText(AddPlaceActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
