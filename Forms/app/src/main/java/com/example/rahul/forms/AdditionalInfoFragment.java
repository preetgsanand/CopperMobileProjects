package com.example.rahul.forms;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AdditionalInfoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_additional_info, container, false);
    }
    public static AdditionalInfoFragment newInstance() {
        AdditionalInfoFragment additionalInfoFragment = new AdditionalInfoFragment();
        return additionalInfoFragment;

    }
}
