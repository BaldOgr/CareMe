package kz.careme.android.model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import kz.careme.android.modules.service.MyService;

public class CallService {

    private MyService myService;

    @Inject
    public CallService() {
    }

    public void setMyService(MyService myService) {
        Log.d("CallService", "Service set!");
        this.myService = myService;
    }

    public void call(final String message) {
        Log.d("CallService", "Calling: " + message);
        if (myService == null) {

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    call(message);
                }
            }, 500);
            return;
        }
        myService.sendMessage(message);
    }

    public MyService getService() {
        return myService;
    }
}
