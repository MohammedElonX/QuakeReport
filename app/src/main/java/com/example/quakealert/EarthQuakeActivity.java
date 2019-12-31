package com.example.quakealert;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthQuakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        earthquakes.add(new Earthquake("1.0", "Nasarrawa", "1-1-2019"));
        earthquakes.add(new Earthquake("2.0", "Benin", "1-2-2019"));
        earthquakes.add(new Earthquake("3.0", "Sapele", "1-3-2019"));
        earthquakes.add(new Earthquake("4.0", "Auchi", "1-4-2019"));
        earthquakes.add(new Earthquake("5.0", "Abuja", "1-5-2019"));
        earthquakes.add(new Earthquake("6.0", "Kebbi", "1-6-2019"));
        earthquakes.add(new Earthquake("7.0", "Katsina", "7-1-2019"));
        earthquakes.add(new Earthquake("8.0", "Niger", "1-8-2019"));
        earthquakes.add(new Earthquake("9.0", "Kaduna", "1-9-2019"));
        earthquakes.add(new Earthquake("3.5", "Calabar", "1-10-2019"));

        EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(this, earthquakes);

        ListView earthquakeListView = findViewById(R.id.earthquake_list);

        earthquakeListView.setAdapter(earthquakeAdapter);
    }
}
