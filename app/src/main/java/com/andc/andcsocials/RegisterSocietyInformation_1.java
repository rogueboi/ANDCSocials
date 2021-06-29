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
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

public class RegisterSocietyInformation_1 extends AppCompatActivity {

    private TextView goToLogin;
    private TextInputLayout textField1, textField2;
    private AutoCompleteTextView selectSocietyType, selectDepartment;
    private ExtendedFloatingActionButton nextRegisterPassword;

    ArrayList<String> Societies, Departments;
    ArrayAdapter<String> societiesAdapter, departmentsAdapter;

    String email="", registrationType="", societyName="", phoneNumber="", societyType="", department="none";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_society_information1);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ConstraintLayout logInLayout = findViewById(R.id.registerSocietyInformationLayout1);
        AnimationDrawable animationDrawable = (AnimationDrawable)logInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        Intent RegisterSocietyInformationIntent=getIntent();
        registrationType =RegisterSocietyInformationIntent.getStringExtra("registrationType");
        email = RegisterSocietyInformationIntent.getStringExtra("email");
        societyName = RegisterSocietyInformationIntent.getStringExtra("societyName");
        phoneNumber =RegisterSocietyInformationIntent.getStringExtra("phoneNumber");

        textField1=findViewById(R.id.TextField1);
        selectSocietyType=findViewById(R.id.selectSocietyType);
        textField2=findViewById(R.id.TextField2);
        selectDepartment=findViewById(R.id.selectDepartment);
        nextRegisterPassword=findViewById(R.id.nextRegisterPassword);
        goToLogin=findViewById(R.id.goToLogin);

        Societies=new ArrayList<>();
        Societies.add("Co-Curricular");
        Societies.add("Departmental");

        societiesAdapter=new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_item,Societies);
        selectSocietyType.setAdapter(societiesAdapter);
        selectSocietyType.setThreshold(1);

        Departments=new ArrayList<>();
        Departments.add("Department of Bio Medical Science");
        Departments.add("Department of Botanical Science");
        Departments.add("Department of Chemisty");
        Departments.add("Department of Commerce");
        Departments.add("Department of Computer Science");
        Departments.add("Department of Electronics");
        Departments.add("Department of Mathematics");
        Departments.add("Department of Physics");
        Departments.add("Department of Zoological Science");

        departmentsAdapter=new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_item,Departments);
        selectDepartment.setAdapter(departmentsAdapter);
        selectDepartment.setThreshold(1);

        selectDepartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                department=selectDepartment.getText().toString();
            }
        });

        selectSocietyType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0) {
                    textField2.setVisibility(View.GONE);
                    department="";
                }
                else if (position==1) {
                    textField2.setVisibility(View.VISIBLE);
                }
                societyType=selectSocietyType.getText().toString();
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLogIn=new Intent(getApplicationContext(),LogIn.class);
                startLogIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startLogIn, ActivityOptions.makeSceneTransitionAnimation(RegisterSocietyInformation_1.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                RegisterSocietyInformation_1.this.finishAffinity();
            }
        });

        nextRegisterPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectSocietyType.getText().toString().equals("")) {
                    textField1.setError("Select a Society Type!");
                    return;
                }
                else {
                    textField2.setError(null);
                }

                if (selectSocietyType.getText().toString().equals("Departmental") && selectDepartment.getText().toString().equals("")) {
                    textField2.setError("Select a Department!");
                    return;
                }
                else {
                    textField2.setError(null);
                }

                Intent intentRegisterPassword = new Intent(getApplicationContext(),RegisterPassword.class);
                intentRegisterPassword.putExtra("registrationType",registrationType);
                intentRegisterPassword.putExtra("email",email);
                intentRegisterPassword.putExtra("societyName",societyName);
                intentRegisterPassword.putExtra("phoneNumber",phoneNumber);
                intentRegisterPassword.putExtra("societyType",societyType);
                intentRegisterPassword.putExtra("department",department);
                startActivity(intentRegisterPassword, ActivityOptions.makeSceneTransitionAnimation(RegisterSocietyInformation_1.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}