package kz.careme.android.modules.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.squareup.otto.Subscribe;

import kz.careme.android.CareMeApp;
import kz.careme.android.R;
import kz.careme.android.model.actions.ActionAuth;

public class MyService extends Service {
    MyBinder binder = new MyBinder();
    Notification notification;

    public MyService() {
        CareMeApp.getCareMeComponent().getBus().register(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(),  R.layout.notification);
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

    public void sendMessage(String message) {
        CareMeApp.getCareMeComponent().getWebSocketClient().send(message);
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
