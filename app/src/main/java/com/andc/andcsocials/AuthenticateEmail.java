package com.andc.andcsocials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticateEmail extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_email);
        getSupportActionBar().hide();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}