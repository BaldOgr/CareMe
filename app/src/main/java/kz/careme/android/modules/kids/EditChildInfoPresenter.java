package kz.careme.android.modules.kids;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import kz.careme.android.model.actions.ActionEditKid;
import kz.careme.android.model.event.KidEditedEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class EditChildInfoPresenter extends BasePresenter<EditChildInfoView> {

    @Subscribe
    public void onKidEdited(KidEditedEvent event) {
        if ("Save ok".equals(event.getMessage())) {
            getViewState().kidEdited();
        } else {
            getViewState().showError();
        }
    }

    public void onDonePressed(int id, String name, String lastName) {
        ActionEditKid editKid = new ActionEditKid(id, name, lastName);
        editKid.setSid(getProfiler().getAccount().getSid());
        getCallService().call(editKid);
    }

}
