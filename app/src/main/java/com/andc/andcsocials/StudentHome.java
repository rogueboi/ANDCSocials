package com.andc.andcsocials;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StudentHome extends Fragment {

    private ViewPager2 societyInformation;
    int[] societyLogo = {R.drawable.andc_logo,R.drawable.andc_logo,R.drawable.andc_logo,
            R.drawable.andc_logo,R.drawable.andc_logo,R.drawable.andc_logo,
            R.drawable.andc_logo,R.drawable.andc_logo,R.drawable.andc_logo,R.drawable.andc_logo};
    String[] societyName = {"Dhwani","Dhun","Dhwani","Dhun","Dhwani","Dhun","Dhwani","Dhun","Dhwani","Dhun"};

    SocietyInformationAdapter adapter;

    public StudentHome() { }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_student_home, container, false);
        societyInformation=view.findViewById(R.id.societyInformation);

        List<SocietyInformation> societyInformations=new ArrayList<>();

        SocietyInformation societies[]=new SocietyInformation[10];
        for (int i=0;i<10;i++) {
            societies[i]=new SocietyInformation();
            societies[i].societyImageID=societyLogo[i];
            societies[i].societyName=societyName[i];
            societyInformations.add(societies[i]);
        }

        adapter=new SocietyInformationAdapter(societyInformations);

        societyInformation.setClipToPadding(false);
        societyInformation.setClipChildren(false);
        societyInformation.setOffscreenPageLimit(3);
        societyInformation.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        societyInformation.setAdapter(adapter);
        CompositePageTransformer transformer=new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(8));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull @NotNull View page, float position) {
                float v=1-Math.abs(position);

                page.setScaleY(0.8f+v*0.2f);
            }
        });
        societyInformation.setPageTransformer(transformer);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}