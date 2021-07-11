package com.andc.andcsocials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

public class ssStudentSocietyView extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapterSocietyDashboard adapter;
    MaterialButton editProfile_dashboard_asTempoaryLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ss_student_society_view);

        tabLayout = findViewById(R.id.tabSociety_dashboard);
        pager2 = findViewById(R.id.view_pager_dashboard);

        editProfile_dashboard_asTempoaryLogOut=findViewById(R.id.editProfile_dashboard);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapterSocietyDashboard(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Photos"));
        tabLayout.addTab(tabLayout.newTab().setText("Coordinators"));
        tabLayout.addTab(tabLayout.newTab().setText("Information"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });



    }
}