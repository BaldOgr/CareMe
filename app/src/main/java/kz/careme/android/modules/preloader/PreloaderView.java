package kz.careme.android.modules.preloader;

import com.arellomobile.mvp.MvpView;

public interface PreloaderView extends MvpView {
    void startChooseAccountTypeActivity();

    void startMainActivity();

    void startChildMainActivity();
}
