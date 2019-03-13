package kz.careme.android.modules.child_main;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import kz.careme.android.model.event.ListenSoundEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class ChildMainPresenter extends BasePresenter<ChildMainActivityView> {

    public ChildMainPresenter(Context context) {
        super(context);
    }

    public void sendAlarmToParent() {
        //TODO: to realize AlarmToParent
    }
}
