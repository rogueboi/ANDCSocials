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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class RegisterUserInformation extends AppCompatActivity {

    private TextView goToLogin;
    private TextInputLayout textField1, textField2;
    private EditText fullNameRegister;
    private AutoCompleteTextView selectCourse;
    private ExtendedFloatingActionButton nextRegisterUserInformation1;

    private ArrayList<String> Courses;
    private ArrayAdapter<String> coursesAdapter;
    private String email="", registrationType="", fullName="", course="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_information);
        ConstraintLayout logInLayout = findViewById(R.id.registerUserInformationLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)logInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        Intent RegisterIntent=getIntent();
        registrationType =RegisterIntent.getStringExtra("registrationType");
        email = RegisterIntent.getStringExtra("email");

        textField1=findViewById(R.id.TextField1);
        fullNameRegister=findViewById(R.id.fullNameRegister);
        textField2=findViewById(R.id.TextField2);
        selectCourse=findViewById(R.id.selectCourse);
        nextRegisterUserInformation1=findViewById(R.id.nextRegisterUserInformation1);

        Courses=new ArrayList<>();
        Courses.add("B. Com. (H.)");
        Courses.add("B. Sc. (H.) Bio Medical Sciences");
        Courses.add("B. Sc. (H.) Botany");
        Courses.add("B. Sc. (H.) Chemistry");
        Courses.add("B. Sc. (H.) Computer Science");
        Courses.add("B. Sc. (H.) Electronics");
        Courses.add("B. Sc. (H.) Mathematics");
        Courses.add("B. Sc. (H.) Physics");
        Courses.add("B. Sc. (H.) Zoology");
        Courses.add("B. Sc. in Life Sciences");
        Courses.add("B. Sc. Phy. Sci. with Chemistry");
        Courses.add("B. Sc. Phy. Sci. with Computer Science");
        Courses.add("B. Sc. Phy. Sci. with Electronics");

        coursesAdapter=new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_item,Courses);
        selectCourse.setAdapter(coursesAdapter);
        selectCourse.setThreshold(1);

        selectCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                course=selectCourse.getText().toString();
            }
        });

        goToLogin=findViewById(R.id.goToLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLogIn=new Intent(getApplicationContext(),LogIn.class);
                startActivity(startLogIn, ActivityOptions.makeSceneTransitionAnimation(RegisterUserInformation.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        nextRegisterUserInformation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName=fullNameRegister.getText().toString().trim();
                if (fullName.length()<1) {
                    textField1.setError("Full Name Required!");
                    textField1.setErrorIconDrawable(null);
                    return;
                }
                else {
                    textField1.setError(null);
                }

                if (selectCourse.getText().toString().equals("")) {
                    textField2.setError("Select a Course!");
                    textField2.setErrorIconDrawable(null);
                    return;
                }
                else {
                    textField2.setError(null);
                }

                Intent intentRegisterUserInformation1 = new Intent(getApplicationContext(),RegisterUserInformation_1.class);
                intentRegisterUserInformation1.putExtra("registrationType",registrationType);
                intentRegisterUserInformation1.putExtra("email",email);
                intentRegisterUserInformation1.putExtra("fullName",fullName);
                intentRegisterUserInformation1.putExtra("course",course);
                startActivity(intentRegisterUserInformation1, ActivityOptions.makeSceneTransitionAnimation(RegisterUserInformation.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}