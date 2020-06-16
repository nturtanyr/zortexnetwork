package com.example.mynetworkv1;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.util.JsonReader;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

final class GetTraders extends AsyncTask<Void, Void, String> {
    //This object works in the background before working with the main UI thread
    private final Context currentContext;
    CardListAdapter cardListAdapter;

    // This alert is so if there's a network issue, it throws an error
    AlertDialog issueWithAPI;
    int responsecode;

    public GetTraders(Context context, CardListAdapter cardListAdapter) {
        // Constructor - allows us to read what the main activity currently is
        this.currentContext = context;
        this.cardListAdapter = cardListAdapter;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        // Before running my async code, create the alert dialog object
        issueWithAPI = new AlertDialog.Builder(currentContext).create();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(Void... params) {
        ArrayList<Trader> traderList = new ArrayList<Trader>();
        // Create API object to work with the API
        ZortexAPI zortexAPI = new ZortexAPI();
        // Get the response code of the getTraders function
        traderList = zortexAPI.getTraders();
        if(traderList.size() > 0) {
            cardListAdapter.traderList = traderList;
            return "Success";
        } else {
            return "No traders found";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(result == "No traders found"){
            // There was an error! So we throw the alert dialog;
            issueWithAPI.setTitle("ERROR");
            issueWithAPI.setMessage(currentContext.getResources().getString(R.string.tab_text_1));
            issueWithAPI.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            issueWithAPI.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog){
                    ((Activity)currentContext).finishAffinity();
                }
            });
            issueWithAPI.show();
        }
        else if (result == "Success"){
            // Else, update the cardListAdapter to say that the cards should be populated!
            cardListAdapter.notifyDataSetChanged();
        }else {
            Log.w("ZORTEXAPI", "Problem translating number of traders");
        }
    }
}
