package kz.careme.android.modules.kids;

import com.arellomobile.mvp.MvpView;

import kz.careme.android.model.Kid;

public interface EditChildInfoView extends MvpView {
    void kidEdited();

    void setFields(Kid kid);

    void finishActivity();

    void showError();
}
