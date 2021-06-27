package com.andc.andcsocials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NeedHelp extends AppCompatActivity {

    private TextView goToLogin;
    private Button contactDevelopers, andcWebsite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_help);
        getSupportActionBar().hide();

        ConstraintLayout signInLayout = findViewById(R.id.needHelpLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)signInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        goToLogin=findViewById(R.id.goToLogin);
        contactDevelopers=findViewById(R.id.contactDevelopers);
        andcWebsite=findViewById(R.id.andcWebsite);

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LogIn.class), ActivityOptions.makeSceneTransitionAnimation(NeedHelp.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        contactDevelopers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String email[] = {"andcsocials@gmail.com"};
                String sub = "I need Help!!!";
                i.putExtra(Intent.EXTRA_EMAIL,email);
                i.putExtra(Intent.EXTRA_SUBJECT,sub);
                startActivity(Intent.createChooser(i,"Send Mail:"));
            }
        });

        andcWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.andcollege.du.ac.in/")));
            }
        });
    }
}