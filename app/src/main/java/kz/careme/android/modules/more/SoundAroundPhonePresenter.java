package kz.careme.android.modules.more;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import kz.careme.android.model.actions.ActionListenSound;
import kz.careme.android.model.event.ListenSoundEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class SoundAroundPhonePresenter extends BasePresenter<SoundAroundPhoneView> {

    public SoundAroundPhonePresenter(Context context) {
        super(context);
    }

    public void recordingStarted(ListenSoundEvent event) {
        getViewState().recordingStarted();
    }

    public void startRecording(int kidId) {
        ActionListenSound actionSoundAroundPhone = new ActionListenSound();
        actionSoundAroundPhone.setSid(getProfiler().getAccount().getSid());
        actionSoundAroundPhone.setKidId(kidId);
        getCallService().call(actionSoundAroundPhone.toString());
    }
}
