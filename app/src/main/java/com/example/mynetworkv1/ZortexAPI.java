package com.example.mynetworkv1;

import android.content.Context;
import android.util.Log;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static android.provider.Settings.System.getString;

public class ZortexAPI {
    // Creating a zortexAPI object to organise connection functions and session data

    // Defining public connection variable that can be contacted for session information
    public HttpsURLConnection connection;

    // API URIs
    private String zortexApi_Root = "https://www.zortex.co.uk/_functions/";
    private String zortexApi_traders_endpoint = "traders";
    private String zortexApi_userAgent = "mynetworkApp";

    // This is the constructor - not much going on here
    public void ZortexApi () {
        Log.d("ZORTEXAPI","Creating API Object");
    }

    // Function for pulling the trader information
    public int getTraders ()
    {
        // Create connection
        try {
            // Defines a URL using the API URL
            URL getTrader_URL = new URL(zortexApi_Root + zortexApi_traders_endpoint);
            Log.d("ZORTEXAPI","Contacting " + zortexApi_Root + zortexApi_traders_endpoint);

            // Then attempts to open the connection and assigns to this object's connection variable for later use
            this.connection = (HttpsURLConnection) getTrader_URL.openConnection();

            // Sets the UserAgent (so we can track who uses our API)
            this.connection.setRequestProperty("User-Agent", zortexApi_userAgent);
            Log.d("ZORTEXAPI","Returned " + connection.getResponseCode() + " response code");

            // Returns with a responsecode
            return connection.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();

        }

        return 0;
    }
}
