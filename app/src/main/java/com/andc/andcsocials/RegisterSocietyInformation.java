package com.andc.andcsocials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

public class RegisterSocietyInformation extends AppCompatActivity {

    private TextView goToLogin;
    private TextInputLayout textField1, textField2;
    private EditText societyNameRegister, phoneNumberRegister;

    private ExtendedFloatingActionButton nextRegisterSocietynformation1;

    String email="", registrationType="", societyName="", phoneNumber="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_society_information);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ConstraintLayout logInLayout = findViewById(R.id.registerSocietyInformationLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)logInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        Intent RegisterIntent=getIntent();
        registrationType =RegisterIntent.getStringExtra("registrationType");
        email = RegisterIntent.getStringExtra("email");

        textField1=findViewById(R.id.TextField1);
        societyNameRegister=findViewById(R.id.societyNameRegister);
        textField2=findViewById(R.id.TextField2);
        phoneNumberRegister=findViewById(R.id.phoneNumberRegister);

        nextRegisterSocietynformation1=findViewById(R.id.nextRegisterSocietyInformation1);

        goToLogin=findViewById(R.id.goToLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LogIn.class), ActivityOptions.makeSceneTransitionAnimation(RegisterSocietyInformation.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        nextRegisterSocietynformation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                societyName=societyNameRegister.getText().toString().trim();
                if (societyName.length()<1) {
                    textField1.setError("Society Name is Required!");
                    return;
                }
                else {
                    textField1.setError(null);
                }

                phoneNumber=phoneNumberRegister.getText().toString();
                if (phoneNumber.length()!=10) {
                    textField2.setError("10 digit Phone Number is Required!");
                    return;
                }
                else {
                    textField2.setError(null);
                }

                Intent intentRegisterSocietyInformation1 = new Intent(getApplicationContext(),RegisterSocietyInformation_1.class);
                intentRegisterSocietyInformation1.putExtra("registrationType",registrationType);
                intentRegisterSocietyInformation1.putExtra("email",email);
                intentRegisterSocietyInformation1.putExtra("societyName",societyName);
                intentRegisterSocietyInformation1.putExtra("phoneNumber",phoneNumber);
                startActivity(intentRegisterSocietyInformation1, ActivityOptions.makeSceneTransitionAnimation(RegisterSocietyInformation.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}