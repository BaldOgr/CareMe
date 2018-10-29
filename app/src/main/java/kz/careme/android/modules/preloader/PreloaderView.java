package kz.careme.android.modules.preloader;

import com.arellomobile.mvp.MvpView;

public interface PreloaderView extends MvpView {
    void startLoginActivity();

    void startMainActivity();

    void startChildMainActivity();
}
