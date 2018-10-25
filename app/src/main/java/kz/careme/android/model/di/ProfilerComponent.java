package kz.careme.android.model.di;

import javax.inject.Singleton;

import dagger.Component;
import kz.careme.android.model.CallService;
import kz.careme.android.model.Profiler;
import kz.careme.android.model.websocket.WebSocketClient;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

@Component(modules = ProfileModule.class)
@SingletonScope
public interface ProfilerComponent {
    Profiler getProfiler();
    WebSocket getWebSocketClient();
    OkHttpClient getOkHttpClient();
    CallService getCallService();
}
