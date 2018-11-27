package kz.careme.android.modules.more.places;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;
import com.yandex.mapkit.geometry.Point;

import kz.careme.android.model.actions.ActionSavePlace;
import kz.careme.android.model.event.PlaceAddedEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class AddPlacePresenter extends BasePresenter<AddPlaceView> {

    @Subscribe
    public void onPlaceAdded(PlaceAddedEvent event) {
        if (event.getMessage().equalsIgnoreCase("added")) {
            getViewState().onSuccess();
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
    }
}
