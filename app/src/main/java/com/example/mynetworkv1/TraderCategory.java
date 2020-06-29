package com.example.mynetworkv1;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TraderCategory {
    public String name;
    public String id;


    public void TraderCategory()
    {
        // TODO: Fill this out to assign all variables with a dictionary or something
    }

    public TraderCategory readTraderCategory(JsonReader jsonReader)
    {
        // This contructs the Trader object using the JSON that's given by the API - had to use a JsonReader right now but could be better with something else more generic
        try {
            // Start of object
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String key = jsonReader.nextName();
                Log.d("traders", "Checking key " + key);

                // Depending what key it is, it gets assigned to a string variable
                switch (key) {
                    case "name":
                        this.name = jsonReader.nextString();
                        break;
                    case "_id":
                        this.id = jsonReader.nextString();
                        break;
                    default:
                        jsonReader.skipValue(); // Skip values of other keys
                        break;
                }

            }
            jsonReader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Then it jsut returns the object that can be further acted upon
        return this;
    }
}
