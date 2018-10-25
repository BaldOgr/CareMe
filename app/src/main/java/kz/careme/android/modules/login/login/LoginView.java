package kz.careme.android.modules.login.login;

import com.arellomobile.mvp.MvpView;

import kz.careme.android.model.Account;

public interface LoginView extends MvpView {
    void startActivity();
    void closeDialog();
    void showIncorrectLogin();
    void saveAccount(Account account);
}
