package com.example.mynetworkv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import java.util.regex.*;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> myDataset = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        (new GetTraders()).execute();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset, this.getApplicationContext());
        recyclerView.setAdapter(mAdapter);
    }

    private final class GetTraders extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // Create URL
            URL zortexTraders = null;
            HttpsURLConnection myConnection = null;

            // Create connection
            try {
                zortexTraders = new URL("https://www.zortex.co.uk/_functions-dev/traders");
                myConnection =
                        (HttpsURLConnection) zortexTraders.openConnection();
                myConnection.setRequestProperty("User-Agent", "mynetworkv1");
                Log.d("tradersAPI", "response code is "+myConnection.getResponseCode());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (myConnection.getResponseCode() == 200) {
                    // Success
                    // Further processing here
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader =
                            new InputStreamReader(responseBody, "UTF-8");
                    JsonReader jsonReader = new JsonReader(responseBodyReader);


                    jsonReader.beginObject(); // Start processing the JSON object
                    while (jsonReader.hasNext()) { // Loop through all keys

                        Log.d("traders", "Found object " + jsonReader.nextName()); // Fetch the next key

                        jsonReader.beginArray();
                        while (jsonReader.hasNext()){
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()) {
                                String key = jsonReader.nextName();
                                Log.d("traders", "Checking key " + key);
                                if (key.equals("card")) { // Check if desired key
                                    // Fetch the value as a String
                                    String cardValue = jsonReader.nextString();
                                    Log.d("traders", "Found title " + cardValue);
                                    // Do something with the value
                                    // ...
                                    Pattern pattern = Pattern.compile("\\/v1\\/(\\S+)\\/");
                                    Matcher matcher = pattern.matcher(cardValue);
                                    // "\\\\/v1\\\\/(\\S+)\\\\/" regex
                                    if(matcher.find()){
                                        Log.d("traders","Found match"+matcher.group(1));
                                        myDataset.add("https://static.wixstatic.com/media/"+matcher.group(1));
                                    }


                                } else {
                                    jsonReader.skipValue(); // Skip values of other keys
                                }

                            }
                            jsonReader.endObject();
                        }
                        jsonReader.endArray();
                    }
                    jsonReader.endObject();

                    jsonReader.close();
                    myConnection.disconnect();

                } else {
                    // Error handling code goes here
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Success";
        }

        @Override
        protected void onPostExecute(String result) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
