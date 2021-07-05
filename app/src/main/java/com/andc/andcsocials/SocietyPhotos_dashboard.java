package com.andc.andcsocials;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView postRecycleView = (RecyclerView) getView().findViewById(R.id.postRecyclerDashboard);
        postRecycleView.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        );

        List<PostItemSDashboard> postItemSDashboardList = new ArrayList<>();
        postItemSDashboardList.add(new PostItemSDashboard(R.drawable.andc_logo));
        postItemSDashboardList.add(new PostItemSDashboard(R.drawable.i1));
        postItemSDashboardList.add(new PostItemSDashboard(R.drawable.i2));
        postItemSDashboardList.add(new PostItemSDashboard(R.drawable.i3));
        postItemSDashboardList.add(new PostItemSDashboard(R.drawable.i4));
        postItemSDashboardList.add(new PostItemSDashboard(R.drawable.i5));
        postItemSDashboardList.add(new PostItemSDashboard(R.drawable.i6));

        postRecycleView.setAdapter(new PostsSDashboardAdapter(postItemSDashboardList));
    }
}
//