package kz.careme.android.model.event;

import kz.careme.android.model.Account;
import kz.careme.android.model.actions.BaseAction;

public class AccountEvent {
    private Account account;
    private BaseAction action;

    public AccountEvent(Account account, BaseAction action) {
        this.account = account;
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BaseAction getAction() {
        return action;
    }

    public void setAction(BaseAction action) {
        this.action = action;
    }
}
