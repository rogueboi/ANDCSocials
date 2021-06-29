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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        getSupportActionBar().hide();

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
                startLogIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startLogIn, ActivityOptions.makeSceneTransitionAnimation(RegisterPassword.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                RegisterPassword.this.finishAffinity();
            }
        });

        nextAuthenticateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password=passwordRegister.getText().toString();
                confirmPassword=confirmPasswordRegister.getText().toString();

                if (password.length()<6) {
                    textField1.setError("Password must contain atleast 6 characters!");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    textField2.setError("Password does not match!");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    user=FirebaseAuth.getInstance().getCurrentUser();
                                    user.sendEmailVerification()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(RegisterPassword.this, "Verification mail has been sent to your email!", Toast.LENGTH_LONG).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull @NotNull Exception e) {
                                                    Toast.makeText(RegisterPassword.this, "Fail to send the verification mail!\n"+e.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            });

                                    userID= FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    DocumentReference documentReference=firestore.collection("Users")
                                            .document(userID);
                                    Map<String, Object> mapUser=new HashMap<>();
                                    mapUser.put("Registration Type",registrationType);
                                    mapUser.put("Email",email);
                                    documentReference.set(mapUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(RegisterPassword.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull @NotNull Exception e) {
                                            Toast.makeText(RegisterPassword.this, "Error Occured.\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    DocumentReference documentReferenceRegistrationType;
                                    if (registrationType.equals("Society")) {
                                        documentReferenceRegistrationType = firestore.collection("Society")
                                                .document(societyType)
                                                .collection("SocietyID")
                                                .document(userID);
                                        Map<String, Object> mapSocietyRegistration = new HashMap<>();
                                        mapSocietyRegistration.put("Registration Type", registrationType);
                                        mapSocietyRegistration.put("Email", email);
                                        mapSocietyRegistration.put("Society Name",societyName);
                                        mapSocietyRegistration.put("Society Type",societyType);
                                        mapSocietyRegistration.put("Department",department);
                                        mapSocietyRegistration.put("Phone Number",phoneNumber);
                                        mapSocietyRegistration.put("Official Account",false);
                                        documentReferenceRegistrationType.set(mapSocietyRegistration)
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull @NotNull Exception e) {
                                                        Toast.makeText(RegisterPassword.this, "Fail to add Society Information in the Database!\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                    else if (registrationType.equals("Student")) {
                                        documentReferenceRegistrationType = firestore.collection("Users")
                                                .document(userID);
                                        Map<String, Object> mapUserRegistration = new HashMap<>();
                                        mapUserRegistration.put("Registration Type", registrationType);
                                        mapUserRegistration.put("Email", email);
                                        mapUserRegistration.put("Full Name",fullName);
                                        mapUserRegistration.put("Course",course);
                                        mapUserRegistration.put("Phone Number",phoneNumber);
                                        mapUserRegistration.put("Student Coordinator of","none");
                                        documentReferenceRegistrationType.update(mapUserRegistration)
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull @NotNull Exception e) {
                                                        Toast.makeText(RegisterPassword.this, "Fail to add Student Information in the Database!\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }

                                    Intent goToAuthenticateEmail=new Intent(getApplicationContext(),AuthenticateEmail.class);
                                    goToAuthenticateEmail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(goToAuthenticateEmail, ActivityOptions.makeSceneTransitionAnimation(RegisterPassword.this).toBundle());
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    RegisterPassword.this.finishAffinity();
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