package com.example.rahul.weather;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import org.json.JSONObject;

/**
 * Created by rahul on 7/12/2016.
 */
public class WeatherWidget extends AppWidgetProvider {
    public static JSONObject json;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.frontgriditem);
    }
    public static void setJson(JSONObject jsonObject){
        try {
            WeatherWidget.json = new JSONObject(jsonObject.toString());
        }
        catch(Exception e){

        }
    }
}
