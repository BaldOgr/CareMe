package kz.careme.android.model.event;

import kz.careme.android.model.actions.ActionActivateCode;

public class CodeActivatedEvent {
    private ActionActivateCode actionActivateCode;

    public CodeActivatedEvent(ActionActivateCode actionActivateCode) {
        this.actionActivateCode = actionActivateCode;
    }

    public ActionActivateCode getActionActivateCode() {
        return actionActivateCode;
    }

    public void setActionActivateCode(ActionActivateCode actionActivateCode) {
        this.actionActivateCode = actionActivateCode;
    }
}
