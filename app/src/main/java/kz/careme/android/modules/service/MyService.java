package kz.careme.android.modules.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import com.squareup.otto.Subscribe;

import kz.careme.android.CareMeApp;
import kz.careme.android.model.actions.ActionAuth;
import okhttp3.WebSocket;

public class MyService extends Service {
    WebSocket webSocket;
    MyBinder binder = new MyBinder();

    public MyService() {
        webSocket = CareMeApp.getCareMeComponent().getWebSocketClient();
        CareMeApp.getCareMeComponent().getBus().register(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (webSocket == null)
            webSocket = CareMeApp.getCareMeComponent().getWebSocketClient();

        return START_STICKY;
    }

    public void sendMessage(String message) {
        webSocket.send(message);
    }

    @Subscribe
    public void showNotification(ActionAuth actionAuth) {
    }

    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }

    }
}
