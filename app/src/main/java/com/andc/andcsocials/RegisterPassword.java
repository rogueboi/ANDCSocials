package com.andc.andcsocials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RegisterPassword extends AppCompatActivity {

    private TextView goToLogin;
    private TextInputLayout textField1, textField2;
    private EditText passwordRegister, confirmPasswordRegister;
    private ProgressBar progressBar;
    private ExtendedFloatingActionButton nextDashboard;
    private String registrationType="", email="", password="", confirmPassword="", userID="";

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_password);

        ConstraintLayout logInLayout = findViewById(R.id.registerPasswordLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)logInLayout.getBackground();
        animationDrawable.setAlpha(215);
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        Intent RegisterInformationIntent=getIntent();
        registrationType =RegisterInformationIntent.getStringExtra("registrationType");
        email = RegisterInformationIntent.getStringExtra("email");

        goToLogin=findViewById(R.id.goToLogin);
        textField1=findViewById(R.id.TextField1);
        passwordRegister=findViewById(R.id.passwordRegister);
        textField2=findViewById(R.id.TextField2);
        confirmPasswordRegister=findViewById(R.id.confirmPasswordRegister);
        nextDashboard = findViewById(R.id.nextDashboard);
        progressBar=findViewById(R.id.progressBar);

        firebaseAuth=FirebaseAuth.getInstance();

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLogIn=new Intent(getApplicationContext(),LogIn.class);
                startActivity(startLogIn, ActivityOptions.makeSceneTransitionAnimation(RegisterPassword.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        nextDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password=passwordRegister.getText().toString();
                confirmPassword=confirmPasswordRegister.getText().toString();

                if (password.length()<6) {
                    textField1.setError("Password must contain atleast 6 characters!");
                    textField1.setErrorIconDrawable(null);
                    return;
                }
                else {
                    textField1.setError(null);
                }

                if (!password.equals(confirmPassword)) {
                    textField2.setError("Password does not match!");
                    textField2.setErrorIconDrawable(null);
                    return;
                }
                else {
                    textField2.setError(null);
                }

                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterPassword.this, "Account Created.", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(RegisterPassword.this, "Verify your Email & Phone No.!", Toast.LENGTH_SHORT).show();
                                    if (registrationType.equals("Student")) {
                                        Intent startMainActivity=new Intent(getApplicationContext(), MainActivity.class);
                                        startMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        RegisterPassword.this.finishAffinity();
                                        startActivity(startMainActivity, ActivityOptions.makeSceneTransitionAnimation(RegisterPassword.this).toBundle());
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    }
                                    else {
                                        Intent startSocietyDashboardActivity=new Intent(getApplicationContext(), society_dashboard.class);
                                        startSocietyDashboardActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        RegisterPassword.this.finishAffinity();
                                        startActivity(startSocietyDashboardActivity, ActivityOptions.makeSceneTransitionAnimation(RegisterPassword.this).toBundle());
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    }
                                }
                                else {
                                    Toast.makeText(RegisterPassword.this, "Error Occurred!!\n"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });
    }
}