package com.andc.andcsocials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

public class RegisterPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_password);


        ConstraintLayout logInLayout = findViewById(R.id.logInLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)logInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();
    }
}