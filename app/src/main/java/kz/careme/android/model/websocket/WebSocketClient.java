package kz.careme.android.model.websocket;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.otto.Bus;

import org.json.JSONException;

import javax.annotation.Nullable;
import javax.inject.Inject;

import kz.careme.android.CareMeApp;
import kz.careme.android.model.CallService;
import kz.careme.android.model.actions.ActionActivateCode;
import kz.careme.android.model.actions.ActionAuth;
import kz.careme.android.model.actions.ActionAuthKid;
import kz.careme.android.model.actions.ActionEditKid;
import kz.careme.android.model.actions.ActionGenerateCode;
import kz.careme.android.model.actions.ActionGetMessage;
import kz.careme.android.model.actions.ActionGetSound;
import kz.careme.android.model.actions.ActionKidList;
import kz.careme.android.model.actions.ActionListenSound;
import kz.careme.android.model.actions.ActionNeedReconnect;
import kz.careme.android.model.actions.ActionRegister;
import kz.careme.android.model.actions.ActionRegisterChild;
import kz.careme.android.model.actions.ActionSavePlace;
import kz.careme.android.model.actions.ActionSendMessage;
import kz.careme.android.model.actions.ActionStartListenSound;
import kz.careme.android.model.actions.BaseAction;
import kz.careme.android.model.actions.CheckCodeKidAction;
import kz.careme.android.model.di.CareMeComponent;
import kz.careme.android.model.event.AuthEvent;
import kz.careme.android.model.event.CheckCodeKidEvent;
import kz.careme.android.model.event.CodeActivatedEvent;
import kz.careme.android.model.event.CodeGeneratedEvent;
import kz.careme.android.model.event.KidEditedEvent;
import kz.careme.android.model.event.KidListEvent;
import kz.careme.android.model.event.ListenSoundEvent;
import kz.careme.android.model.event.MessageLoadedEvent;
import kz.careme.android.model.event.PlaceAddedEvent;
import kz.careme.android.model.event.PullABellEvent;
import kz.careme.android.model.event.RegEvent;
import kz.careme.android.modules.more.ActionPullABell;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient extends WebSocketListener {

    private Bus bus;
    private CareMeComponent careMeComponent;

    @Inject
    public WebSocketClient(Context context) {
        careMeComponent = ((CareMeApp) context.getApplicationContext()).getCareMeComponent();
        bus = careMeComponent.getBus();
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.d("CallService", "WebSocket Opened!");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.d("CallService", "Response: " + text);
        if (text.isEmpty()) {
            return;
        }
        if (bus == null) {
            bus = careMeComponent.getBus();
            return;
        }
        BaseAction action = new Gson().fromJson(text, BaseAction.class);
        switch (action.getAction()) {
            case ActionAuth.ACTION:
            case ActionAuthKid.ACTION:
                bus.post(new AuthEvent(new Gson().fromJson(text, ActionAuth.class)));
                break;

            case ActionRegisterChild.ACTION:
            case ActionRegister.ACTION:
                bus.post(new RegEvent(new Gson().fromJson(text, ActionRegister.class)));
                break;

            case ActionGenerateCode.ACTION:
                bus.post(new CodeGeneratedEvent(new Gson().fromJson(text, ActionGenerateCode.class)));
                break;

            case ActionActivateCode.ACTION:
                bus.post(new CodeActivatedEvent(new Gson().fromJson(text, ActionActivateCode.class)));
                break;
            case ActionGetMessage.ACTION:
                try {
                    bus.post(new MessageLoadedEvent(text));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case ActionKidList.ACTION:
                try {
                    bus.post(new KidListEvent(text));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case CheckCodeKidAction.ACTION:
                bus.post(new Gson().fromJson(text, CheckCodeKidEvent.class));
                break;
            case ActionSendMessage.ACTION:
                bus.post(new Gson().fromJson(text, MessageLoadedEvent.class));
                break;
            case ActionEditKid.ACTION:
                bus.post(new Gson().fromJson(text, KidEditedEvent.class));
                break;
            case ActionListenSound.ACTION:
                bus.post(new Gson().fromJson(text, ListenSoundEvent.class));
                break;
            case ActionSavePlace.ACTION:
                bus.post(new Gson().fromJson(text, PlaceAddedEvent.class));
                break;
            case ActionPullABell.ACTION:
                bus.post(new Gson().fromJson(text, PullABellEvent.class));
                break;
            case ActionStartListenSound.ACTION:
                bus.post(new Gson().fromJson(text, ActionStartListenSound.class));
                break;
            case ActionGetSound.ACTION:
                bus.post(new Gson().fromJson(text, ActionGetSound.class));
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
        if (bus != null) {
            bus.post(new ActionNeedReconnect());
            bus = null;
        }
    }

    @Override
    public void onFailure(final WebSocket webSocket, Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
        if (bus != null) {
            bus.post(new ActionNeedReconnect());
            bus = null;
        }
    }
}
