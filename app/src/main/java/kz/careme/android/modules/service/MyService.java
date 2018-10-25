package kz.careme.android.modules.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import kz.careme.android.CareMeApp;
import kz.careme.android.model.websocket.WebSocketClient;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

public class MyService extends Service {
    WebSocket client;
    OkHttpClient okHttpClient;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (client == null)
            client = ((CareMeApp) getApplication()).getProfilerComponent().getWebSocketClient();
        if (okHttpClient == null)
            okHttpClient = ((CareMeApp) getApplication()).getProfilerComponent().getOkHttpClient();
        return START_STICKY;
    }
}
