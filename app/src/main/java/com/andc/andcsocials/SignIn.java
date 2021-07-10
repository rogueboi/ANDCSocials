package com.andc.andcsocials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;
import static java.lang.String.valueOf;

public class SignIn extends AppCompatActivity {

    private TextView needHelp;
    private Button signinButton1, registerButton1 , testButton;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ANDCSocials);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();

        if (user!=null) {
            FirebaseFirestore.getInstance()
                    .collectionGroup("StudentID")
                    .whereEqualTo("Email",user.getEmail()).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                            List<String> list=new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                String emails=document.getString("Email");
                                list.add(emails);
                            }
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                Intent startMainActivity=new Intent(getApplicationContext(), MainActivity.class);
                                startMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                SignIn.this.finishAffinity();
                                startActivity(startMainActivity, ActivityOptions.makeSceneTransitionAnimation(SignIn.this).toBundle());
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                            else if (task.isSuccessful() && task.getResult().isEmpty()) {
                                Intent startSocietyDashboardActivity=new Intent(getApplicationContext(), society_dashboard.class);
                                startSocietyDashboardActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                SignIn.this.finishAffinity();
                                startActivity(startSocietyDashboardActivity, ActivityOptions.makeSceneTransitionAnimation(SignIn.this).toBundle());
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                            else {
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(SignIn.this, "Fail to obtain user authentication details!\n"+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        ConstraintLayout signInLayout = findViewById(R.id.signInLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)signInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        needHelp=findViewById(R.id.help1);
        needHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NeedHelp.class), ActivityOptions.makeSceneTransitionAnimation(SignIn.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        signinButton1=findViewById(R.id.signInButton1);
        registerButton1=findViewById(R.id.registerButton1);
        testButton = findViewById(R.id.testButton);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),society_dashboard.class));

            }
        });


        signinButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LogIn.class), ActivityOptions.makeSceneTransitionAnimation(SignIn.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        registerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class), ActivityOptions.makeSceneTransitionAnimation(SignIn.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}