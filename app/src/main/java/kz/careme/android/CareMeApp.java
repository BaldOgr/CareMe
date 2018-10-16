package kz.careme.android;

import android.app.Application;

import kz.careme.android.model.di.DaggerProfilerComponent;
import kz.careme.android.model.di.ProfilerComponent;

public class CareMeApp extends Application {
    private ProfilerComponent profilerComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        profilerComponent = DaggerProfilerComponent.create();
        profilerComponent.getProfiler().setName("123 Test");
    }

    public ProfilerComponent getProfilerComponent() {
        return profilerComponent;
    }
}
