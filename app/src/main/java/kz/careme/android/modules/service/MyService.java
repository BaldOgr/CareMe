package kz.careme.android.modules.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.squareup.otto.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

import kz.careme.android.CareMeApp;
import kz.careme.android.R;
import kz.careme.android.model.Account;
import kz.careme.android.model.Const;
import kz.careme.android.model.actions.ActionAuth;
import kz.careme.android.model.actions.ActionSendGeo;
import kz.careme.android.model.di.CareMeModule;

public class MyService extends Service {
    MyBinder binder = new MyBinder();
    private Notification notification;
    private LocationManager locationManager;

    public MyService() {
        CareMeApp.getCareMeComponent().getBus().register(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.textView, "CareMe");
        String channelId = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = createNotificationChanel("my_service", "CareMeApp", NotificationManager.IMPORTANCE_HIGH);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notification = new NotificationCompat.Builder(this, channelId)
                    .setLocalOnly(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
        } else {
            notification = new NotificationCompat.Builder(this, channelId)
                    .setLocalOnly(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
        }
        startForeground(100, notification);
        int accountType = getSharedPreferences(Const.SHARED_PREFERENCES, MODE_PRIVATE)
                .getInt(Const.ACCOUNT_TYPE, Const.TYPE_PARENT);
        if (accountType == Const.TYPE_CHILD) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (locationManager != null) {
                return;
            }
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 100, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Account account = CareMeApp.getCareMeComponent().getProfiler().getAccount();
                    if (account == null || account.getSid().isEmpty()) {
                        return;
                    }
                    ActionSendGeo sendGeo = new ActionSendGeo();
                    sendGeo.setLatitude(location.getLatitude());
                    sendGeo.setLongitude(location.getLongitude());
                    sendGeo.setSpeed(location.getSpeed());
                    sendGeo.setAccuracy(location.getAccuracy());
                    sendGeo.setTime(location.getTime());
                    sendGeo.setSessionId(account.getSid());
                    CareMeApp.getCareMeComponent().getCallService().call(sendGeo);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.O)
    private String createNotificationChanel(String id, String name, int priority) {
        NotificationChannel channel = new NotificationChannel(id, name, priority);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        return channel.getId();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Subscribe
    public void showNotification(ActionAuth actionAuth) {
    }

    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }

    }
}
