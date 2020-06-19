package com.example.mynetworkv1;

import android.util.Log;
import android.util.JsonReader;

import java.io.IOException;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Trader {
    // Defining a Trader object for use to collect and organise trader information
    // Just a bunch of strings for now
    public String name;
    public String id;
    public String email;
    public String phone;
    public String city;
    public String subdivision;
    public String country;
    public String formatedAddress;
    public String description;
    public String logo_URL;
    public String card_URL;

    public void Trader()
    {
        //TODO: Fill this out to assign all variables with a dictionary or something
    }

    public Trader readTrader(JsonReader jsonReader)
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
                    case "title":
                        this.name = jsonReader.nextString();
                        break;
                    case "_id":
                        this.id = jsonReader.nextString();
                        break;
                    case "email":
                        this.email = jsonReader.nextString();
                        break;
                    case "phone":
                        this.phone = jsonReader.nextString();
                        break;
                    case "formatted":
                        this.formatedAddress = jsonReader.nextString();
                        break;

                    // Address is multi-layered so its split up
                    case "address":
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            key = jsonReader.nextName();
                            if (key.equals("city")) {
                                this.city = jsonReader.nextString();
                            } else if (key.equals("country")) {
                                this.country = jsonReader.nextString();
                            } else if (key.equals("subdivision")) {
                                this.subdivision = jsonReader.nextString();
                            } else {
                                jsonReader.skipValue(); // Skip values of other keys
                            }
                        }
                        jsonReader.endObject();
                        break;
                    case "description":
                        this.description = jsonReader.nextString();
                        break;

                    // For the card and logo, we gotta regex the value and attach it to the correct root, and then store that
                    case "card": {
                        String cardValue = jsonReader.nextString();
                        Log.d("traders", "Found title " + cardValue);
                        Pattern pattern = Pattern.compile("/v1/(\\S+)/");
                        Matcher matcher = pattern.matcher(cardValue);
                        if (matcher.find()) {
                            Log.d("traders", "Found match" + matcher.group(1));
                            this.card_URL = ("https://static.wixstatic.com/media/" + matcher.group(1));
                        }
                        break;
                    }
                    case "logo": {
                        String cardValue = jsonReader.nextString();
                        Log.d("traders", "Found title " + cardValue);
                        Pattern pattern = Pattern.compile("/v1/(\\S+)/");
                        Matcher matcher = pattern.matcher(cardValue);
                        if (matcher.find()) {
                            Log.d("traders", "Found match" + matcher.group(1));
                            this.logo_URL = ("https://static.wixstatic.com/media/" + matcher.group(1));
                        }
                        break;
                    }
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
