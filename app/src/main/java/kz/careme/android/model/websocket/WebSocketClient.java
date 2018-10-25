package kz.careme.android.model.websocket;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import kz.careme.android.model.actions.ActionRegister;
import kz.careme.android.modules.BasePresenter;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient extends WebSocketListener {


    private List<BasePresenter> presenters = new ArrayList<>();

    @Inject
    public WebSocketClient() {
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        Log.d("WebSocketClient", "Response: " + text);
        presenters.get(0).onMessage(text);
        presenters.remove(0);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
        Log.e("WebSocketClient", "Error!!!", t);
    }

    public void addCallback(BasePresenter callback) {
        presenters.add(callback);
    }
}
