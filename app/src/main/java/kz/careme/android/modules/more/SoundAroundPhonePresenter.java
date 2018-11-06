package kz.careme.android.modules.more;

import com.arellomobile.mvp.InjectViewState;

import kz.careme.android.model.actions.ActionSoundAroundPhone;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class SoundAroundPhonePresenter extends BasePresenter<SoundAroundPhoneView> {


    public void startRecording(Long kidId) {
        ActionSoundAroundPhone actionSoundAroundPhone = new ActionSoundAroundPhone();
        actionSoundAroundPhone.setSessionId(getProfiler().getAccount().getSid());
        actionSoundAroundPhone.setKidId(kidId);
        getCallService().call(actionSoundAroundPhone.toString());
    }
}
