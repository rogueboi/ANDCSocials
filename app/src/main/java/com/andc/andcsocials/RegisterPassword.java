package com.andc.andcsocials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class RegisterPassword extends AppCompatActivity {

    private TextView goToLogin;
    private ExtendedFloatingActionButton nextRegisterUserInformation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_password);
        getSupportActionBar().hide();

        ConstraintLayout logInLayout = findViewById(R.id.registerPasswordLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)logInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        goToLogin=findViewById(R.id.goToLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LogIn.class), ActivityOptions.makeSceneTransitionAnimation(RegisterPassword.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        nextRegisterUserInformation= findViewById(R.id.nextRegisterUserInformation);
        nextRegisterUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterUserInformation.class), ActivityOptions.makeSceneTransitionAnimation(RegisterPassword.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}