package com.andc.andcsocials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignIn extends AppCompatActivity {

    private TextView needHelp;
    private Button signinButton1, registerButton1;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ANDCSocials);
        setContentView(R.layout.activity_sign_in);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ConstraintLayout signInLayout = findViewById(R.id.signInLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)signInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        if (user!=null) {
            firebaseAuth.getCurrentUser().reload()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            user=firebaseAuth.getCurrentUser();
                            if (!user.isEmailVerified()) {
                                Intent startAuthenticateEmail=new Intent(getApplicationContext(),AuthenticateEmail.class);
                                startAuthenticateEmail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(startAuthenticateEmail,ActivityOptions.makeSceneTransitionAnimation(SignIn.this).toBundle());
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                SignIn.this.finishAffinity();
                            }
                            Intent startMainActivity=new Intent(getApplicationContext(), MainActivity.class);
                            startMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(startMainActivity, ActivityOptions.makeSceneTransitionAnimation(SignIn.this).toBundle());
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            SignIn.this.finishAffinity();
                        }
                    });
        }


        needHelp=findViewById(R.id.help1);
        needHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NeedHelp.class), ActivityOptions.makeSceneTransitionAnimation(SignIn.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        signinButton1=findViewById(R.id.signInButton1);
        registerButton1=findViewById(R.id.registerButton1);

        signinButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LogIn.class), ActivityOptions.makeSceneTransitionAnimation(SignIn.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        registerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class), ActivityOptions.makeSceneTransitionAnimation(SignIn.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}