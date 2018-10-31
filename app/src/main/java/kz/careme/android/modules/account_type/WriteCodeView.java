package kz.careme.android.modules.account_type;

import com.arellomobile.mvp.MvpView;

public interface WriteCodeView extends MvpView {

    void setCode(int code);

    void dismissDialog();

    void showError(String error);

    void onCodeActivated(int parentId, int childId);
}
