package com.andc.andcsocials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class RegisterUserInformation extends AppCompatActivity {

    private TextView goToLogin;
    private TextInputLayout TextField2;
    private AutoCompleteTextView autoCompleteTextView;

    ArrayList<String> Courses;
    ArrayAdapter<String> coursesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_information);
        getSupportActionBar().hide();

        ConstraintLayout logInLayout = findViewById(R.id.registerUserInformationLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)logInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        TextField2=findViewById(R.id.TextField2);
        autoCompleteTextView=findViewById(R.id.autoCompleteTextView);

        Courses=new ArrayList<>();
        Courses.add("B. Sc. (H.) Computer Science");
        Courses.add("B. Sc. (H.) Electronics");
        Courses.add("B. Sc. (H.) Physics");

        coursesAdapter=new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_item,Courses);
        autoCompleteTextView.setAdapter(coursesAdapter);
        autoCompleteTextView.setThreshold(1);

        goToLogin=findViewById(R.id.goToLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LogIn.class), ActivityOptions.makeSceneTransitionAnimation(RegisterUserInformation.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }
}