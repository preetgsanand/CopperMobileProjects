package com.example.rahul.weather;

import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class FrontPageFragment extends Fragment {
    Handler handler;
    GridView gridView;
    CustomGridAdapter adapter;
    View view;
    public FrontPageFragment() {
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_front_page, container, false);
        Button submit = (Button)view.findViewById(R.id.submit);
        Button reload = (Button)view.findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWeatherData(new CityPreference(getActivity()).getCity());
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setUpDialog();
            }
        });
        gridView = (GridView)view.findViewById(R.id.gridView);
        adapter = new CustomGridAdapter(getActivity(),((MainActivity)getActivity()).getJsonObjects());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).setDetailFragment(position);
            }
        });
        gridView.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).getJsonObjects().remove(position);
                adapter.notifyDataSetChanged();
                gridView.setAdapter(adapter);
                return true;
            }
        });
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateWeatherData(new CityPreference(getActivity()).getCity());
    }
    private void updateWeatherData(final String city){
        new Thread(){
            public void run(){
                Looper.prepare();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        "http://api.openweathermap.org/data/2.5/weather?q="+city,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {
                                try {
                                    if(response == null){
                                        handler.post(new Runnable(){
                                            public void run(){
                                                Toast.makeText(getActivity(),
                                                        "Location not found!!",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    } else {
                                        checkJson(response);
                                        handler.post(new Runnable(){
                                            public void run() {
                                                ((MainActivity) getActivity()).setJsonObjects(response);
                                                adapter.notifyDataSetChanged();
                                                gridView.setAdapter(adapter);
                                            }
                                        });
                                    }
                                }
                                catch(Exception e) {
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
                ){
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
    public void changeCity(String city){
        updateWeatherData(city);
    }
    public void checkJson(JSONObject json) {
        for(int i = 0 ; i < ((MainActivity)getActivity()).getJsonObjects().size() ; i++) {
            try {
                if (json.getLong("id") == ((MainActivity)getActivity()).getJsonObjects().get(i).getLong("id")) {
                    ((MainActivity)getActivity()).getJsonObjects().remove(i);
                }
            }
            catch(Exception e) {
            }
        }
    }
}
