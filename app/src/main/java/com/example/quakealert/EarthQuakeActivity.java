package com.example.quakealert;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class EarthQuakeActivity extends AppCompatActivity {

    private static final String REQUEST_URL = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        ArrayList<Earthquake> earthquakes;

        EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(this, earthquakes);

        ListView earthquakeListView = findViewById(R.id.earthquake_list);

        earthquakeListView.setAdapter(earthquakeAdapter);
    }

    private class EarthquakeAsyncTask extends AsyncTask <URL, Void, String>{

        public ArrayList<Earthquake> extractEarthquakes(){
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

                    //
                    // Earthquake earthQuake = new Earthquake(mag, location, date);
                    //earthquakes.add(earthQuake);
                }
            } catch (JSONException e) {
                Log.e("Error", "No data available", e);
            }


            return earthquakes;
        }

        @Override
        protected String doInBackground(URL... urls) {
            //Make Url object on API
            URL url = createUrl(REQUEST_URL);
            return null;
        }


        private URL createUrl(String url){
            URL Url = null;
            try {
                Url = new URL(url);
            } catch (MalformedURLException e) {
                Log.e("Crearte Url", "Error!!!", e);
            }
            return Url;
        }
    }
}
