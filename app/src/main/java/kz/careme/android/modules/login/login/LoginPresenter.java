package kz.careme.android.modules.login.login;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.google.gson.Gson;

import kz.careme.android.model.Account;
import kz.careme.android.model.Const;
import kz.careme.android.model.ErrorMessage;
import kz.careme.android.model.actions.ActionAuth;
import kz.careme.android.model.actions.ActionAuthKid;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class LoginPresenter extends BasePresenter<LoginView> {

    public void onMessage(String text) {
        ErrorMessage error = new Gson().fromJson(text, ErrorMessage.class);
        getViewState().closeDialog();
        if (!error.getError().isEmpty()) {
            getViewState().showIncorrectLogin();
            return;
        }
        Account account = new Gson().fromJson(text, Account.class);
        getProfiler().setAccount(account);
        getViewState().saveAccount(account);
        getViewState().startActivity();
    }

    public void auth(String email, String password) {
        ActionAuth actionAuth = new ActionAuth();
        actionAuth.setEmail(email);
        actionAuth.setPassword(password);
        getCallService().call(actionAuth.toString());
    }

    public void authKid(String email, String password) {
        ActionAuthKid actionAuth = new ActionAuthKid();
        actionAuth.setEmail(email);
        actionAuth.setPassword(password);
        getCallService().call(actionAuth.toString());
    }
}
