package kz.careme.android.model;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import kz.careme.android.CareMeApp;
import kz.careme.android.model.actions.BaseAction;
import kz.careme.android.model.websocket.WebSocketClient;
import kz.careme.android.modules.service.MyService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class CallService {

    private MyService myService;
    private WebSocket webSocket;
    private WebSocketClient webSocketClient;

    @Inject
    public CallService(OkHttpClient client) {
        webSocketClient = new WebSocketClient();
        Request request = new Request.Builder().url("ws://195.93.152.96:11210").build();
        webSocket = client.newWebSocket(request, webSocketClient);
    }

    public void setMyService(MyService myService) {
        Log.d("CallService", "Service set!");
        this.myService = myService;
    }

    public void call(final String message) {
        Log.d("CallService", "Calling: " + message);
        boolean sended = webSocket.send(message);
        Log.d("CallService", "Is sended: " + sended);
        if (!sended) {
            reconnect();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    call(message);
                }
            }, 1000L);
        }
    }

    public MyService getService() {
        return myService;
    }

    public void call(BaseAction sendMessage) {
        if (sendMessage.getAction() == null || sendMessage.getAction().isEmpty())
            throw new IllegalArgumentException("Action cannot be null or empty!");
        call(sendMessage.toString());
    }

    public void reconnect() {
        Log.d("CallService", "Reconnecting...");
        Request request = new Request.Builder().url("ws://195.93.152.96:11210").build();
        webSocket = CareMeApp.getCareMeComponent().getOkHttpClient().newWebSocket(request, webSocketClient);
        Log.d("CallService", "Reconnected!");
    }
}
