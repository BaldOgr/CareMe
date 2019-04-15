package kz.careme.android.modules.more;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import kz.careme.android.model.actions.ActionGetSound;
import kz.careme.android.model.actions.ActionListenSound;
import kz.careme.android.model.actions.ActionStartListenSound;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class SoundAroundPhonePresenter extends BasePresenter<SoundAroundPhoneView> {

    private long kidId;
    private long recordId;

    public SoundAroundPhonePresenter(Context context) {
        super(context);
    }

    @Subscribe
    public void onRecorded(ActionListenSound actionListenSound) {
        recordId = actionListenSound.getId();
        getCallService().call(new ActionGetSound(getProfiler().getAccount().getSid(), kidId));
    }

    @Subscribe
    public void onRecorded(ActionGetSound actionGetSound) {
        FTPClient ftpClient = new FTPClient();
        BufferedInputStream buffIn;

        ActionGetSound.Record record = null;
        for (ActionGetSound.Record record1 : actionGetSound.getRecordList()) {
            if (recordId == record1.getId()) {
                record = record1;
                break;
            }
        }
        if (record == null) {
            return;
        }

        String localFile = getContext().getCacheDir().getAbsolutePath() + "/" + record.getFile();
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
                success = ftpClient.retrieveFile(record.getFile(), outputStream);
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

    public void startRecording(long kidId) {
        this.kidId = kidId;
        ActionStartListenSound startListenSound = new ActionStartListenSound();
        startListenSound.setKidId(kidId);
        startListenSound.setSessionId(getProfiler().getAccount().getSid());
        getCallService().call(startListenSound);
        getViewState().recordingStarted();
    }
}
