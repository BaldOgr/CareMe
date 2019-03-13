package kz.careme.android.modules.kids;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import kz.careme.android.model.actions.ActionKidList;
import kz.careme.android.model.event.KidListEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class MyKidsPresenter extends BasePresenter<MyKidsView> {

    public MyKidsPresenter(Context context) {
        super(context);
    }

    @Subscribe
    public void onKidsListLoaded(KidListEvent event) {
        getViewState().onDataLoaded(event.getActionKidList().getKids());
        getProfiler().setKids(event.getActionKidList().getKids());
    }

    public void getKids() {
        ActionKidList actionKidList = new ActionKidList(getProfiler().getAccount().getSid());
        getCallService().call(actionKidList.toString());
    }
}
