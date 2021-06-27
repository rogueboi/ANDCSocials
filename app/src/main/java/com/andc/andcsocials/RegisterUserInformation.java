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
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class RegisterUserInformation extends AppCompatActivity {

    private TextView goToLogin;
    private TextInputLayout textField1, textField2;
    private EditText fullNameRegister;
    private AutoCompleteTextView selectCourse;

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

        textField1=findViewById(R.id.TextField1);
        fullNameRegister=findViewById(R.id.fullNameRegister);
        textField2=findViewById(R.id.TextField2);
        selectCourse=findViewById(R.id.selectCourse);

        Courses=new ArrayList<>();
        Courses.add("B. Com. (H.)");
        Courses.add("B. SC. (H.) Bio Medical Sciences");
        Courses.add("B. Sc. (H.) Botany");
        Courses.add("B. Sc. (H.) Chemistry");
        Courses.add("B. Sc. (H.) Computer Science");
        Courses.add("B. Sc. (H.) Electronics");
        Courses.add("B. Sc. (H.) Mathematics");
        Courses.add("B. Sc. (H.) Physics");
        Courses.add("B. Sc. (H.) Zoology");
        Courses.add("B. Sc. in Life Sciences");
        Courses.add("B. Sc. Physical Sciences with Chemistry");
        Courses.add("B. Sc. Physical Sciences with Computer Science");
        Courses.add("B. Sc. Physical Sciences with Electronics");

        coursesAdapter=new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_item,Courses);
        selectCourse.setAdapter(coursesAdapter);
        selectCourse.setThreshold(1);

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