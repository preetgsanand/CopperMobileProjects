package com.example.rahul.taps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rahul on 6/24/2016.
 */
public class CusotmAdapter extends BaseAdapter implements ListAdapter{
    private ArrayList<String> list = new ArrayList<>();
    private Context context;
    public CusotmAdapter(ArrayList<String> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.location_list, null);
        }
        TextView text = (TextView)view.findViewById(R.id.location);
        text.setText(list.get(position));
        Button favourite = (Button) view.findViewById(R.id.favourite);
        favourite.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v) {

            }
        });
        return view;
    }
}
