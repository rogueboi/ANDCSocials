package com.andc.andcsocials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import org.jetbrains.annotations.NotNull;

public class ViewPagerExample extends AppCompatActivity {

    private ViewPager2 vp_vertical, vp_horizontal;
    int [] images ={R.drawable.andc_logo,R.drawable.andc_logo,R.drawable.andc_logo,
            R.drawable.andc_logo,R.drawable.andc_logo,R.drawable.andc_logo,
            R.drawable.andc_logo,R.drawable.andc_logo,R.drawable.andc_logo,R.drawable.andc_logo};
    MainAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_example);

        vp_vertical=findViewById(R.id.vp_vertical);
        vp_horizontal=findViewById(R.id.vp_horizontal);

        adapter=new MainAdapter(images);

        vp_vertical.setAdapter(adapter);

        vp_horizontal.setClipToPadding(false);

        vp_horizontal.setClipChildren(false);

        vp_horizontal.setOffscreenPageLimit(3);

        vp_horizontal.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        vp_horizontal.setAdapter(adapter);

        CompositePageTransformer transformer=new CompositePageTransformer();

        transformer.addTransformer(new MarginPageTransformer(8));

        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull @NotNull View page, float position) {
                float v=1-Math.abs(position);

                page.setScaleY(0.8f+v*0.2f);
            }
        });

        vp_horizontal.setPageTransformer(transformer);
    }
}