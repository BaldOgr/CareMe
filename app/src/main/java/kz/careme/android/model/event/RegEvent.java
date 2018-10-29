package kz.careme.android.model.event;

import kz.careme.android.model.actions.ActionRegister;

public class RegEvent {
    private ActionRegister actionRegister;

    public RegEvent(ActionRegister actionRegister) {
        this.actionRegister = actionRegister;
    }

    public ActionRegister getActionRegister() {
        return actionRegister;
    }

    public void setActionRegister(ActionRegister actionRegister) {
        this.actionRegister = actionRegister;
    }

}
