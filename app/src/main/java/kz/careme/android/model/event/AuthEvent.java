package kz.careme.android.model.event;

import kz.careme.android.model.Account;
import kz.careme.android.model.actions.ActionAuth;
import kz.careme.android.model.actions.BaseAction;

public class AuthEvent {
    private ActionAuth action;

    public AuthEvent(ActionAuth action) {
        this.action = action;
    }

    public ActionAuth getAction() {
        return action;
    }

    public void setAction(ActionAuth action) {
        this.action = action;
    }
}
