package kz.careme.android.modules.more;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import kz.careme.android.CareMeApp;
import kz.careme.android.model.actions.ActionGetSound;
import kz.careme.android.model.actions.ActionStartListenSound;
import kz.careme.android.model.event.ListenSoundEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class SoundAroundPhonePresenter extends BasePresenter<SoundAroundPhoneView> {

    public SoundAroundPhonePresenter(Context context) {
        super(context);
    }

    @Subscribe
    public void onRecorded(ActionGetSound actionGetSound) {
        FTPClient ftpClient = new FTPClient();
        BufferedInputStream buffIn;
        String localFile = getContext().getCacheDir().getAbsolutePath() + "/" + actionGetSound.getFile();
        try {
            ftpClient.connect("195.93.152.96");
            ftpClient.login("ftpuser", "Open111");
            ftpClient.changeWorkingDirectory("/html/server/sound/");

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            OutputStream outputStream = null;
            boolean success = false;

            try {
                outputStream = new BufferedOutputStream(new FileOutputStream(localFile));
                success = ftpClient.retrieveFile(actionGetSound.getFile(), outputStream);
            } finally {
                outputStream.close();
            }
            if (success) {
                getViewState().playRecord(localFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void startRecording(final long kidId) {
        ActionStartListenSound startListenSound = new ActionStartListenSound();
        startListenSound.setKidId(kidId);
        startListenSound.setSessionId(getProfiler().getAccount().getSid());
        getCallService().call(startListenSound);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                getCallService().call(new ActionGetSound(getProfiler().getAccount().getSid(), kidId));
            }
        }, 40_000);
        getViewState().recordingStarted();
    }
}
