package com.andc.andcsocials;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.rpc.context.AttributeContext;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public class AuthenticateEmail extends AppCompatActivity implements UpdateEmailDialog.UpdateEmailDialogListener {

    private ExtendedFloatingActionButton updateEmail, resendAuthenticateEmailLink, verifyEmail;
    private TextView emailText, waitingText, verifyEmailMessage, helper1, helper2, helper3;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore firestore;

    DocumentSnapshot documentSnapshot;
    private String userID, registrationType, societyType;
    private DocumentReference documentReference;
    protected static int i=0;
    long lastClickTimeForUpdateEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_email);

        ConstraintLayout signInLayout = findViewById(R.id.authenticateEmailLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable)signInLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();

        updateEmail=findViewById(R.id.updateEmail);
        emailText=findViewById(R.id.emailText);
        waitingText=findViewById(R.id.waitingText);
        verifyEmailMessage=findViewById(R.id.verifyEmailMessage);
        resendAuthenticateEmailLink=findViewById(R.id.resendAuthenticateEmailLink);
        verifyEmail=findViewById(R.id.verifyEmail);

        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        user=firebaseAuth.getCurrentUser();
        userID=user.getUid();

        Intent getDocumentReference=getIntent();
        documentReference=firestore.document(getDocumentReference.getStringExtra("documentReference"));

        i++;
        documentReference.addSnapshotListener(AuthenticateEmail.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                boolean isEmailVerified=value.getBoolean("Is Email Verified?");
                if (user.isEmailVerified()) {
                    if (isEmailVerified) {
                        Map<String, Object> mapRegistration = new HashMap<>();
                        mapRegistration.put("Is Email Verified?", true);
                        documentReference.update(mapRegistration)
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Toast.makeText(AuthenticateEmail.this, "Fail to Update \"Is Email Verified?\" field in the Database!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    emailText.setText("Your Email Address has\nbeen verified successfully!");
                    waitingText.setVisibility(View.GONE);
                    verifyEmailMessage.setVisibility(View.GONE);
                    resendAuthenticateEmailLink.setVisibility(View.GONE);
                    verifyEmail.setEnabled(false);
                    verifyEmail.setBackgroundColor(getColor(R.color.disabled));
                }
                else if (i == 1) {
                    user.sendEmailVerification()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(AuthenticateEmail.this, "Verification mail has been sent to your email!", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Toast.makeText(AuthenticateEmail.this, "Fail to send the verification mail!\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });

        updateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTimeForUpdateEmail < 1000){
                    return;
                }
                lastClickTimeForUpdateEmail = SystemClock.elapsedRealtime();
                openUpdateEmailDialog();
            }
        });

        resendAuthenticateEmailLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getCurrentUser().reload()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                user=firebaseAuth.getCurrentUser();
                                if (!user.isEmailVerified()) {
                                    user.sendEmailVerification()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(AuthenticateEmail.this, "Verification Email Sucessfully Sent!", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                 public void onFailure(@NonNull @NotNull Exception e) {
                                                   Toast.makeText(AuthenticateEmail.this, "Error Occurred.\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                                else {
                                    Toast.makeText(AuthenticateEmail.this, "Your email is already verified! Click on the Verify Button to proceed...", Toast.LENGTH_SHORT).show();
                                    resendAuthenticateEmailLink.setEnabled(false);
                                    resendAuthenticateEmailLink.setBackgroundColor(getColor(R.color.disabled));
                                }
                            }
                        });
            }
        });

        verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getCurrentUser().reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        user=firebaseAuth.getCurrentUser();
                        if (!user.isEmailVerified()) {
                            Toast.makeText(AuthenticateEmail.this, "Your email is not verified!!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Map<String, Object> mapRegistration = new HashMap<>();
                            mapRegistration.put("Is Email Verified?",true);
                            documentReference.update(mapRegistration)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                emailText.setText("Your Email Address has\nbeen verified successfully!");
                                                waitingText.setVisibility(View.GONE);
                                                verifyEmailMessage.setVisibility(View.GONE);
                                                resendAuthenticateEmailLink.setVisibility(View.GONE);
                                                verifyEmail.setEnabled(false);
                                                verifyEmail.setBackgroundColor(getColor(R.color.disabled));
                                            }
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }

    private void openUpdateEmailDialog() {
        UpdateEmailDialog updateEmailDialog=new UpdateEmailDialog();
        updateEmailDialog.show(getSupportFragmentManager(),"Update Email Dialog");
    }

    @Override
    public void checkSignOutStatus(String newEmail) {
        user.updateEmail(newEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Map<String, Object> mapRegistration = new HashMap<>();
                mapRegistration.put("Email",newEmail);
                mapRegistration.put("Is Email Verified?",false);
                documentReference.update(mapRegistration)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                Toast.makeText(AuthenticateEmail.this, "Your Email Address has been successfully Updated!", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                i=0;
                                Intent startSignIn=new Intent(getApplicationContext(),SignIn.class);
                                startSignIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                AuthenticateEmail.this.finishAffinity();
                                startActivity(startSignIn, ActivityOptions.makeSceneTransitionAnimation(AuthenticateEmail.this).toBundle());
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(AuthenticateEmail.this, "Error Occurred!!\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}