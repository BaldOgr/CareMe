package kz.careme.android.modules.more.places;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.R;
import kz.careme.android.model.Place;
import kz.careme.android.modules.BaseActivity;

public class AddPlaceActivity extends BaseActivity {

    @BindView(R.id.place_address)
    TextView mPlaceAddress;

    @BindView(R.id.place_name)
    TextView mPlaceName;

    @BindView(R.id.seekbar)
    SeekBar mSeekbar;

    @BindView(R.id.seekbar_value)
    TextView mSeekbarValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        ButterKnife.bind(this);
        initializeActionBar(true, "");

        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSeekbarValue.setText(getString(R.string.seekbar_value, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekbar.setProgress(200);
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
                profiler.getPlaces().add(generatePlace());
                finish();
                break;
            case R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private Place generatePlace() {
        Place place = new Place();
        place.setAddress(mPlaceAddress.getText().toString());
        place.setName(mPlaceName.getText().toString());
        place.setSafeRadius(mSeekbar.getProgress());
        place.setId(profiler.getPlaces().size());
        return place;
    }
}
