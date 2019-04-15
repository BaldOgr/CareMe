package kz.careme.android.modules.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.arellomobile.mvp.MvpApplication;
import com.squareup.otto.Subscribe;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import kz.careme.android.CareMeApp;
import kz.careme.android.R;
import kz.careme.android.model.Account;
import kz.careme.android.model.CallService;
import kz.careme.android.model.Const;
import kz.careme.android.model.actions.ActionAuth;
import kz.careme.android.model.actions.ActionListenSound;
import kz.careme.android.model.actions.ActionSendGeo;
import kz.careme.android.model.actions.ActionStartListenSound;
import kz.careme.android.model.di.CareMeComponent;
import kz.careme.android.model.event.ListenSoundEvent;
import kz.careme.android.model.event.PullABellEvent;

public class MyService extends Service {
    MyBinder binder = new MyBinder();
    private Notification notification;
    private LocationManager locationManager;
    private Ringtone r;
    private Notification alarmNotification;
    private NotificationManager mNotificationManager;
    private int batteryLevel = -1;
    private Account account;
    private CallService callService;
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            batteryLevel = intent.getIntExtra("level", -1);
        }
    };

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CareMeComponent careMeComponent = ((CareMeApp) getApplicationContext()).getCareMeComponent();
        careMeComponent.getBus().register(this);
        account = careMeComponent.getProfiler().getAccount();
        callService = careMeComponent.getCallService();

        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.textView, "CareMe");
        String channelId = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = createNotificationChanel("my_service", "CareMeApp", NotificationManager.IMPORTANCE_HIGH);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notification = new NotificationCompat.Builder(this, channelId)
                    .setLocalOnly(true)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
        } else {
            notification = new NotificationCompat.Builder(this, channelId)
                    .setLocalOnly(true)
                    .setSmallIcon(R.drawable.ic_notification)
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
            this.registerReceiver(mBatInfoReceiver,
                    new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 100, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Account account = ((CareMeApp) getApplicationContext()).getCareMeComponent().getProfiler().getAccount();
                    if (account == null || account.getSid().isEmpty()) {
                        return;
                    }
                    if (account.getRole() == Const.TYPE_PARENT) {
                        locationManager.removeUpdates(this);
                        return;
                    }
                    ActionSendGeo sendGeo = new ActionSendGeo();
                    sendGeo.setLatitude(location.getLatitude());
                    sendGeo.setLongitude(location.getLongitude());
                    sendGeo.setSpeed(location.getSpeed());
                    sendGeo.setAccuracy(location.getAccuracy());
                    sendGeo.setTime(location.getTime());
                    sendGeo.setSessionId(account.getSid());
                    if (batteryLevel != -1) {
                        sendGeo.setBatteryLevel(String.valueOf(batteryLevel));
                    }
                    ((CareMeApp) getApplicationContext()).getCareMeComponent().getCallService().call(sendGeo);
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


    @Subscribe
    public void listenSound(ListenSoundEvent event) {
        if (((CareMeApp) getApplicationContext()).getCareMeComponent().getProfiler().getAccount().getRole() == Const.TYPE_CHILD) {
            Toast.makeText(this, "ListenSound Event!", Toast.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void pullABell(PullABellEvent event) {
        if (((CareMeApp) getApplicationContext()).getCareMeComponent().getProfiler().getAccount().getRole() == Const.TYPE_CHILD) {
            String channelId = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channelId = createNotificationChanel("my_service", "CareMeApp", NotificationManager.IMPORTANCE_HIGH);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                alarmNotification = new NotificationCompat.Builder(this, channelId)
                        .setLocalOnly(true)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentText(getText(R.string.send_signal_notification))
                        .setPriority(NotificationManager.IMPORTANCE_HIGH)
                        .setCategory(Notification.CATEGORY_SERVICE)
                        .setContentIntent(PendingIntent.getService(this,
                                1,
                                new Intent(this, MyService.class).setAction(Const.ACTION_DISABLE_ALARM),
                                0))
                        .build();
            } else {
                alarmNotification = new NotificationCompat.Builder(this, channelId)
                        .setLocalOnly(true)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentText(getText(R.string.send_signal_notification))
                        .setCategory(Notification.CATEGORY_SERVICE)
                        .setContentIntent(PendingIntent.getService(this,
                                1,
                                new Intent(this, MyService.class).setAction(Const.ACTION_DISABLE_ALARM),
                                0))
                        .build();
            }
            try {
                Uri notify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                r = RingtoneManager.getRingtone(getApplicationContext(), notify);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(101, alarmNotification);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return START_STICKY;

        if (Const.ACTION_DISABLE_ALARM.equalsIgnoreCase(intent.getAction())) {
            r.stop();
            mNotificationManager.cancel(101);
        }
        return START_STICKY;
    }

    @Subscribe
    public void showNotification(ActionAuth actionAuth) {
    }

    @Subscribe
    public void recordSound(final ActionStartListenSound actionListenSound) {
        Account account = ((CareMeApp) getApplication()).getCareMeComponent().getProfiler().getAccount();
        if (account == null || (account.getAccountType() != Const.TYPE_CHILD && actionListenSound.getKidId() != account.getId()))
            return;

        final String fileName = getExternalCacheDir().getAbsolutePath() + "/record.3gp";
        final MediaRecorder mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(fileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    sendAudio(fileName, actionListenSound.getId());
                }
            }, 30 * 1000);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendAudio(String fileName, long id) {
        FTPClient ftpClient = new FTPClient();
        BufferedInputStream buffIn;
        try {
            ftpClient.connect("195.93.152.96");
            ftpClient.login("ftpuser", "Open111");
            ftpClient.changeWorkingDirectory("/html/server/sound/");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            buffIn = new BufferedInputStream(new FileInputStream(fileName));
            ftpClient.enterLocalPassiveMode();
            String uuid = UUID.randomUUID().toString();
            ftpClient.storeFile(uuid, buffIn);
            buffIn.close();
            sendAudioIsSended(uuid, id);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendAudioIsSended(String uuid, long id) {
        if (account == null) {
            account = ((CareMeApp) getApplication()).getCareMeComponent().getProfiler().getAccount();
        }
        ActionListenSound listenSound = new ActionListenSound();
        listenSound.setKidSessionId(account.getSid());
        listenSound.setId(id);
        listenSound.setFile(uuid);
        callService.call(listenSound);
    }

    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }

    }
}
