package kz.careme.android.modules.login.login;

import com.arellomobile.mvp.InjectViewState;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

import kz.careme.android.CareMeApp;
import kz.careme.android.model.Account;
import kz.careme.android.model.Const;
import kz.careme.android.model.ErrorMessage;
import kz.careme.android.model.actions.ActionAuth;
import kz.careme.android.model.actions.ActionAuthKid;
import kz.careme.android.model.event.AuthEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class LoginPresenter extends BasePresenter<LoginView> {

    private int accountType;

    @Subscribe
    public void onLogin(AuthEvent actionAuth) {
        getViewState().closeDialog();
        if (actionAuth.getAction().getCode() != 0) {
            getViewState().showIncorrectLogin();
            return;
        }
        Account account = new Account();
        account.setEmail(actionAuth.getAction().getEmail());
        account.setPassword(actionAuth.getAction().getPassword());
        account.setSid(actionAuth.getAction().getSid());
        account.setAccountType(accountType);
        getProfiler().setAccount(account);
        getViewState().saveAccount(account);
        getViewState().startActivity();
//        CareMeApp.getCareMeComponent().getBus().unregister(this);
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
