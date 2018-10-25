package kz.careme.android.model;

import android.util.Log;

import javax.inject.Inject;

import kz.careme.android.model.websocket.WebSocketClient;
import kz.careme.android.modules.BasePresenter;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class CallService {
    private WebSocket webSocket;
    private WebSocketClient webSocketClient;

    @Inject
    public CallService(WebSocket webSocket, WebSocketClient webSocketListener) {
        this.webSocket = webSocket;
        this.webSocketClient = webSocketListener;
    }

    public void call(String message, BasePresenter callback) {
        Log.d(CallService.class.getSimpleName(), "Calling:" + message);
        webSocketClient.addCallback(callback);
        webSocket.send(message);
    }

}
