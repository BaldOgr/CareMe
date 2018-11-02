package kz.careme.android.modules.kids;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import kz.careme.android.model.actions.ActionKidList;
import kz.careme.android.model.event.KidListEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class MyKidsPresenter extends BasePresenter<MyKidsView> {

    @Subscribe
    public void onKidsListLoaded(KidListEvent event) {
        getViewState().onDataLoaded(event.getActionKidList().getKids());
    }

    public void getKids() {
        ActionKidList actionKidList = new ActionKidList(getProfiler().getAccount().getSid());
        getCallService().call(actionKidList.toString());
    }
}
