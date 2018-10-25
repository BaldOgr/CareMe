package kz.careme.android.model.di;

import android.content.Context;

import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;
import kz.careme.android.model.CallService;
import kz.careme.android.model.Profiler;
import kz.careme.android.model.websocket.WebSocketClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

@Module
public class CareMeModule {
    private Context context;

    @Provides
    @SingletonScope
    Profiler getProfiler() {
        return new Profiler();
    }

    @Provides
    @SingletonScope
    WebSocketClient getWebSocketClient() {
        return new WebSocketClient();
    }

    @Provides
    @SingletonScope
    OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @SingletonScope
    WebSocket getWebSocket(OkHttpClient client, WebSocketClient webSocketClient) {
        Request request = new Request.Builder().url("ws://195.93.152.96:11210").build();
        return client.newWebSocket(request, webSocketClient);
    }

    @Provides
    Context getContext() {
        return context;
    }

    @Provides
    @SingletonScope
    CallService getCallService(Context context) {
        return new CallService(context);
    }

    @Provides
    @SingletonScope
    Bus getBus() {
        return new Bus();
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
