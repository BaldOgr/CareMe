package kz.careme.android;

import android.app.Application;

import com.squareup.picasso.Picasso;
import com.yandex.mapkit.MapKitFactory;

import kz.careme.android.model.di.CareMeComponent;
import kz.careme.android.model.di.DaggerCareMeComponent;

public class CareMeApp extends Application {
    private static CareMeComponent CareMeComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        MapKitFactory.setApiKey("be6291e7-21f3-4246-897a-53727fdec2c2");
        Picasso.setSingletonInstance(new Picasso.Builder(this).indicatorsEnabled(true).build());
    }

    public static CareMeComponent getCareMeComponent() {
        if (CareMeComponent == null) {
            CareMeComponent = DaggerCareMeComponent.builder().build();
        }
        return CareMeComponent;
    }
}
