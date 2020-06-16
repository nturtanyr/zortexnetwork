package com.example.mynetworkv1;

import android.util.JsonReader;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class ZortexAPI {
    // Creating a zortexAPI object to organise connection functions and session data

    // Defining public connection variable that can be contacted for session information
    public HttpsURLConnection connection;

    // API URIs
    private String zortexApi_Root = "https://www.zortex.co.uk/_functions/";
    private String zortexApi_traders_endpoint = "traders";
    private String zortexApi_trader_endpoint = "trader";
    private String zortexApi_userAgent = "mynetworkApp";

    // This is the constructor - not much going on here
    public void ZortexApi () {
        Log.d("ZORTEXAPI","Creating API Object");
    }

    // Function for pulling the trader information
    public ArrayList<Trader> getTraders ()
    {
        ArrayList<Trader> traderList = new ArrayList<Trader>();
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

                    Log.d("traders", "Found object " + jsonReader.nextName()); // Fetch the next key

                    // We know the first object is "items", which is an array of the traders, so here we loop through them
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()){
                        // For each trader we define a new object (but don't store it. We can do this later
                        Trader traderObject = new Trader();
                        traderObject.readTrader(jsonReader);

                        // In the meantime, we add the card URL to the list of strings
                        traderList.add(traderObject);
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

        return traderList;
    }

    // Function for pulling a specific trader's information
    public Trader getTrader (String id)
    {
        Trader traderObject = null;
        // Create connection
        try {
            // Defines a URL using the API URL
            URL getTrader_URL = new URL(zortexApi_Root + zortexApi_trader_endpoint + "/" + id);
            Log.d("ZORTEXAPI","Contacting " + zortexApi_Root + zortexApi_trader_endpoint + "/" + id);

            // Then attempts to open the connection and assigns to this object's connection variable for later use
            this.connection = (HttpsURLConnection) getTrader_URL.openConnection();

            // Sets the UserAgent (so we can track who uses our API)
            this.connection.setRequestProperty("User-Agent", zortexApi_userAgent);
            Log.d("ZORTEXAPI","Returned " + connection.getResponseCode() + " response code");

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

                    Log.d("traders", "Found object " + jsonReader.nextName()); // Fetch the next key

                    // We know the first object is "items", which is an array of the traders, so here we loop through them
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()){
                        // For each trader we define a new object (but don't store it. We can do this later
                        traderObject = new Trader();
                        traderObject.readTrader(jsonReader);
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

        return traderObject;
    }
}
