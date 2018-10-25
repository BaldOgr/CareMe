package kz.careme.android.model.di;

import dagger.Module;
import dagger.Provides;
import kz.careme.android.model.CallService;
import kz.careme.android.model.Profiler;
import kz.careme.android.model.websocket.WebSocketClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

@Module
class ProfileModule {
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
    @SingletonScope
    CallService getCallService(WebSocket webSocket, WebSocketClient webSocketClient) {
        return new CallService(webSocket, webSocketClient);
    }

}
