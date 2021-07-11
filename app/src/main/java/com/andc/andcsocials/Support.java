package com.andc.andcsocials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Support extends AppCompatActivity {

    private TextView principalEmail, collegeContact, instagramRishi, contactRishi, instagramAniket, contactAniket;
    private LinearLayout redirectCollegeWebsite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        principalEmail=findViewById(R.id.principalEmail);
        collegeContact=findViewById(R.id.collegeContact);
        instagramRishi=findViewById(R.id.instagramRishi);
        contactRishi=findViewById(R.id.contactRishi);
        instagramAniket=findViewById(R.id.instagramAniket);
        contactAniket=findViewById(R.id.contactAniket);
        redirectCollegeWebsite=findViewById(R.id.redirectCollegeWebsite);

        redirectCollegeWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.andcollege.du.ac.in/")));
            }
        });

        principalEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent principal = new Intent(Intent.ACTION_SEND);
                principal.setType("text/plain");
                String email[] = {"principal@andc.du.ac.in"};
                principal.putExtra(Intent.EXTRA_EMAIL,email);
                startActivity(Intent.createChooser(principal,"Contact College:"));
            }
        });

        collegeContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "01126293224")));
            }
        });

        instagramRishi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        contactRishi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+916306064863")));
            }
        });

        instagramAniket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        contactAniket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+918826868310")));
            }
        });
    }
}