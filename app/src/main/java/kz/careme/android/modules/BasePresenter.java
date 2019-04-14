package kz.careme.android.modules;

import android.content.Context;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import com.squareup.otto.Bus;

import kz.careme.android.CareMeApp;
import kz.careme.android.model.CallService;
import kz.careme.android.model.Profiler;

public abstract class BasePresenter<T extends MvpView> extends MvpPresenter<T> {
    private Profiler profiler;
    private CallService callService;
    private Bus bus;
    private Context context;

    public BasePresenter(Context context) {
        profiler = ((CareMeApp) context.getApplicationContext()).getCareMeComponent().getProfiler();
        callService = ((CareMeApp) context.getApplicationContext()).getCareMeComponent().getCallService();

        bus = ((CareMeApp) context.getApplicationContext()).getCareMeComponent().getBus();
        bus.register(this);
        this.context = context;
    }

    public Profiler getProfiler() {
        return profiler;
    }

    public CallService getCallService() {
        return callService;
    }

    protected Bus getBus() {
        return bus;
    }

    protected Context getContext() {
        return context;
    }
}
