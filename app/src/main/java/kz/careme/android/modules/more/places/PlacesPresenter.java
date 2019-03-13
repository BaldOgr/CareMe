package kz.careme.android.modules.more.places;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import kz.careme.android.model.Place;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class PlacesPresenter extends BasePresenter<PlacesView> {

    public PlacesPresenter(Context context) {
        super(context);
    }

    @Subscribe
    public void onPlaceAdded(Place place) {
        getViewState().onPLaceAdded(place);
    }

}
