package com.andc.andcsocials;

import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

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


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyAdapter_Coordinator_SDashboard myAdapter;
        RecyclerView mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerViewCoordinators);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        myAdapter = new MyAdapter_Coordinator_SDashboard(getContext(),getMyList());
        mRecyclerView.setAdapter(myAdapter);



    }
    private ArrayList<Model_Coordinators_Sdashboard> getMyList() {
        ArrayList<Model_Coordinators_Sdashboard> models = new ArrayList<>();

        Model_Coordinators_Sdashboard m = new Model_Coordinators_Sdashboard();
        m.setTitle("Trial Title");
        m.setDescription("Trial Description");
        m.setImg(R.drawable.andc_logo);
        models.add(m);

        m = new Model_Coordinators_Sdashboard();
        m.setTitle("Trial Title2");
        m.setDescription("Trial Description2");
        m.setImg(R.drawable.andc_logo);
        models.add(m);

        m = new Model_Coordinators_Sdashboard();
        m.setTitle("Trial Title3");
        m.setDescription("Trial Description3");
        m.setImg(R.drawable.andc_logo);
        models.add(m);


        m = new Model_Coordinators_Sdashboard();
        m.setTitle("Trial Title4");
        m.setDescription("Trial Description4");
        m.setImg(R.drawable.andc_logo);
        models.add(m);

        m = new Model_Coordinators_Sdashboard();
        m.setTitle("Trial Title5");
        m.setDescription("Trial Description5");
        m.setImg(R.drawable.andc_logo);
        models.add(m);

        return models;
    }


}