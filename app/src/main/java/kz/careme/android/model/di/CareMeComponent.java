package kz.careme.android.model.di;

import com.squareup.otto.Bus;

import dagger.Component;
import kz.careme.android.model.CallService;
import kz.careme.android.model.Profiler;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

@Component(modules = CareMeModule.class)
@SingletonScope
public interface CareMeComponent {
    Profiler getProfiler();
    WebSocket getWebSocketClient();
    OkHttpClient getOkHttpClient();
    CallService getCallService();
    Bus getBus();
}
