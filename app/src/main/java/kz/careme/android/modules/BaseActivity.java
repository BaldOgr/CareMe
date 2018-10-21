package kz.careme.android.modules;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import kz.careme.android.CareMeApp;
import kz.careme.android.model.Profiler;

public abstract class BaseActivity extends AppCompatActivity {
    @Inject
    protected Profiler profiler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profiler = ((CareMeApp) getApplicationContext()).getProfilerComponent().getProfiler();
        Log.d(this.getClass().getSimpleName(), profiler.getName());
    }

    protected void initializeActionBar(boolean enableHomeButton, String title) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(enableHomeButton);
        getSupportActionBar().setDisplayShowHomeEnabled(enableHomeButton);
        getSupportActionBar().setTitle(title);
    }
}
