package kz.careme.android.modules.more.places;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;
import com.yandex.mapkit.geometry.Point;

import kz.careme.android.CareMeApp;
import kz.careme.android.model.Place;
import kz.careme.android.model.actions.ActionSavePlace;
import kz.careme.android.model.event.PlaceAddedEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class AddPlacePresenter extends BasePresenter<AddPlaceView> {

    private Place place;

    @Subscribe
    public void onPlaceAdded(PlaceAddedEvent event) {
        if (event.getMessage().equalsIgnoreCase("added")) {
            getViewState().onSuccess();
            CareMeApp.getCareMeComponent().getBus().post(place);
        } else {
            getViewState().onFailure();
        }
    }

    public void addPlace(String text, Point point, int radius, int kidId) {
        ActionSavePlace actionSavePlace = new ActionSavePlace();
        actionSavePlace.setKidId(kidId);
        actionSavePlace.setSessionId(getProfiler().getAccount().getSid());
        actionSavePlace.setLatitude(point.getLatitude());
        actionSavePlace.setLongitude(point.getLongitude());
        actionSavePlace.setRadius(radius);
        actionSavePlace.setTitle(text);
        actionSavePlace.setType(1);
        getCallService().call(actionSavePlace);
        place = new Place();
        place.setKidId(kidId);
        place.setLatitude(point.getLatitude());
        place.setLongitude(point.getLongitude());
        place.setRadius(radius);
        place.setTitle(text);
        place.setType(1);
    }
}
