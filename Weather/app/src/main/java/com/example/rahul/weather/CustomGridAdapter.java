package com.example.rahul.weather;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CustomGridAdapter extends BaseAdapter{
    private Context context;
    private List<JSONObject> jsonObject;
    public CustomGridAdapter(Context context, List<JSONObject> jsonObject) {
        this.context = context;
        this.jsonObject = jsonObject;
    }
    @Override
    public int getCount() {
        return jsonObject.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertView == null) {
            gridView = new View(context);
            gridView = inflater.inflate( R.layout.frontgriditem , null);
            TextView temperature = (TextView) gridView.findViewById(R.id.temperature);
            TextView country = (TextView) gridView.findViewById(R.id.country);
            TextView description = (TextView) gridView.findViewById(R.id.description);
            TextView location = (TextView) gridView.findViewById(R.id.location);
            TextView mintemp = (TextView) gridView.findViewById(R.id.mintemp);
            TextView maxtemp = (TextView) gridView.findViewById(R.id.maxtemp);
            TextView humidity = (TextView) gridView.findViewById(R.id.humidity);
            TextView time = (TextView) gridView.findViewById(R.id.time);
            try {
                Log.e("in", "try");
                JSONObject details = jsonObject.get(position).getJSONArray("weather").getJSONObject(0);
                JSONObject main = jsonObject.get(position).getJSONObject("main");


                country.setText(jsonObject.get(position).getJSONObject("sys").getString("country"));
                location.setText(jsonObject.get(position).getString("name").toUpperCase(Locale.US));

                int temp = main.getInt("temp")-273;
                temperature.setText(String.valueOf(temp));

                int temp_min = main.getInt("temp_min")-273;
                mintemp.setText("MIN  "+String.valueOf(temp_min));

                int temp_max = main.getInt("temp_max")-273;
                maxtemp.setText("MAX  "+String.valueOf(temp_max));

                int hum = main.getInt("humidity");
                humidity.setText(String.valueOf(hum)+" %");
                description.setText(details.getString("description").toUpperCase(Locale.US));

                long val = jsonObject.get(position).getLong("dt")*1000;
                SimpleDateFormat df2 = new SimpleDateFormat("dd MMM, yyyy HH:mm");
                String dateText = df2.format(val);
                time.setText(dateText.toString());

                RelativeLayout relativeLayout = (RelativeLayout)gridView.findViewById(R.id.gridrelative);
                ImageView weatherIcon = (ImageView)gridView.findViewById(R.id.weathericon);


                switch(Integer.parseInt(details.getString("id"))/100) {
                    case 3:relativeLayout.setBackgroundColor(gridView.getResources().getColor(R.color.shower_rain));
                        weatherIcon.setBackground(gridView.getResources().getDrawable(R.drawable.brokenclouds));
                        break;
                    case 5:relativeLayout.setBackgroundColor(gridView.getResources().getColor(R.color.rain));
                        weatherIcon.setBackground(gridView.getResources().getDrawable(R.drawable.brokenclouds));
                        break;
                    case 2:relativeLayout.setBackgroundColor(gridView.getResources().getColor(R.color.thunderstorm));
                        weatherIcon.setBackground(gridView.getResources().getDrawable(R.drawable.thunderstorm));
                        break;
                    case 6:relativeLayout.setBackgroundColor(gridView.getResources().getColor(R.color.snow));
                        weatherIcon.setBackground(gridView.getResources().getDrawable(R.drawable.snow));
                        break;
                    case 7:relativeLayout.setBackgroundColor(gridView.getResources().getColor(R.color.mist));
                        weatherIcon.setBackground(gridView.getResources().getDrawable(R.drawable.mist));
                        break;
                    case 8:if(description.getText().toString().toUpperCase(Locale.US).equals("CLEAR SKY")) {
                        relativeLayout.setBackgroundColor(gridView.getResources().getColor(R.color.clear_sky));
                        weatherIcon.setBackground(gridView.getResources().getDrawable(R.drawable.sunny));
                        break;
                    }
                    else {
                        relativeLayout.setBackgroundColor(gridView.getResources().getColor(R.color.scattered_clouds));
                        weatherIcon.setBackground(gridView.getResources().getDrawable(R.drawable.scatteredclouds));
                        break;
                    }
                }
            } catch (Exception e) {
                Log.e("exception", e.toString());
            }
        }
        else {
            gridView = (View) convertView;
        }
        return gridView;
    }
}
