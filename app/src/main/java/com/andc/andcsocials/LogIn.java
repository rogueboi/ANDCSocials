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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class LogIn extends AppCompatActivity {

    private TextView registerRedirect, forgetPassword;
    private TextInputLayout textField1, textField2;
    private EditText emailLogIn, passwordLogIn;
    private ExtendedFloatingActionButton login;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String email="", password="";
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
        forgetPassword=findViewById(R.id.forgetPassword);
        textField1=findViewById(R.id.TextField1);
        emailLogIn=findViewById(R.id.emailLogIn);
        textField2=findViewById(R.id.TextField2);
        passwordLogIn=findViewById(R.id.passwordLogIn);
        login=findViewById(R.id.login);
        firebaseAuth=FirebaseAuth.getInstance();

        registerRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class), ActivityOptions.makeSceneTransitionAnimation(LogIn.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ForgetPassword.class), ActivityOptions.makeSceneTransitionAnimation(LogIn.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=emailLogIn.getText().toString().trim();
                if (email.length()==0) {
                    textField1.setError("Email Required!!");
                    return;
                }
                else {
                    textField1.setError(null);
                }

                password=passwordLogIn.getText().toString();
                if (password.length()<6) {
                    textField2.setError("Password should be atleast 6 characters long!");
                    return;
                }
                else {
                    textField2.setError(null);
                }

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user=firebaseAuth.getCurrentUser();
                            if (!user.isEmailVerified()) {
                                    startActivity(new Intent(getApplicationContext(),AuthenticateEmail.class),ActivityOptions.makeSceneTransitionAnimation(LogIn.this).toBundle());
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    finish();
                            }
                            else {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class), ActivityOptions.makeSceneTransitionAnimation(LogIn.this).toBundle());
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                finish();
                            }
                        }
                        else {
                            Toast.makeText(LogIn.this, "Error Occurred!!\n"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}