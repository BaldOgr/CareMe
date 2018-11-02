package kz.careme.android;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

import kz.careme.android.model.di.CareMeComponent;
import kz.careme.android.model.di.DaggerCareMeComponent;

public class CareMeApp extends Application {
    private static CareMeComponent CareMeComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        CareMeComponent = DaggerCareMeComponent.builder().build();
        MapKitFactory.setApiKey("AJwEzlsBAAAAVbI9FQIAtNVfr1lKD_MYsfpcQfi19IZ6g_sAAAAAAAAAAABApF6cJiihWBLEMCnkbrefktp72g==");

    }

    public static CareMeComponent getCareMeComponent() {
        return CareMeComponent;
    }
}
