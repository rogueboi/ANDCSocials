package com.andc.andcsocials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import static com.andc.andcsocials.R.color.colorGradientEnd;

public class Register extends AppCompatActivity {

    private TextView goToLogin;
    private TextInputLayout textField1, textField2;
    private AutoCompleteTextView selectRegisterType;
    private EditText emailRegister;
    private ExtendedFloatingActionButton nextRegister;

    private int mode=0;
    String RegistrationType = "",email = "";

    ArrayList<String> Registrations;
    ArrayAdapter<String> registrationTypeAdapter;
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

        textField1=findViewById(R.id.TextField1);
        selectRegisterType =findViewById(R.id.selectRegisterType);

        Registrations =new ArrayList<>();
        Registrations.add("New Student");
        Registrations.add("New Society");

        registrationTypeAdapter =new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_item, Registrations);
        selectRegisterType.setAdapter(registrationTypeAdapter);
        selectRegisterType.setThreshold(1);

        selectRegisterType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        textField1.setStartIconDrawable(R.drawable.student);
                        mode=0;
                        break;
                    case 1:
                        textField1.setStartIconDrawable(R.drawable.society);
                        mode=1;
                        break;
                }
                RegistrationType = selectRegisterType.getText().toString();
                Toast.makeText(Register.this, "You are Registering as a "+RegistrationType+".", Toast.LENGTH_SHORT).show();
                textField1.setError(null);
                textField1.setHelperText(null);
            }
        });

        goToLogin=findViewById(R.id.goToLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LogIn.class), ActivityOptions.makeSceneTransitionAnimation(Register.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        textField2=findViewById(R.id.TextField2);
        emailRegister=findViewById(R.id.emailRegister);
        nextRegister= findViewById(R.id.nextRegisterPassword);
        nextRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectRegisterType.getText().toString().equals("")) {
                    textField1.setError("Select a Registration Type!");
                    return;
                }
                else {
                    textField1.setError(null);
                }
                
                email=emailRegister.getText().toString().trim();
                if (email.length()<1) {
                    textField2.setError("Enter Correct E-mail Address!");
                    return;
                }
                else {
                    textField2.setError(null);
                }

                Intent intentRegisterPassword=new Intent(getApplicationContext(),RegisterPassword.class);
                intentRegisterPassword.putExtra("registrationType",mode);
                intentRegisterPassword.putExtra("email",email);
                startActivity(intentRegisterPassword, ActivityOptions.makeSceneTransitionAnimation(Register.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}