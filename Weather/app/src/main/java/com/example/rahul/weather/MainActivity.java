package com.example.rahul.weather;

import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<JSONObject> jsonObjects = new ArrayList<>();
    private String location_autocomplete_url =
            "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=%s&key=%s";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpFrontFragment();
    }
    public void setUpDialog() {
        final Dialog dialog = new Dialog(this);
        final List<String> locations = new ArrayList<String>();
        final View view = getLayoutInflater().inflate(R.layout.add_dialog, null);
        dialog.setContentView(view);
        final EditText city = (EditText)view.findViewById(R.id.city);
        final ListView listView = (ListView) view.findViewById(R.id.autocomplete_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String description = locations.get(position);
                Log.e("location",description);
                int index = description.indexOf(',');
                if(index != -1) {
                    String location = description.substring(0,index).replaceAll("\\s", "");
                    submitCity(location);
                    dialog.dismiss();

                }
                else {
                    submitCity(locations.get(position).replaceAll("\\s", ""));
                    dialog.dismiss();

                }

            }
        });
        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (city.getText().toString().length() > 3) {
                    handler = new Handler();
                    new Thread() {
                        public void run() {
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                                    String.format(location_autocomplete_url, city.getText().toString().replaceAll("\\s", ""), "AIzaSyAw-HRJYHQ9z5-GNTOoy85ZM9ZJVPbc4_w"),
                                    null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(final JSONObject response) {
                                            if (response != null) {
                                                handler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        locations.clear();
                                                        updateAdapter(locations, response);
                                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                                                                android.R.layout.simple_list_item_1,
                                                                locations){
                                                            @Override
                                                            public View getView(int position, View convertView, ViewGroup parent) {
                                                                View view = super.getView(position, convertView, parent);
                                                                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                                                                tv.setTextColor(Color.BLACK);

                                                                return view;
                                                            }
                                                        };
                                                        listView.setAdapter(adapter);
                                                    }
                                                });
                                            } else {
                                                handler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Log.e("miss", "google");
                                                    }
                                                });
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    });
                            Volley.newRequestQueue(MainActivity.this).add(jsonObjectRequest);
                        }
                    }.start();


                } else {
                    listView.setAdapter(null);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button submit = (Button)view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityString = city.getText().toString().replaceAll("\\s", "");
                submitCity(cityString);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void setJsonObjects(JSONObject jsonObject) {
        jsonObjects.add(jsonObject);
    }
    public List<JSONObject> getJsonObjects() {
        return this.jsonObjects;
    }
    public void submitCity(String city) {
        FrontPageFragment frontPageFragment = (FrontPageFragment)getFragmentManager().findFragmentById(R.id.fragment_container);
        frontPageFragment.changeCity(city);
        new CityPreference(this).setCity(city);

    }

    public void setDetailFragment(int position) {
        try {
            new CityPreference(this).setCity(jsonObjects.get(position).getString("name").replaceAll("\\s",""));
            Log.e("city", new CityPreference(this).getCity());
        }
        catch(Exception e) {

        }
        DetailFragment detailFragment = new DetailFragment().newInstance(jsonObjects.get(position));
        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.exit_to_left, R.anim.enter_from_right).replace(R.id.fragment_container, detailFragment).commit();
    }
    public void setUpFrontFragment() {
        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.exit_to_left, R.anim.enter_from_right).replace(R.id.fragment_container, new FrontPageFragment()).commit();
    }

    public void updateAdapter(List<String> locations,JSONObject jsonObject) {
        try {
            JSONArray predictions = jsonObject.getJSONArray("predictions");
            for(int i = 0 ; i < 6 ; i++) {
                String location = predictions.getJSONObject(i).getString("description");
                locations.add(location);
            }
        }
        catch(Exception e) {
            Log.e("Exception location autocomplete",e.toString());
        }
    }
}

