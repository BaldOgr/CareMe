package kz.careme.android.modules.login.register;

import android.content.Context;

import com.google.gson.Gson;

import kz.careme.android.model.Account;
import kz.careme.android.model.ErrorMessage;
import kz.careme.android.model.actions.ActionRegister;
import kz.careme.android.modules.BasePresenter;

public class RegisterPresenter extends BasePresenter<RegisterView> {
    private String email;
    private String password;


//    @Override
    public void onMessage(String text) {
        ErrorMessage errorMessage = new Gson().fromJson(text, ErrorMessage.class);
        getViewState().dismissDialog();
        if (!errorMessage.getError().isEmpty()) {
            getViewState().dismissDialog();
            getViewState().showError(errorMessage.getError());
            return;
        }
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        getProfiler().setAccount(account);
        getViewState().nextActivity();
    }

    public void reg(String email, String password) {
        ActionRegister register = new ActionRegister();
        register.setEmail(email);
        register.setPassword(password);
        getCallService().call(register.toString());
        this.email = email;
        this.password = password;
    }
}
