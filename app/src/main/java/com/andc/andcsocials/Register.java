package com.andc.andcsocials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.Color;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Register extends AppCompatActivity {

    private TextView goToLogin;
    private TextInputLayout textField1, textField2;
    private AutoCompleteTextView selectRegisterType;
    private EditText emailRegister;
    private ExtendedFloatingActionButton nextRegisterUserInformation;

    private String RegistrationType = "",email = "";

    private ArrayList<String> Registrations;
    private ArrayAdapter<String> registrationTypeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ConstraintLayout logInLayout = findViewById(R.id.registerLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)logInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        textField1=findViewById(R.id.TextField1);
        selectRegisterType =findViewById(R.id.selectRegisterType);
        textField2=findViewById(R.id.TextField2);
        emailRegister=findViewById(R.id.emailRegister);
        nextRegisterUserInformation= findViewById(R.id.nextRegisterUserInformation);

        Registrations =new ArrayList<>();
        Registrations.add("Student");
        Registrations.add("Society");

        registrationTypeAdapter =new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_item, Registrations);
        selectRegisterType.setAdapter(registrationTypeAdapter);
        selectRegisterType.setThreshold(1);

        selectRegisterType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        textField1.setStartIconDrawable(R.drawable.student);
                        break;
                    case 1:
                        textField1.setStartIconDrawable(R.drawable.society);
                        break;
                }
                RegistrationType = selectRegisterType.getText().toString();
                Toast.makeText(Register.this, "You are Registering as a "+RegistrationType+".", Toast.LENGTH_SHORT).show();
                textField1.setError(null);
            }
        });

        goToLogin=findViewById(R.id.goToLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLogIn=new Intent(getApplicationContext(),LogIn.class);
                startActivity(startLogIn, ActivityOptions.makeSceneTransitionAnimation(Register.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        nextRegisterUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectRegisterType.getText().toString().equals("")) {
                    textField1.setError("Select a Registration Type!");
                    textField1.setErrorIconDrawable(null);
                    return;
                }
                else {
                    textField1.setError(null);
                }
                
                email=emailRegister.getText().toString().trim();
                if (email.length()<1) {
                    textField2.setError("Enter Correct E-mail Address!");
                    textField2.setErrorIconDrawable(null);
                    return;
                }
                else {
                    textField2.setError(null);
                }

                Intent intentRegisterInformation=new Intent(getApplicationContext(), RegisterPassword.class);
                intentRegisterInformation.putExtra("registrationType",RegistrationType);
                intentRegisterInformation.putExtra("email",email);

                if (selectRegisterType.equals("Society")) {
                    FirebaseFirestore.getInstance().collectionGroup("SocietyID")
                            .whereEqualTo("Email",email).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                        startActivity(intentRegisterInformation, ActivityOptions.makeSceneTransitionAnimation(Register.this).toBundle());
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    }
                                    else {
                                        Toast.makeText(Register.this, "Society details for the Email "+email+" does not exist in the Database!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                else {
                    FirebaseFirestore.getInstance().collectionGroup("StudentID")
                            .whereEqualTo("Email",email).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                        startActivity(intentRegisterInformation, ActivityOptions.makeSceneTransitionAnimation(Register.this).toBundle());
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    }
                                    else {
                                        Toast.makeText(Register.this, "Student details for the Email "+email+" does not exist in the Database!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

            }
        });
    }
}