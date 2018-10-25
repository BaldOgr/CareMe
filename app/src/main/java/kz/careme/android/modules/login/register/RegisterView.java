package kz.careme.android.modules.login.register;

import com.arellomobile.mvp.MvpView;

public interface RegisterView extends MvpView {
    void showDialog();
    void dismissDialog();
    void showError(String message);
    void nextActivity();
}
