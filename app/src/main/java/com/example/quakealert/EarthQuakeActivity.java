package com.example.quakealert;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class EarthQuakeActivity extends AppCompatActivity {

    private static final String REQUEST_URL = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        ArrayList<Earthquake> earthquakes;

        EarthquakeAdapter earthquakeAdapter = EarthquakeAsyncTask.execute();

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

            //Make HTTP Request to recieve JSON
            String Json = "";
            Json = makeHTTPrequest(url);
            return null;
        }

        private String makeHTTPrequest(URL url){
            String JsonRequest = "";
            HttpURLConnection httpURLConnection = null;
            InputStream inputStream = null;
            try {
                URLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                JsonRequest = readFromStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while(line != null){
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
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
