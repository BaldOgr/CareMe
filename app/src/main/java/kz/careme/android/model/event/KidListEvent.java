package kz.careme.android.model.event;

import kz.careme.android.model.actions.ActionKidList;

public class KidListEvent {

    private ActionKidList actionKidList;

    public KidListEvent(ActionKidList actionKidList) {
        this.actionKidList = actionKidList;
    }

    public ActionKidList getActionKidList() {
        return actionKidList;
    }

    public void setActionKidList(ActionKidList actionKidList) {
        this.actionKidList = actionKidList;
    }
}
