package kz.careme.android.modules.login.register;

import android.content.Context;

import com.google.gson.Gson;

import kz.careme.android.model.Account;
import kz.careme.android.model.ErrorMessage;
import kz.careme.android.model.actions.ActionRegister;
import kz.careme.android.modules.BasePresenter;

public class RegisterPresenter extends BasePresenter {
    private RegisterView registerView;
    private String email;
    private String password;

    public RegisterPresenter(Context context, RegisterView registerView) {
        super(context);
        this.registerView = registerView;
    }

    @Override
    public void onMessage(String text) {
        ErrorMessage errorMessage = new Gson().fromJson(text, ErrorMessage.class);
        registerView.dismissDialog();
        if (!errorMessage.getError().isEmpty()) {
            registerView.dismissDialog();
            registerView.showError(errorMessage.getError());
            return;
        }
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        getProfiler().setAccount(account);
        registerView.nextActivity();
    }

    public void reg(String email, String password) {
        ActionRegister register = new ActionRegister();
        register.setEmail(email);
        register.setPassword(password);
        getCallService().call(register.toString(), this);
        this.email = email;
        this.password = password;
    }
}
