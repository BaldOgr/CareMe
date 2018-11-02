package kz.careme.android.modules;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.MvpAppCompatActivity;

import javax.inject.Inject;

import kz.careme.android.CareMeApp;
import kz.careme.android.model.Profiler;
import kz.careme.android.modules.service.MyService;

public abstract class BaseActivity extends MvpAppCompatActivity {

    protected MyService myService;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Activity", this.getClass().getSimpleName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, MyService.class));
        } else {
            startService(new Intent(this, MyService.class));
        }
        if (CareMeApp.getCareMeComponent().getCallService().getService() == null) {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    myService = ((MyService.MyBinder) service).getService();
                    CareMeApp.getCareMeComponent().getCallService().setMyService(myService);
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
            bindService(new Intent(this, MyService.class), serviceConnection, BIND_AUTO_CREATE);
        }
    }

    protected void initializeActionBar(boolean enableHomeButton, String title) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(enableHomeButton);
        getSupportActionBar().setDisplayShowHomeEnabled(enableHomeButton);
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceConnection != null) unbindService(serviceConnection);
    }
}
