package kz.careme.android.model.dialog_util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import kz.careme.android.R;

public class DialogUtil {
    private static HashMap<Activity, AlertDialog> alertDialogHashMap = new HashMap<>();

    public static void showDialog(Activity activity, String text) {
        if (!activity.isFinishing()) {
            View view = LayoutInflater.from(activity).inflate(R.layout.dialog_loader, null);
            AlertDialog alertDialog = new AlertDialog.Builder(activity)
                    .setView(view)
                    .setCancelable(false)
                    .create();
            ((TextView) view.findViewById(R.id.dialog_text)).setText(text);
            alertDialog.show();
            alertDialogHashMap.put(activity, alertDialog);
        }
    }

    public static void closeDialog(Activity activity) {
        if (alertDialogHashMap.containsKey(activity)) {
            alertDialogHashMap.get(activity).dismiss();
            alertDialogHashMap.remove(activity);
        }
    }
}
