package com.andc.andcsocials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ForgetPassword extends AppCompatActivity {

    private TextInputLayout textField1;
    private EditText emailForgetPassword;
    private ExtendedFloatingActionButton loginRedirect, sendResetLink;

    private FirebaseAuth firebaseAuth;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ConstraintLayout logInLayout = findViewById(R.id.forgetPasswordLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)logInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        textField1=findViewById(R.id.TextField1);
        emailForgetPassword=findViewById(R.id.emailForgetPassword);
        loginRedirect=findViewById(R.id.loginRedirect);
        sendResetLink=findViewById(R.id.sendResetLink);
        firebaseAuth=FirebaseAuth.getInstance();

        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLogIn=new Intent(getApplicationContext(),LogIn.class);
                startLogIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startLogIn, ActivityOptions.makeSceneTransitionAnimation(ForgetPassword.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                ForgetPassword.this.finishAffinity();
            }
        });

        sendResetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailForgetPassword.getText().toString();
                if (email.length()<1) {
                    textField1.setError("Email Address Required!");
                    return;
                }
                else {
                    textField1.setError(null);
                }

                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ForgetPassword.this, "Reset Link has been sent to your Email!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(ForgetPassword.this, "Fail to send the Reset Link!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}