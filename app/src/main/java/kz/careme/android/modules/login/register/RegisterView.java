package kz.careme.android.modules.login.register;

public interface RegisterView {
    void showDialog();
    void dismissDialog();
    void showError(String message);
    void nextActivity();
}
