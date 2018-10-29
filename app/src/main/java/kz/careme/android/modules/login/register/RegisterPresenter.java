package kz.careme.android.modules.login.register;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import kz.careme.android.model.Account;
import kz.careme.android.model.actions.ActionRegister;
import kz.careme.android.model.event.RegEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class RegisterPresenter extends BasePresenter<RegisterView> {
    private String email;
    private String password;


    @Subscribe
    public void onReg(RegEvent regEvent) {
        getViewState().dismissDialog();
        if (regEvent.getActionRegister().getError() != null && !regEvent.getActionRegister().getError().isEmpty()) {
            getViewState().showError(regEvent.getActionRegister().getError());
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
