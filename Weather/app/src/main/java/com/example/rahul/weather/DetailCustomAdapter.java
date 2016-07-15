package com.example.rahul.weather;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DetailCustomAdapter extends BaseAdapter{
    private Context context;
    JSONArray jsonArray;
    public DetailCustomAdapter(Context context,JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }
    @Override
    public int getCount() {
        return 12;
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
            gridView = inflater.inflate(R.layout.detail_custom_adapter, null);
            gridView.setMinimumHeight(300);
            gridView.setMinimumWidth(125);
            TextView description = (TextView)gridView.findViewById(R.id.description);
            TextView humidity = (TextView)gridView.findViewById(R.id.humidity);
            TextView pressure = (TextView)gridView.findViewById(R.id.pressure);
            TextView wind = (TextView)gridView.findViewById(R.id.wind);
            TextView time = (TextView)gridView.findViewById(R.id.time);
            ImageView weatherIcon = (ImageView)gridView.findViewById(R.id.weatherIcon);
            try {
                JSONObject object = jsonArray.getJSONObject(position);
                JSONObject weather = object.getJSONArray("weather").getJSONObject(0);
                JSONObject main = object.getJSONObject("main");
                JSONObject windObject = object.getJSONObject("wind");
                pressure.setText(main.getString("pressure"));
                humidity.setText(main.getString("humidity"));
                description.setText(weather.getString("description").toUpperCase(Locale.US));
                wind.setText(windObject.getString("speed"));

                String date = object.getString("dt_txt");
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = f.parse(date);
                long ms = d.getTime();
                SimpleDateFormat df2 = new SimpleDateFormat("dd MMM, HH:mm");
                String dateText = df2.format(ms);
                time.setText(dateText.toString());
                setWeatherIcon(weather,weatherIcon,description,gridView);
            }
            catch(Exception e) {
            }
        }
        else {
            gridView = (View) convertView;
        }
        return gridView;
    }
    public void setWeatherIcon(JSONObject details,ImageView weatherIcon,TextView description,View view) {
        try {
            switch (Integer.parseInt(details.getString("id")) / 100) {
                case 3:
                    weatherIcon.setBackground(view.getResources().getDrawable(R.drawable.fewclouds));
                    break;
                case 5:
                    weatherIcon.setBackground(view.getResources().getDrawable(R.drawable.brokenclouds));
                    break;
                case 2:
                    weatherIcon.setBackground(view.getResources().getDrawable(R.drawable.thunderstorm));
                    break;
                case 6:
                    weatherIcon.setBackground(view.getResources().getDrawable(R.drawable.snow));
                    break;
                case 7:
                    weatherIcon.setBackground(view.getResources().getDrawable(R.drawable.mist));
                    break;
                case 8:
                    if (description.getText().toString().toUpperCase(Locale.US).equals("CLEAR SKY")) {
                        weatherIcon.setBackground(view.getResources().getDrawable(R.drawable.sunny));
                        break;
                    } else {
                        weatherIcon.setBackground(view.getResources().getDrawable(R.drawable.scatteredclouds));
                        break;
                    }
            }
        }
        catch(Exception e) {

        }
    }
}
