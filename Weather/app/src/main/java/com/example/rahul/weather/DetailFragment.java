package com.example.rahul.weather;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;

import android.text.Layout;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class DetailFragment extends Fragment {
    private static JSONObject json;
    static Handler handler;
    private View view;
    GridView gridView;
    boolean expanded = false;
    int heightRelative;
    int heightGrid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_detail, container, false);
        Button back = (Button)view.findViewById(R.id.back);
        gridView = (GridView) view.findViewById(R.id.gridView2);
        final RelativeLayout relativeLayout = (RelativeLayout)view.findViewById(R.id.relativeLayout2);
        final ViewGroup.LayoutParams paramsRelative = relativeLayout.getLayoutParams();
        final ViewGroup.LayoutParams paramsGrid = gridView.getLayoutParams();
        heightRelative = paramsRelative.height;
        heightGrid = paramsGrid.height;
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(!expanded) {
                    relativeLayout.animate().translationYBy(-heightRelative).setDuration(1000);
                    gridView.animate().translationYBy(-heightRelative).setDuration(1000);
                    paramsGrid.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    gridView.setLayoutParams(paramsGrid);
                    expanded = true;
                }
                else {
                    gridView.animate().translationYBy(heightRelative).setDuration(1000);
                    relativeLayout.animate().translationYBy(heightRelative).setDuration(1000);
                    paramsGrid.height = heightGrid;
                    gridView.setLayoutParams(paramsGrid);
                    expanded = false;
                }
            }
        });


        updateView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setUpFrontFragment();
            }
        });
        return view;
    }
    public static DetailFragment newInstance(JSONObject jsonObject) {
        try {
            json = new JSONObject(jsonObject.toString());
        }
        catch(Exception e ){

        }
        DetailFragment detailFragment = new DetailFragment();
        handler = new Handler();
        return  detailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateWeatherData(new CityPreference(getActivity()).getCity());
    }
    public void updateWeatherData(final String city) {
        new Thread() {
            public void run() {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        "http://api.openweathermap.org/data/2.5/forecast?q=" + city,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {

                                try {
                                    if (response == null) {
                                        handler.post(new Runnable() {
                                            public void run() {
                                                Toast.makeText(getActivity(),
                                                        "Location not found!!",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    } else {
                                        handler.post(new Runnable() {
                                            public void run() {
                                                try {
                                                    JSONArray jsonObjects = response.getJSONArray("list");
                                                    DetailCustomAdapter adapter = new DetailCustomAdapter(getActivity(), jsonObjects);
                                                    gridView.setAdapter(adapter);
                                                    Log.e("json",jsonObjects.toString());
                                                    updateGraph(jsonObjects);

                                                } catch (Exception e) {

                                                }
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(),
                                        "Error Fetching Data",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("X-API-KEY", "2c50f0957a686b471d69d444ecde93b6");
                        return headers;
                    }
                };
                Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
            }
        }.start();
    }
    public void updateView(){
        TextView country = (TextView)view.findViewById(R.id.country);
        TextView location = (TextView)view.findViewById(R.id.location);
        TextView description = (TextView)view.findViewById(R.id.description);
        TextView temperature = (TextView)view.findViewById(R.id.temperature);
        TextView temp_min = (TextView)view.findViewById(R.id.mintemp);
        TextView temp_max = (TextView)view.findViewById(R.id.maxtemp);
        TextView humidity = (TextView)view.findViewById(R.id.humidity);
        TextView pressure = (TextView)view.findViewById(R.id.pressure);
        TextView wind = (TextView)view.findViewById(R.id.wind);
        try {
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            Log.e("details",details.toString());
            JSONObject main = json.getJSONObject("main");
            JSONObject windObject = json.getJSONObject("wind");

            country.setText(json.getJSONObject("sys").getString("country"));
            location.setText(json.getString("name").toUpperCase(Locale.US));
            description.setText(details.getString("description").toUpperCase(Locale.US));

            int temp = main.getInt("temp")-273;
            temperature.setText(String.valueOf(temp));

            int min = main.getInt("temp_min")-273;
            temp_min.setText("MIN  "+String.valueOf(min));

            int max = main.getInt("temp_max")-273;
            temp_max.setText("MAX  "+String.valueOf(max));

            int hum = main.getInt("humidity");
            humidity.setText(String.valueOf(hum)+" %");

            pressure.setText(String.valueOf(main.getString("pressure")));
            wind.setText(String.valueOf(windObject.getString("speed")));
            ImageView weatherIcon = (ImageView)view.findViewById(R.id.weathericon);
            setWeatherIcon(details, weatherIcon, description, view);
        }
        catch(Exception e){
        }
    }
    public void setWeatherIcon(JSONObject details,ImageView weatherIcon,TextView description,View view) {
        try {
            switch (Integer.parseInt(details.getString("id")) / 100) {
                case 3:
                    weatherIcon.setBackground(view.getResources().getDrawable(R.drawable.clouds_black));
                    return;
                case 5:
                    weatherIcon.setBackground(view.getResources().getDrawable(R.drawable.rain_black));
                    return;
                case 2:
                    weatherIcon.setBackground(view.getResources().getDrawable(R.drawable.thunderstorm_black));
                    return;
                case 6:
                    weatherIcon.setBackground(view.getResources().getDrawable(R.drawable.snow_black));
                    return;
                case 7:
                    weatherIcon.setBackground(view.getResources().getDrawable(R.drawable.haze_black));
                    return;
                case 8:
                    if (description.getText().toString().toUpperCase(Locale.US).equals("CLEAR SKY")) {
                        weatherIcon.setBackground(view.getResources().getDrawable(R.drawable.sunny_black));
                        return;
                    } else {
                        weatherIcon.setBackground(view.getResources().getDrawable(R.drawable.clouds_black));
                        return;
                    }
            }

        }
        catch(Exception e) {

        }
    }
    public void updateGraph(JSONArray jsonObject) {
        Log.e("updating","update");
        List<Integer> temp = new ArrayList<>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for(int i = 0 ; i < jsonObject.length() ; i++) {
            try{
                temp.add(i,jsonObject.getJSONObject(i).getJSONObject("main").getInt("temp") - 273);
                yVals.add(new Entry(i, temp.get(i)));
            }
            catch(Exception e)  {

            }
        }
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        set1.setLineWidth(1.75f);
        set1.setCircleRadius(5f);
        set1.setCircleHoleRadius(2.5f);
        set1.setColor(getResources().getColor(R.color.shower_rain));
        set1.setCircleColor(getResources().getColor(R.color.shower_rain));
        set1.setHighLightColor(Color.WHITE);
        set1.setDrawValues(false);
        LineData data = new LineData(set1);

        LineChart chart = (LineChart)view.findViewById(R.id.chart);
        chart.setDrawGridBackground(false);
        chart.setTouchEnabled(true);

        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setBackgroundColor(getResources().getColor(R.color.white));
        chart.animateX(2500);
        chart.setDescription("Temperature Data");
        chart.setData(data);

    }
}
