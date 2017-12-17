package com.example.prygoon.testmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.widget.Toast;

public class MapApplication extends Application {
    private static int backPressedCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void exit(final Activity activity) {
        if (++backPressedCount > 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(R.string.close_app);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    backPressedCount = 0;
                    activity.finishAffinity();
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    backPressedCount = 0;
                    dialogInterface.cancel();
                }
            });
            builder.create().show();
        } else {
            Toast.makeText(activity, R.string.back_pressed_once, Toast.LENGTH_SHORT).show();
        }
    }
}
