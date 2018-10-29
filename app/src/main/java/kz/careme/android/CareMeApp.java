package kz.careme.android;

import android.app.Application;

import kz.careme.android.model.di.CareMeComponent;
import kz.careme.android.model.di.DaggerCareMeComponent;

public class CareMeApp extends Application {
    private static CareMeComponent CareMeComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        CareMeComponent = DaggerCareMeComponent.builder().build();
    }

    public static CareMeComponent getCareMeComponent() {
        return CareMeComponent;
    }
}
