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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public final class CallCategoryEndpoint extends AsyncTask<Void, Void, ArrayList<TraderCategory> >{

    private String APIendpoint;
    Handler UIHandler;
    private HttpsURLConnection connection;

    // API URIs
    private String zortexApi_Root = "https://www.zortex.co.uk/_functions/";
    private String zortexApi_category_endpoint = "category";
    private String zortexApi_userAgent = "mynetworkApp";

    public CallCategoryEndpoint(Handler handler, String endpoint) {
        // Constructor - allows us to read what the main activity currently is
        this.UIHandler = handler;
        this.APIendpoint = endpoint;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<TraderCategory> doInBackground(Void... params) {
        // Create API object to work with the API
        ArrayList<TraderCategory> returnObject = new ArrayList<TraderCategory>();
        // Get the list of traders using getTraders function
        switch (APIendpoint){
            case "category" :
                returnObject = getTraderCategories();
                break;
        }
        return returnObject;
    }

    @Override
    protected void onPostExecute(ArrayList<TraderCategory> returnObject) {
        Message response;
        // Defined a general task that extends AsyncTask to then call/run API calls
        int responseCode;
        if(returnObject != null){
            responseCode = 200;
        }else{
            responseCode = 500;
        }
        response = UIHandler.obtainMessage(responseCode, returnObject);
        response.sendToTarget();
    }

    // Function for pulling a specific trader's information
    // TODO Add a new HTTP API to change how the endpoint is pulled out
    private ArrayList<TraderCategory> getTraderCategories ()
    {
        ArrayList<TraderCategory> categoryList  = new ArrayList<TraderCategory>();
        // Create connection
        try {
            // Defines a URL using the API URL
            URL getTrader_URL = new URL(zortexApi_Root + zortexApi_category_endpoint);
            Log.d("ZORTEXAPI","Contacting " + zortexApi_Root + zortexApi_category_endpoint);

            // Then attempts to open the connection and assigns to this object's connection variable for later use
            this.connection = (HttpsURLConnection) getTrader_URL.openConnection();

            // Sets the UserAgent (so we can track who uses our API)
            this.connection.setRequestProperty("User-Agent", zortexApi_userAgent);
            Log.d("ZORTEXAPI","Returned " + this.connection.getResponseCode() + " response code");

            // Returns with a responsecode
            if (connection.getResponseCode() == 200) {
                // Success
                // Processing input stream from the zortexApi connection
                InputStream responseBody = this.connection.getInputStream();
                InputStreamReader responseBodyReader =
                        new InputStreamReader(responseBody, StandardCharsets.UTF_8);
                JsonReader jsonReader = new JsonReader(responseBodyReader);

                // Starting to process the JSON
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {

                    Log.d("ZORTEXAPI", "Found object " + jsonReader.nextName()); // Fetch the next key

                    // We know the first object is "items", which is an array of the traders, so here we loop through them
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()){
                        // For each trader we define a new object but don't store it. We can do this later
                        TraderCategory categoryObject = new TraderCategory().readTraderCategory(jsonReader);
                        categoryList.add(categoryObject);
                    }
                    jsonReader.endArray();
                }
                jsonReader.endObject();

                // Closing everything up - gotta be clean
                jsonReader.close();
                connection.disconnect();

            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return categoryList;
    }
}
