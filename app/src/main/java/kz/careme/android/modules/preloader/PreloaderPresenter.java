package kz.careme.android.modules.preloader;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import kz.careme.android.CareMeApp;
import kz.careme.android.model.Account;
import kz.careme.android.model.Const;
import kz.careme.android.model.actions.ActionAuth;
import kz.careme.android.model.actions.ActionAuthKid;
import kz.careme.android.model.actions.CheckCodeKidAction;
import kz.careme.android.model.event.AuthEvent;
import kz.careme.android.model.event.CheckCodeKidEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class PreloaderPresenter extends BasePresenter<PreloaderView> {

    private int accountType;

    public PreloaderPresenter(Context context) {
        super(context);
    }

    @Subscribe
    public void onLogin(AuthEvent authEvent) {
        if (authEvent.getAction().getError() != null && !authEvent.getAction().getError().isEmpty()) {
            getViewState().startChooseAccountTypeActivity();
            return;
        }

        Account account = new Account();
        account.setEmail(authEvent.getAction().getEmail());
        account.setPassword(authEvent.getAction().getPassword());
        account.setSid(authEvent.getAction().getSid());
        account.setAccountType(accountType);
        account.setId(authEvent.getAction().getId());
        account.setRole(authEvent.getAction().getRole());
        getProfiler().setAccount(account);
        if (accountType == Const.TYPE_PARENT) {
            getViewState().startMainActivity();
        } else {
            checkActivation(account.getSid());
        }
    }

    @Subscribe
    public void onCheckCode(CheckCodeKidEvent event){
        if (event.getMessage() == null || event.getMessage().isEmpty()) {
//            ActionGetParentId actionGetParentId = new ActionGetParentId();
//            actionGetParentId.setKidSessionId(getProfiler().getAccount().getSid());
//            getCallService().call(actionGetParentId);
            getProfiler().getAccount().setParentId(event.getParentId());
            getViewState().startChildMainActivity(event.getParentId(), event.getChildId());
            return;
        }
        getViewState().startWriteCodeActivity();
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

    private void checkActivation(String sessionId) {
        CheckCodeKidAction action = new CheckCodeKidAction();
        action.setSid(sessionId);
        getCallService().call(action);
    }


    public void register() {
//        CareMeApp.getCareMeComponent().getBus().register(this);
    }

    public void unsubscribe() {
//        CareMeApp.getCareMeComponent().getBus().unregister(this);
    }
}
