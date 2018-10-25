package kz.careme.android.modules.preloader;

import android.content.Context;

import kz.careme.android.model.Account;
import kz.careme.android.model.actions.ActionAuth;
import kz.careme.android.model.actions.ActionAuthKid;
import kz.careme.android.modules.BasePresenter;

public class PreloaderPresenter extends BasePresenter {

    public PreloaderPresenter(Context context) {
        super(context);
    }

    @Override
    public void onMessage(String text) {
        Account account = new Account();
    }

    public void auth(String email, String password) {
    }

    public void authKid(String email, String password) {
    }
}
