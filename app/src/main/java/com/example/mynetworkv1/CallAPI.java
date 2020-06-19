package com.example.mynetworkv1;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.JsonReader;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public final class CallAPI extends AsyncTask<Void, Void, ArrayList<Trader>> {

    // Defined a general task that extends AsyncTask to then call/run API calls
    private int responseCode;
    private String APIendpoint;
    private String APIarguments;
    Handler UIHandler;
    private ArrayList<Trader> returnObject;

    public CallAPI(Handler handler, String endpoint, String arguments) {
        // Constructor - allows us to read what the main activity currently is
        this.UIHandler = handler;
        this.APIendpoint = endpoint;
        this.APIarguments = arguments;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Trader> doInBackground(Void... params) {
        // Create API object to work with the API
        ZortexAPI zortexAPI = new ZortexAPI();
        returnObject = new ArrayList<Trader>();
        // Get the list of traders using getTraders function
        switch (APIendpoint){
            case "trader" :
                returnObject.add(zortexAPI.getTrader(APIarguments));
                break;
            case "traders" :
                returnObject = zortexAPI.getTraders();
                break;
        }
        return returnObject;
    }

    @Override
    protected void onPostExecute(ArrayList<Trader> returnObject) {
        Message response;
        if(returnObject != null){
            responseCode = 200;
        }else{
            responseCode = 500;
        }
        response = UIHandler.obtainMessage(responseCode, returnObject);
        response.sendToTarget();

    }
}
