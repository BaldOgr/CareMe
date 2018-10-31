package kz.careme.android.modules.preloader;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import kz.careme.android.CareMeApp;
import kz.careme.android.model.Account;
import kz.careme.android.model.Const;
import kz.careme.android.model.actions.ActionAuth;
import kz.careme.android.model.actions.ActionAuthKid;
import kz.careme.android.model.event.AuthEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class PreloaderPresenter extends BasePresenter<PreloaderView> {

    private int accountType;

    @Subscribe
    public void onLogin(AuthEvent authEvent) {
        if (authEvent.getAction().getCode() != 0) {
            getViewState().startChooseAccountTypeActivity();
            return;
        }

        Account account = new Account();
        account.setEmail(authEvent.getAction().getEmail());
        account.setPassword(authEvent.getAction().getPassword());
        account.setSid(authEvent.getAction().getSid());
        account.setAccountType(accountType);
        getProfiler().setAccount(account);
        if (accountType == Const.TYPE_PARENT) {
            getViewState().startMainActivity();
        } else {
            getViewState().startChildMainActivity();
        }
    }

    public void auth(String email, String password) {
        ActionAuth actionAuth = new ActionAuth();
        actionAuth.setEmail(email);
        actionAuth.setPassword(password);
        getCallService().call(actionAuth.toString());
        accountType = Const.TYPE_PARENT;
    }

    public void authKid(String email, String password) {
        ActionAuthKid actionAuth = new ActionAuthKid();
        actionAuth.setEmail(email);
        actionAuth.setPassword(password);
        getCallService().call(actionAuth.toString());
        accountType = Const.TYPE_CHILD;
    }

    public void unsubscribe() {
        CareMeApp.getCareMeComponent().getBus().unregister(this);
    }
}
