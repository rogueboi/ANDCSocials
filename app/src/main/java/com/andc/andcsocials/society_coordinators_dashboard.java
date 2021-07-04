package com.andc.andcsocials;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class society_coordinators_dashboard extends Fragment {

    public society_coordinators_dashboard() {
        // Required empty public constructor
    }

//    public static society_coordinators_dashboard newInstance(String param1, String param2) {
//        society_coordinators_dashboard fragment = new society_coordinators_dashboard();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_society_coordinators_dashboard, container, false);
    }
}