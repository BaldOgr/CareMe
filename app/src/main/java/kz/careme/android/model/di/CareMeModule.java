package kz.careme.android.model.di;

import android.content.Context;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import kz.careme.android.model.CallService;
import kz.careme.android.model.Profiler;
import okhttp3.OkHttpClient;

@Module
public class CareMeModule {

    @Provides
    @SingletonScope
    Profiler getProfiler() {
        return new Profiler();
    }

    @Provides
    @SingletonScope
    OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder().connectTimeout(1, TimeUnit.SECONDS).build();
    }

    @Provides
    @SingletonScope
    CallService getCallService(OkHttpClient client, Context context) {
        return new CallService(client, context);
    }

    @Provides
    @SingletonScope
    Bus getBus() {
        return new Bus(ThreadEnforcer.ANY);
    }
}
