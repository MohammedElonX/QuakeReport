package com.example.quakealert;

import android.annotation.SuppressLint;
import android.app.usage.UsageEvents;
import android.arch.lifecycle.Lifecycle;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
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

    private static final String REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2012-01-01&endtime=2012-12-01&minmagnitude=6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        EarthquakeAsyncTask earthquakeAsyncTask = new EarthquakeAsyncTask();
        earthquakeAsyncTask.execute(REQUEST_URL);
    }

    private void updateUi(ArrayList<Earthquake> earthquakes){

        EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(this, earthquakes);

        ListView earthquakeListView = findViewById(R.id.earthquake_list);

        earthquakeListView.setAdapter(earthquakeAdapter);
    }

    private static class EarthquakeAsyncTask extends AsyncTask <String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            //Make Url object on API
            URL url = createUrl(urls[0]);

            //Make HTTP Request to receive JSON
            String Json = "";
            try {
                Json = makeHTTPrequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Json;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s == null){return;}
            updateUi(extractEarthquakes(s));
        }

        private String makeHTTPrequest(URL url) throws IOException {
            String JsonRequest = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                JsonRequest = readFromStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(inputStream != null){
                    inputStream.close();
                }
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
            }

            return JsonRequest;
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

        private ArrayList<Earthquake> extractEarthquakes(String s){
            ArrayList<Earthquake> earthquakes = new ArrayList<>();

            try{
                JSONObject jsonResponse = new JSONObject(s);
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
}
