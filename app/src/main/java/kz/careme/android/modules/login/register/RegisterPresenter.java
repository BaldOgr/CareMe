package kz.careme.android.modules.login.register;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import kz.careme.android.model.Account;
import kz.careme.android.model.Const;
import kz.careme.android.model.actions.ActionAuth;
import kz.careme.android.model.actions.ActionRegister;
import kz.careme.android.model.actions.ActionRegisterChild;
import kz.careme.android.model.event.RegEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class RegisterPresenter extends BasePresenter<RegisterView> {
    private String email;
    private String password;
    private int accountType;
    private boolean registerKid;

    @Subscribe
    public void onReg(RegEvent regEvent) {
        if (regEvent.getActionRegister().getError() != null && !regEvent.getActionRegister().getError().isEmpty()) {
            getViewState().dismissDialog();
            getViewState().showError(regEvent.getActionRegister().getError());
            return;
        }
        if (!registerKid) {
            Account account = new Account();
            account.setEmail(email);
            account.setPassword(password);
            account.setAccountType(accountType);
            getProfiler().setAccount(account);
            getViewState().nextActivity();
        } else {
            getViewState().startWriteCodeActivity(regEvent.getActionRegister().getUserId());
        }
    }

    public void reg(String email, String password, int mAccountType, boolean mRegisterKid) {
        accountType = mAccountType;
        registerKid = mRegisterKid;
        if (mAccountType == Const.TYPE_PARENT) {
            ActionRegister register = new ActionRegister();
            register.setEmail(email);
            register.setPassword(password);
            getCallService().call(register.toString());
            this.email = email;
            this.password = password;
        } else {
            ActionRegisterChild register = new ActionRegisterChild();
            register.setEmail(email);
            register.setPassword(password);
            getCallService().call(register.toString());
            this.email = email;
            this.password = password;
        }
    }
}
