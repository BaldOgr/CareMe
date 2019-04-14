package kz.careme.android.modules.more;

import com.arellomobile.mvp.MvpView;

public interface SoundAroundPhoneView extends MvpView {

    void recordingStarted();

    void playRecord(String localFile);
}
