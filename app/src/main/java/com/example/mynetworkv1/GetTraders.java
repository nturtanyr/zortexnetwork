package com.example.mynetworkv1;

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

final class GetTraders extends AsyncTask<Void, Void, String> {
    //This object works in the background before working with the main UI thread
    private final MainActivity mainActivity;

    // This alert is so if there's a network issue, it throws an error
    AlertDialog issueWithAPI;
    int responsecode;

    public GetTraders(MainActivity mainActivity) {
        // Constructor - allows us to read what the main activity currently is
        this.mainActivity = mainActivity;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        // Before running my async code, create the alert dialog object
        issueWithAPI = new AlertDialog.Builder(mainActivity).create();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(Void... params) {
        // Create API object to work with the API
        ZortexAPI zortexAPI = new ZortexAPI();
        // Get the response code of the getTraders function
        responsecode = zortexAPI.getTraders();
        Log.d("ZORTEXAPI","Returned " + responsecode + " to main activity");

        try {
            if (responsecode == 200) {
                // Success
                // Processing input stream from the zortexApi connection
                InputStream responseBody = zortexAPI.connection.getInputStream();
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
                        //TODO Store the trader objects in an object array so when we select a card we can isntantly read information about that trader and construct the new activity
                        Trader traderObject = new Trader();
                        traderObject.defineTrader(jsonReader);

                        // In the meantime, we add the card URL to the list of strings
                        mainActivity.traderList.add(traderObject);
                    }
                    jsonReader.endArray();
                }
                jsonReader.endObject();

                // Closing everything up - gotta be clean
                jsonReader.close();
                zortexAPI.connection.disconnect();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if(responsecode != 200){
            // There was an error! So we throw the alert dialog;
            issueWithAPI.setTitle("ERROR");
            issueWithAPI.setMessage("There was an issue speaking to the ZORTEX server; please ensure you have a working network connection.");
            issueWithAPI.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            issueWithAPI.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog){
                    mainActivity.finishAffinity();
                }
            });
            issueWithAPI.show();
        }
        else {
            // Else, update the main UI thread to say that the cards should be populated!
            mainActivity.cardListAdapter.notifyDataSetChanged();
        }
    }
}
