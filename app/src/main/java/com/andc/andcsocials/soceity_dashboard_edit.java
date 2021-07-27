package com.andc.andcsocials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class soceity_dashboard_edit extends AppCompatActivity {


    private LinearLayout createPost, societyProfile, membersCoordinators, timeline, reviewApplicants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soceity_dashboard_edit);

        createPost= findViewById(R.id.createPost);
        societyProfile= findViewById(R.id.societyProfile);
        membersCoordinators = findViewById(R.id.membersCoordinators);
        timeline= findViewById(R.id.timeline);
        reviewApplicants= findViewById(R.id.reviewApplicants);


        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CreatePostScreen.class));

            }
        });

        societyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),society_profile.class));

            }
        });

        membersCoordinators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),society_members_coordinatiors.class));

            }
        });





    }
}