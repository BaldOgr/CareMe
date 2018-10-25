package kz.careme.android.model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import javax.inject.Inject;

import kz.careme.android.modules.service.MyService;

public class CallService {

    private MyService myService;

    @Inject
    public CallService(Context context) {
        context.bindService(new Intent(context, MyService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myService = ((MyService.MyBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, 0);
    }

    public void call(String message) {
        Log.d("CallService", "Calling: " + message);
        myService.sendMessage(message);
    }

}
