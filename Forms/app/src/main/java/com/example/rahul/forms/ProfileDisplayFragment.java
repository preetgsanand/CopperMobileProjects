package com.example.rahul.forms;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.orm.SugarRecord;

import java.util.List;

public class ProfileDisplayFragment extends Fragment {
    private static long i;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_display, container, false);
        Button back = (Button)view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setSignInForm();
            }
        });
        setProfileDisplay(view);
        return view;
    }
    public static ProfileDisplayFragment newInstance(long id) {
        i = id;
        ProfileDisplayFragment profileDisplayFragment = new ProfileDisplayFragment();
        return profileDisplayFragment;
    }
    public void setProfileDisplay(View view) {
        String id = String.valueOf(i);
        List<Personal> recordsPersonnal = SugarRecord.find(Personal.class, "ID = ?", id);
        for(int i = 0 ; i < recordsPersonnal.size() ; i++) {
            EditText personal = (EditText)view.findViewById(R.id.personalEditText);
            personal.setText(recordsPersonnal.get(i).toString());
        }
        List<Additional> recordsAdditional = SugarRecord.find(Additional.class, "ID = ?", id);
        for(int i = 0 ; i < recordsAdditional.size() ; i++) {
            EditText personal = (EditText)view.findViewById(R.id.additionalEditText);
            personal.setText(recordsAdditional.get(i).toString());
        }
        List<Education> recordsEducation = SugarRecord.find(Education.class, "ID = ?", id);
        for(int i = 0 ; i < recordsEducation.size() ; i++) {
            EditText personal = (EditText)view.findViewById(R.id.educationEditText);
            personal.setText(recordsEducation.get(i).toString());
        }
        List<Jee> recordsJEE = SugarRecord.find(Jee.class, "ID = ?", id);
        for(int i = 0 ; i < recordsJEE.size() ; i++) {
            EditText personal = (EditText)view.findViewById(R.id.jeeEditText);
            personal.setText(recordsJEE.get(i).toString());
        }
    }
}
