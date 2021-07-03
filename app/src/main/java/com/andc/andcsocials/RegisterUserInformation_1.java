package com.andc.andcsocials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterUserInformation_1 extends AppCompatActivity {

    private TextView goToLogin;
    private TextInputLayout textField1;
    private EditText phoneNumberRegister;
    private ExtendedFloatingActionButton nextRegisterPassword;

    private String email="", registrationType="", fullName="", course="", phoneNumber="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_information1);

        ConstraintLayout logInLayout = findViewById(R.id.registerUserInformationLayout1);
        AnimationDrawable animationDrawable = (AnimationDrawable)logInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        Intent RegisterUserInformationIntent=getIntent();
        registrationType =RegisterUserInformationIntent.getStringExtra("registrationType");
        email = RegisterUserInformationIntent.getStringExtra("email");
        fullName = RegisterUserInformationIntent.getStringExtra("fullName");
        course = RegisterUserInformationIntent.getStringExtra("course");

        textField1=findViewById(R.id.TextField1);
        phoneNumberRegister=findViewById(R.id.phoneNumberRegister);
        nextRegisterPassword=findViewById(R.id.nextRegisterPassword);

        goToLogin=findViewById(R.id.goToLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLogIn=new Intent(getApplicationContext(),LogIn.class);
                startActivity(startLogIn, ActivityOptions.makeSceneTransitionAnimation(RegisterUserInformation_1.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        nextRegisterPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=phoneNumberRegister.getText().toString().trim();
                if (phoneNumber.length()!=10) {
                    textField1.setError("Enter 10 digit Phone Number!");
                    textField1.setErrorIconDrawable(null);
                    return;
                }
                else {
                    textField1.setError(null);
                }

                Intent intentRegisterPassword = new Intent(getApplicationContext(),RegisterPassword.class);
                intentRegisterPassword.putExtra("registrationType",registrationType);
                intentRegisterPassword.putExtra("email",email);
                intentRegisterPassword.putExtra("phoneNumber",phoneNumber);
                intentRegisterPassword.putExtra("fullName",fullName);
                intentRegisterPassword.putExtra("course",course);
                startActivity(intentRegisterPassword, ActivityOptions.makeSceneTransitionAnimation(RegisterUserInformation_1.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}