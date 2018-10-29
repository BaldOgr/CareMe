package kz.careme.android.model.websocket;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.otto.Bus;

import javax.annotation.Nullable;
import javax.inject.Inject;

import kz.careme.android.CareMeApp;
import kz.careme.android.model.Account;
import kz.careme.android.model.actions.ActionAuth;
import kz.careme.android.model.actions.ActionAuthKid;
import kz.careme.android.model.actions.ActionRegister;
import kz.careme.android.model.actions.BaseAction;
import kz.careme.android.model.event.AuthEvent;
import kz.careme.android.model.event.RegEvent;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient extends WebSocketListener {

    private Bus bus;

    @Inject
    public WebSocketClient() {
        bus = CareMeApp.getCareMeComponent().getBus();
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.d("WebSocketClient", "WebSocket Opened!");

    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.d("WebSocketClient", "Response: " + text);
        BaseAction action = new Gson().fromJson(text, BaseAction.class);
//        action.setAction(ActionAuth.ACTION);
        switch (action.getAction()) {
            case ActionAuth.ACTION:
            case ActionAuthKid.ACTION:
                bus.post(new AuthEvent(new Gson().fromJson(text, ActionAuth.class)));
                break;
            case ActionRegister.ACTION:
                bus.post(new RegEvent(new Gson().fromJson(text, ActionRegister.class)));
                break;
        }
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
}
