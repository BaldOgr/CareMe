package kz.careme.android.model.di;

import android.content.Context;

import com.squareup.otto.Bus;

import dagger.BindsInstance;
import dagger.Component;
import kz.careme.android.model.CallService;
import kz.careme.android.model.Profiler;
import kz.careme.android.model.websocket.WebSocketClient;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

@Component(modules = CareMeModule.class)
@SingletonScope
public interface CareMeComponent {
    Profiler getProfiler();
    OkHttpClient getOkHttpClient();
    CallService getCallService();
    Bus getBus();


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(Context context);
        CareMeComponent build();
    }
}
