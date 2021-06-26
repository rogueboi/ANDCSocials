package com.andc.andcsocials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class Register extends AppCompatActivity {

    private TextView goToLogin;
    private TextInputLayout textInputLayout;
    private EditText emailRegister;
    private ExtendedFloatingActionButton nextRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        ConstraintLayout logInLayout = findViewById(R.id.registerLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)logInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        goToLogin=findViewById(R.id.goToLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LogIn.class), ActivityOptions.makeSceneTransitionAnimation(Register.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        textInputLayout=findViewById(R.id.TextField1);
        emailRegister=findViewById(R.id.emailRegister);
        nextRegister= findViewById(R.id.nextRegisterPassword);
        nextRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterPassword.class), ActivityOptions.makeSceneTransitionAnimation(Register.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return;
            }
        });
    }
}