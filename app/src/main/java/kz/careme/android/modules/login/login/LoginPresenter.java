package kz.careme.android.modules.login.login;

import android.content.Context;

import com.google.gson.Gson;

import kz.careme.android.model.Account;
import kz.careme.android.model.Const;
import kz.careme.android.model.ErrorMessage;
import kz.careme.android.model.actions.ActionAuth;
import kz.careme.android.model.actions.ActionAuthKid;
import kz.careme.android.modules.BasePresenter;

public class LoginPresenter extends BasePresenter {

    private LoginView loginView;
    public LoginPresenter(Context context) {
        super(context);
        loginView = (LoginView) context;
    }

    @Override
    public void onMessage(String text) {
        ErrorMessage error = new Gson().fromJson(text, ErrorMessage.class);
        loginView.closeDialog();
        if (!error.getError().isEmpty()) {
            loginView.showIncorrectLogin();
            return;
        }
        Account account = new Gson().fromJson(text, Account.class);
        saveAccount(account);
        getProfiler().setAccount(account);
        loginView.startActivity();
    }

    private void saveAccount(Account account) {
        getContext().getSharedPreferences("CareMe", Context.MODE_PRIVATE)
                .edit()
                .putString(Const.EMAIL, account.getEmail())
                .putString(Const.PASSWORD, account.getPassword())
                .apply();
    }

    public void auth(String email, String password) {
        ActionAuth actionAuth = new ActionAuth();
        actionAuth.setEmail(email);
        actionAuth.setPassword(password);
        getCallService().call(actionAuth.toString(), this);
    }

    public void authKid(String email, String password) {
        ActionAuthKid actionAuth = new ActionAuthKid();
        actionAuth.setEmail(email);
        actionAuth.setPassword(password);
        getCallService().call(actionAuth.toString(), this);
    }
}
