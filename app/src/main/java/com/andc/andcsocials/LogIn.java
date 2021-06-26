package com.andc.andcsocials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LogIn extends AppCompatActivity {

    private TextView registerRedirect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().hide();

        ConstraintLayout logInLayout = findViewById(R.id.logInLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)logInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        registerRedirect=findViewById(R.id.registerRedirect);
        registerRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class), ActivityOptions.makeSceneTransitionAnimation(LogIn.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}