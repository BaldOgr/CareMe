package kz.careme.android.modules;

import android.content.Context;

import kz.careme.android.CareMeApp;
import kz.careme.android.model.CallService;
import kz.careme.android.model.Profiler;
import okhttp3.WebSocket;

public abstract class BasePresenter {
    private CallService callService;
    private Context context;
    private Profiler profiler;

    public BasePresenter(Context context) {
        this.context = context;
        callService = ((CareMeApp) context.getApplicationContext()).getProfilerComponent().getCallService();
        profiler = ((CareMeApp) context.getApplicationContext()).getProfilerComponent().getProfiler();
    }

    public abstract void onMessage(String text);

    protected CallService getCallService() {
        return callService;
    }

    public Context getContext() {
        return context;
    }

    public Profiler getProfiler() {
        return profiler;
    }
}
