package com.example.quakealert;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QueryUtils {

    private static final String SAMPLE_JSON = "1";

    private QueryUtils(){
    }

    public static ArrayList<Earthquake> extractEarthquakes(){
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        try{
            JSONObject jsonResponse = new JSONObject(SAMPLE_JSON);
            JSONArray earthquakeArray = jsonResponse.getJSONArray("features");

            for(int i = 0; i<earthquakeArray.length(); i++){
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");

                String mag = properties.getString("mag");
                String location = properties.getString("place");
                String date = properties.getString("time");

                Earthquake earthQuake = new Earthquake(mag, location, date);
                earthquakes.add(earthQuake);
            }
        } catch (JSONException e) {
            Log.e("Error", "No data available", e);
        }


        return earthquakes;
    }
}
