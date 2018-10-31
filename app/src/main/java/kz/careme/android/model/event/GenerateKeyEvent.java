package kz.careme.android.model.event;

import kz.careme.android.model.actions.ActionGenerateCode;

public class GenerateKeyEvent {

    private ActionGenerateCode actionGenerateCode;

    public GenerateKeyEvent(ActionGenerateCode actionGenerateCode) {
        this.actionGenerateCode = actionGenerateCode;
    }

    public ActionGenerateCode getActionGenerateCode() {
        return actionGenerateCode;
    }

    public void setActionGenerateCode(ActionGenerateCode actionGenerateCode) {
        this.actionGenerateCode = actionGenerateCode;
    }
}
