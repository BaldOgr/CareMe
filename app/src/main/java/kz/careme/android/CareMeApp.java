package kz.careme.android;

import android.app.Application;
import android.content.Intent;
import android.os.Build;

import kz.careme.android.model.di.CareMeModule;
import kz.careme.android.model.di.DaggerCareMeComponent;
import kz.careme.android.model.di.CareMeComponent;
import kz.careme.android.modules.service.MyService;

public class CareMeApp extends Application {
    private static CareMeComponent CareMeComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        CareMeModule meModule = new CareMeModule();
        meModule.setContext(this);
        CareMeComponent = DaggerCareMeComponent.builder().careMeModule(meModule).build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, MyService.class));
        } else {
            startService(new Intent(this, MyService.class));
        }
    }

    public static CareMeComponent getCareMeComponent() {
        return CareMeComponent;
    }
}
