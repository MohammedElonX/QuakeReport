package com.example.quakealert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter <Earthquake> {

    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes){
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);

        TextView magnitude = listItemView.findViewById(R.id.magnitude);
        magnitude.setText(currentEarthquake.getMagnitude());

        TextView location = listItemView.findViewById(R.id.location);
        location.setText(currentEarthquake.getLocation());

        TextView date = listItemView.findViewById(R.id.date);
        date.setText(currentEarthquake.getDate());

        return listItemView;
    }
}
