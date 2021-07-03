package com.andc.andcsocials;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AuthenticatePhoneNumber extends AppCompatActivity {

    private ExtendedFloatingActionButton updatePhoneNumber, resendMessage, verifyPhone;
    private TextView phoneNumberText, verifyPhoneNumberMessage, resendOTPMessage;
    private LinearLayout linearLayoutPhoneNumber;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore firestore;

    private String userID;
    private DocumentReference documentReferenceRegistrationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_phone_number);

        ConstraintLayout signInLayout = findViewById(R.id.authenticatePhoneNumberLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)signInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        updatePhoneNumber=findViewById(R.id.updatePhoneNumber);
        phoneNumberText=findViewById(R.id.phoneNumberText);
        resendOTPMessage=findViewById(R.id.resendOTPMessage);
        verifyPhoneNumberMessage=findViewById(R.id.verifyPhoneNumberMessage);
        resendMessage=findViewById(R.id.resendMessage);
        verifyPhone=findViewById(R.id.verifyPhone);
        linearLayoutPhoneNumber=findViewById(R.id.linearLayoutPhoneNumber);
        firebaseAuth= FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        user=firebaseAuth.getCurrentUser();
        if (user.isEmailVerified()) {
            phoneNumberText.setText("Your Phone Number has\nbeen verified successfully!");
            resendOTPMessage.setVisibility(View.GONE);
            verifyPhoneNumberMessage.setVisibility(View.GONE);
            resendMessage.setVisibility(View.GONE);
            linearLayoutPhoneNumber.setVisibility(View.GONE);
            verifyPhone.setEnabled(false);
            verifyPhone.setBackgroundColor(getColor(R.color.disabled));
        }

        userID=user.getUid();
//        DocumentReference documentReference=firestore.collection("Users")
//                .document(userID);
//
//        documentReferenceRegistrationType=firestore.collection("Users")
//                .document(userID);
//
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
//                if (value.getString("Registration Type").equals("Society")) {
//                    String societyType=value.getString("Society Type");
//                    documentReferenceRegistrationType=firestore.collection("Society")
//                            .document(societyType)
//                            .collection("SocietyID")
//                            .document(userID);
//                }
//            }
//        });

        updatePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent startSignIn=new Intent(getApplicationContext(),SignIn.class);
                startSignIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startSignIn, ActivityOptions.makeSceneTransitionAnimation(AuthenticatePhoneNumber.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                AuthenticatePhoneNumber.this.finishAffinity();
            }
        });

        resendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AuthenticatePhoneNumber.this, "Resend Message Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        verifyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}