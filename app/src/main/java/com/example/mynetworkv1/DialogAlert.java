package com.example.mynetworkv1;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public abstract class DialogAlert {

    public static void showAPIAlert(final Context context){
        AlertDialog issueWithAPI = new AlertDialog.Builder(context).create();
        issueWithAPI.setTitle("ERROR");
        issueWithAPI.setMessage(context.getResources().getString(R.string.network_error_message));
        issueWithAPI.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        issueWithAPI.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                ((Activity)context).finish();
            }
        });
        issueWithAPI.show();
    }
}
