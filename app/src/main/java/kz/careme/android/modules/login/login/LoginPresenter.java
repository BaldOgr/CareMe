package kz.careme.android.modules.login.login;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import kz.careme.android.model.Account;
import kz.careme.android.model.Const;
import kz.careme.android.model.actions.ActionAuth;
import kz.careme.android.model.actions.ActionAuthKid;
import kz.careme.android.model.actions.CheckCodeKidAction;
import kz.careme.android.model.event.AuthEvent;
import kz.careme.android.model.event.CheckCodeKidEvent;
import kz.careme.android.model.event.GetParentIdEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class LoginPresenter extends BasePresenter<LoginView> {

    private int accountType;

    @Subscribe
    public void onLogin(AuthEvent actionAuth) {
        getViewState().closeDialog();
        if (actionAuth.getAction().getError() != null && !actionAuth.getAction().getError().isEmpty()) {
            getViewState().showIncorrectLogin();
            return;
        }
        Account account = new Account();
        account.setEmail(actionAuth.getAction().getEmail());
        account.setPassword(actionAuth.getAction().getPassword());
        account.setSid(actionAuth.getAction().getSid());
        account.setAccountType(accountType);
        account.setId(actionAuth.getAction().getId());
        getProfiler().setAccount(account);
        getViewState().saveAccount(account);
        if (accountType == Const.TYPE_CHILD) {
            checkActivation(account.getSid());
            return;
        }
        getViewState().startWriteCodeActivity();
//        CareMeApp.getCareMeComponent().getBus().unregister(this);
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

    @Subscribe
    public void onGetParentId(GetParentIdEvent event) {
        getProfiler().getAccount().setParentId(event.getParentId());
    }

    private void checkActivation(String sessionId) {
        CheckCodeKidAction action = new CheckCodeKidAction();
        action.setSid(sessionId);
        getCallService().call(action);
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
