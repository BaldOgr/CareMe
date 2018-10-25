package kz.careme.android.modules.preloader;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import kz.careme.android.CareMeApp;
import kz.careme.android.model.Account;
import kz.careme.android.model.Const;
import kz.careme.android.model.actions.ActionAuth;
import kz.careme.android.model.actions.ActionAuthKid;
import kz.careme.android.model.event.AccountEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class PreloaderPresenter extends BasePresenter<PreloaderView> {

    private int accountType;

    @Subscribe
    public void onLogin(AccountEvent accountEvent) {
        if (!accountEvent.getAction().getError().isEmpty()) {

            return;
        }
        accountEvent.getAccount().setAccountType(accountType);
        getProfiler().setAccount(accountEvent.getAccount());
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
}
