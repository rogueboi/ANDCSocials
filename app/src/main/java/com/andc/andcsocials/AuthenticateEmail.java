package com.andc.andcsocials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AuthenticateEmail extends AppCompatActivity {

    private ExtendedFloatingActionButton logoutAuthenticateEmail, resendAuthenticateEmailLink, nextLogIn;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_email);
        getSupportActionBar().hide();

        ConstraintLayout signInLayout = findViewById(R.id.authenticateEmailLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)signInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        logoutAuthenticateEmail=findViewById(R.id.logoutAuthenticateEmail);
        resendAuthenticateEmailLink=findViewById(R.id.resendAuthenticateEmailLink);
        nextLogIn=findViewById(R.id.nextLogIn);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();

        logoutAuthenticateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent startLogIn=new Intent(getApplicationContext(),LogIn.class);
                startLogIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startLogIn,ActivityOptions.makeSceneTransitionAnimation(AuthenticateEmail.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                AuthenticateEmail.this.finishAffinity();
            }
        });

        resendAuthenticateEmailLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AuthenticateEmail.this, "Verification Email Sucessfully Sent!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(AuthenticateEmail.this, "Error Occurred.\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    nextLogIn.setEnabled(false);
//                    while(!user.isEmailVerified()) {
//                        Thread.sleep(50);
//                    }
//                    nextLogIn.setEnabled(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };

        nextLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                thread.start();
                firebaseAuth.getCurrentUser().reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        user=firebaseAuth.getCurrentUser();
                        if (!user.isEmailVerified()) {
                            Toast.makeText(AuthenticateEmail.this, "Your email is not verified!!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent startMainActivity=new Intent(getApplicationContext(), MainActivity.class);
                            startMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(startMainActivity, ActivityOptions.makeSceneTransitionAnimation(AuthenticateEmail.this).toBundle());
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            AuthenticateEmail.this.finishAffinity();
                        }
                    }
                });
            }
        });
    }
}