package kz.careme.android.modules.more.places;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.CareMeApp;
import kz.careme.android.R;
import kz.careme.android.model.Place;
import kz.careme.android.model.Profiler;
import kz.careme.android.modules.parent_main.ChangeBehaviorListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesFragment extends MvpAppCompatFragment implements ViewTreeObserver.OnGlobalLayoutListener, PlacesView {
    public static final String TAG = "PlacesFragment";


    @BindView(R.id.bottom_sheet_header_image)
    ImageView mHeaderImage;

    @BindView(R.id.bottom_sheet_text)
    TextView mHeaderText;

    @BindView(R.id.reload)
    ImageView mHeaderAdd;


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.bottom_sheet_header)
    View mHeader;

    Profiler mProfiler;

    @InjectPresenter
    PlacesPresenter presenter;

    private PlacesAdapter mAdapter;

    private ChangeBehaviorListener mChangeBehaviorListener;

    public PlacesFragment() {
    }

    @ProvidePresenter
    public PlacesPresenter getPresenter() {
        return new PlacesPresenter(getContext());
    }

    public static Fragment newInstance(ChangeBehaviorListener listener) {
        PlacesFragment fragment = new PlacesFragment();
        fragment.mChangeBehaviorListener = listener;
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places, container, false);
        ButterKnife.bind(this, view);
        mHeaderImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_placeslight));
        mHeaderText.setText(R.string.places);
        mHeaderAdd.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
        mHeaderAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddPlaceActivity.class));
            }
        });
        mProfiler = ((CareMeApp) getContext().getApplicationContext()).getCareMeComponent().getProfiler();
        view.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new PlacesAdapter();
        mAdapter.setPlaces(mProfiler.getPlaces());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onGlobalLayout() {
        if (isVisible()) {
            int height = mHeader.getHeight();
            if (mRecyclerView.getChildCount() != 0) {
                height += mRecyclerView.getChildAt(0).getHeight();
            }
            mChangeBehaviorListener.changeBehaviorPeekSize(height);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPlacesLoaded(List<Place> places) {
        mAdapter.setPlaces(places);
    }

    @Override
    public void onPLaceAdded(Place place) {
        mAdapter.getPlaces().add(place);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyItemInserted(mAdapter.getItemCount());
            }
        });
    }
}
