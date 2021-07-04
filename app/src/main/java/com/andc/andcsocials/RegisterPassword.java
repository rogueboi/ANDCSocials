package com.andc.andcsocials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RegisterPassword extends AppCompatActivity {

    private TextView goToLogin;
    private TextInputLayout textField1, textField2;
    private EditText passwordRegister, confirmPasswordRegister;
    private ExtendedFloatingActionButton nextAuthenticateEmail;
    private String registrationType="", email="", password="", confirmPassword="", fullName="", course="", phoneNumber=" ", societyName="", societyType="", department="", userID="";

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_password);

        ConstraintLayout logInLayout = findViewById(R.id.registerPasswordLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)logInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        Intent RegisterInformationIntent=getIntent();
        registrationType =RegisterInformationIntent.getStringExtra("registrationType");
        email = RegisterInformationIntent.getStringExtra("email");
        phoneNumber = RegisterInformationIntent.getStringExtra("phoneNumber");

        if (registrationType.equals("Student")) {
            fullName = RegisterInformationIntent.getStringExtra("fullName");
            course = RegisterInformationIntent.getStringExtra("course");
        }
        else {
            societyName=RegisterInformationIntent.getStringExtra("societyName");
            societyType=RegisterInformationIntent.getStringExtra("societyType");
            department=RegisterInformationIntent.getStringExtra("department");
            if (department.equals("")) {
                department="none";
            }
        }

        goToLogin=findViewById(R.id.goToLogin);
        textField1=findViewById(R.id.TextField1);
        passwordRegister=findViewById(R.id.passwordRegister);
        textField2=findViewById(R.id.TextField2);
        confirmPasswordRegister=findViewById(R.id.confirmPasswordRegister);
        nextAuthenticateEmail= findViewById(R.id.nextAuthenicateEmail);

        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLogIn=new Intent(getApplicationContext(),LogIn.class);
                startActivity(startLogIn, ActivityOptions.makeSceneTransitionAnimation(RegisterPassword.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        nextAuthenticateEmail.setOnClickListener(new View.OnClickListener() {
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

                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    user=FirebaseAuth.getInstance().getCurrentUser();
                                    userID= FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    firestore=FirebaseFirestore.getInstance();
                                    DocumentReference documentReference=firestore.collection(registrationType).document(userID);

                                    Map<String, Object> mapRegistration = new HashMap<>();
                                    mapRegistration.put("Email", email);
                                    mapRegistration.put("Phone Number",phoneNumber);
                                    mapRegistration.put("Is Email Verified?",false);
                                    mapRegistration.put("Is Phone Number Verified?",false);

                                    if (registrationType.equals("Society")) {
                                        mapRegistration.put("Society Name",societyName);
                                        mapRegistration.put("Society Type",societyType);
                                        mapRegistration.put("Department",department);
                                        mapRegistration.put("Official Account",false);
                                        documentReference.set(mapRegistration)
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull @NotNull Exception e) {
                                                        Toast.makeText(RegisterPassword.this, "Fail to add Society Information in the Society Information Database!\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                        Toast.makeText(RegisterPassword.this, "Account Successfully Created!", Toast.LENGTH_SHORT).show();

                                        Intent goToSocietyDashboard=new Intent(getApplicationContext(),society_dashboard.class);
                                        goToSocietyDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        RegisterPassword.this.finishAffinity();
                                        startActivity(goToSocietyDashboard, ActivityOptions.makeSceneTransitionAnimation(RegisterPassword.this).toBundle());
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    }
                                    else if (registrationType.equals("Student")) {
                                        mapRegistration.put("Full Name",fullName);
                                        mapRegistration.put("Course",course);
                                        mapRegistration.put("Student Coordinator of","none");
                                        documentReference.set(mapRegistration)
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull @NotNull Exception e) {
                                                        Toast.makeText(RegisterPassword.this, "Fail to add Student Information in the Database!\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                        Toast.makeText(RegisterPassword.this, "Account Successfully Created!", Toast.LENGTH_SHORT).show();

                                        Intent goToMainActivity=new Intent(getApplicationContext(),MainActivity.class);
                                        goToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        RegisterPassword.this.finishAffinity();
                                        startActivity(goToMainActivity, ActivityOptions.makeSceneTransitionAnimation(RegisterPassword.this).toBundle());
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    }
                                }
                                else {
                                    Toast.makeText(RegisterPassword.this, "Error Occurred!!\n"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}