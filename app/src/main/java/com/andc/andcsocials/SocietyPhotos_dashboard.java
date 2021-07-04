package com.andc.andcsocials;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SocietyPhotos_dashboard extends Fragment {

    public SocietyPhotos_dashboard() {
        // Required empty public constructor
    }

    public static SocietyPhotos_dashboard newInstance(String param1, String param2) {
        SocietyPhotos_dashboard fragment = new SocietyPhotos_dashboard();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_society_photos_dashboard, container, false);
    }
}