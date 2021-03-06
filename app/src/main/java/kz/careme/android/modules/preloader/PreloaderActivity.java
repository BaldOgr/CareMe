package kz.careme.android.modules.preloader;

import android.Manifest;
import android.app.AlertDialog;
import android.arch.core.executor.TaskExecutor;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import kz.careme.android.R;
import kz.careme.android.model.Const;
import kz.careme.android.modules.BaseActivity;
import kz.careme.android.modules.child_main.ChildMainActivity;
import kz.careme.android.modules.parent_main.MainActivity;
import kz.careme.android.modules.account_type.ChooseAccountTypeActivity;
import kz.careme.android.modules.account_type.WriteCodeActivity;

public class PreloaderActivity extends BaseActivity implements PreloaderView {

    private static final int REQUEST_PERMISSIONS = 2;
    @InjectPresenter
    PreloaderPresenter preloaderPresenter;
    private boolean mCheckedPermission = false;
    private boolean mAuth = false;
    private boolean mNeedAuth = false;
    private Class mActivityToStart;

    @ProvidePresenter
    PreloaderPresenter getPreloaderPresenter() {
        return new PreloaderPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader);
        preloaderPresenter.register();

        SharedPreferences preferences = getSharedPreferences(Const.SHARED_PREFERENCES, MODE_PRIVATE);
        String email = preferences.getString(Const.EMAIL, "");
        String password = preferences.getString(Const.PASSWORD, "");
        int accountType = preferences.getInt(Const.ACCOUNT_TYPE, Const.TYPE_PARENT);

        if (!email.isEmpty() && !password.isEmpty()) {
            if (accountType == Const.TYPE_PARENT) {
                preloaderPresenter.auth(email, password);
            } else {
                preloaderPresenter.authKid(email, password);
            }
        } else {
            mNeedAuth = true;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.RECORD_AUDIO,
            };
            int count = permissions.length;
            for (int i = 0; i < permissions.length; i++) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])
                        && ActivityCompat.checkSelfPermission(this, permissions[i]) == PackageManager.PERMISSION_GRANTED) {
                    permissions[i] = null;
                    count--;
                }
            }
            if (count > 0) {
                String[] permissionToRequest = new String[count];
                for (int i = 0, j = 0; i < permissions.length; i++) {
                    if (permissions[i] != null) permissionToRequest[j++] = permissions[i];
                }
                ActivityCompat.requestPermissions(this,
                        permissionToRequest,
                        REQUEST_PERMISSIONS);
            }
            return;
        }
        mCheckedPermission = true;
        if (mAuth) startActivity(new Intent(this, mActivityToStart));
        if (mNeedAuth && mCheckedPermission) {
            auth();
        }
    }

    private void auth() {
        startActivity(new Intent(this, ChooseAccountTypeActivity.class));
        preloaderPresenter.unsubscribe();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            final ArrayList<String> permissionsToRequest = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    permissionsToRequest.add(permissions[i]);
                }
            }
            if (permissionsToRequest.size() != 0) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.request_permissions)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .show();
            } else {
                mCheckedPermission = true;
                if (mAuth) {
                    startActivity(new Intent(this, mActivityToStart));
                } else if (mNeedAuth) {
                    auth();
                }
            }
        }
    }

    @Override
    public void startChooseAccountTypeActivity() {
        if (!mCheckedPermission) {
            mAuth = true;
            mActivityToStart = ChooseAccountTypeActivity.class;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    startChooseAccountTypeActivity();
                }
            }, 1000L);
            return;
        }
        startActivity(new Intent(this, ChooseAccountTypeActivity.class));
        finish();
        preloaderPresenter.unsubscribe();
    }

    @Override
    public void startMainActivity() {
        if (!mCheckedPermission) {
            mAuth = true;
            mActivityToStart = MainActivity.class;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    startMainActivity();
                }
            }, 1000L);
            return;
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
        preloaderPresenter.unsubscribe();
    }

    @Override
    public void startChildMainActivity(final int parentId, final int childId) {
        getSharedPreferences("CareMe", MODE_PRIVATE)
                .edit()
                .putInt(Const.PARENT_ID, parentId)
                .putInt(Const.CHILD_ID, childId)
                .apply();
        if (!mCheckedPermission) {
            mAuth = true;
            mActivityToStart = ChildMainActivity.class;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    startChildMainActivity(parentId, childId);
                }
            }, 1000L);
            return;
        }
        startActivity(new Intent(this, ChildMainActivity.class));
        finish();
        preloaderPresenter.unsubscribe();
    }

    @Override
    public void startWriteCodeActivity() {
        startActivity(new Intent(this, WriteCodeActivity.class).putExtra(Const.ACCOUNT_TYPE, Const.TYPE_CHILD));
    }
}
