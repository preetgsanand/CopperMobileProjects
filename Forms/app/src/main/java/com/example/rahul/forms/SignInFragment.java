package com.example.rahul.forms;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.orm.SugarRecord;


import java.util.List;

public class SignInFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_sign_in, container, false);
        Button submit = (Button)view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails(view);
            }
        });
        View parent = getActivity().findViewById(R.id.fragment_container);
        view.setTranslationX(parent.getWidth());
        view.animate()
                .translationXBy(-parent.getWidth())
                .setDuration(1000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                    }
                });
        return view;
    }
    public void checkDetails(View view) {
        Log.e("Check Sign In","Check SignIn");
        EditText password = (EditText)view.findViewById(R.id.password);
        EditText email = (EditText)view.findViewById(R.id.email);
        //List<Personal> records =SugarRecord.listAll(Personal.class);
        List<Personal> records = SugarRecord.find(Personal.class,"EMAIL = ?",email.getText().toString());
        TextView result = (TextView)view.findViewById(R.id.result);
        for(int i = 0 ; i < records.size() ; i++) {
            if(records.get(i).getPassword().equals(password.getText().toString())) {
                Log.e("Rock", "Rock");
                result.setText(result.getText()+records.get(i).getId().toString());
                ((MainActivity)getActivity()).setProfileDisplay(records.get(i).getId());
            }
        }
    }
}
