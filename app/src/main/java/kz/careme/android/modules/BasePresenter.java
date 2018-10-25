package kz.careme.android.modules;

import android.content.Context;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import kz.careme.android.CareMeApp;
import kz.careme.android.model.CallService;
import kz.careme.android.model.Profiler;

public abstract class BasePresenter<T extends MvpView> extends MvpPresenter<T> {
    private Profiler profiler;
    private CallService callService;

    public BasePresenter() {
        profiler = CareMeApp.getCareMeComponent().getProfiler();
        callService = CareMeApp.getCareMeComponent().getCallService();
        CareMeApp.getCareMeComponent().getBus().register(this);
    }

    public Profiler getProfiler() {
        return profiler;
    }

    public CallService getCallService() {
        return callService;
    }
}
