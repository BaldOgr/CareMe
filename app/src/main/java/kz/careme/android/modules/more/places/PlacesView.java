package kz.careme.android.modules.more.places;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import kz.careme.android.model.Place;

public interface PlacesView extends MvpView {
    void onPlacesLoaded(List<Place> places);
}
