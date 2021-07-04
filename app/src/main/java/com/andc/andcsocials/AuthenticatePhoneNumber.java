package com.andc.andcsocials;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.String.valueOf;

public class AuthenticatePhoneNumber extends AppCompatActivity {

    private ExtendedFloatingActionButton updatePhoneNumber, resendMessage, verifyPhone;
    private TextView phoneNumberText, verifyPhoneNumberMessage, resendOTPMessage;
    private TextInputLayout textField1;
    private EditText otp;
    private LinearLayout linearLayoutPhoneNumber;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private PhoneAuthProvider.ForceResendingToken Token;

    private String userID="", verificationID="", otpText="", registrationType;
    private DocumentReference documentReference;
    public static int i=0;

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
        resendMessage.setEnabled(false);
        resendMessage.setBackgroundColor(getColor(R.color.disabled));
        verifyPhone=findViewById(R.id.verifyPhone);
        linearLayoutPhoneNumber=findViewById(R.id.linearLayoutPhoneNumber);
        textField1=findViewById(R.id.TextField1);
        otp=findViewById(R.id.otp);

        firebaseAuth= FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        user=firebaseAuth.getCurrentUser();
        userID=user.getUid();

        Intent getRegistrationType=getIntent();
        registrationType=getRegistrationType.getStringExtra("registrationType");

        documentReference=firestore.collection(registrationType)
                .document(userID);

        i++;
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                String phoneNumber=value.getString("Phone Number");
                if (value.getBoolean("Is Phone Number Verified?")) {
                    phoneNumberText.setText("Your Phone Number has\nbeen verified successfully!");
                    resendMessage.setVisibility(View.GONE);
                    resendOTPMessage.setVisibility(View.GONE);
                    verifyPhoneNumberMessage.setVisibility(View.GONE);
                    linearLayoutPhoneNumber.setVisibility(View.GONE);
                    verifyPhone.setEnabled(false);
                    verifyPhone.setBackgroundColor(getColor(R.color.disabled));
                }
                else if (!phoneNumber.isEmpty() && i==1) {
                    resendOTPMessage.setVisibility(View.GONE);
                    resendMessage.setVisibility(View.GONE);
                    String text="Enter your phone number ending\nwith +91 ******";
                    verifyPhoneNumberMessage.setText(text.concat(phoneNumber.substring(6))+"\nto continue...");
                }
                else if (phoneNumber.isEmpty()) {
                    Toast.makeText(AuthenticatePhoneNumber.this, "Update Your Phone Number!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updatePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent startSignIn=new Intent(getApplicationContext(),SignIn.class);
                startSignIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                AuthenticatePhoneNumber.this.finishAffinity();
                startActivity(startSignIn, ActivityOptions.makeSceneTransitionAnimation(AuthenticatePhoneNumber.this).toBundle());
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        resendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentReference.addSnapshotListener(AuthenticatePhoneNumber.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        String phoneNumber=value.getString("Phone Number");
                        verifyPhoneNumber("+91"+phoneNumber);

                        resendMessage.setEnabled(false);
                        resendMessage.setBackgroundColor(getColor(R.color.disabled));
                    }
                });
            }
        });

        verifyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpText = otp.getText().toString();
                if(resendMessage.getVisibility()==View.GONE) {
                    documentReference.addSnapshotListener(AuthenticatePhoneNumber.this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                            if (value.getString("Phone Number").equals(otpText)) {
                                verifyPhoneNumberMessage.setText("Enter the 6-digit OTP that we \nhave send you on your \nregistered phone number.");
                                resendMessage.setVisibility(View.VISIBLE);
                                resendMessage.setEnabled(true);
                                resendMessage.setBackgroundColor(getColor(R.color.colorGradientStart));
                                resendOTPMessage.setVisibility(View.VISIBLE);
                                otp.setHint("Enter OTP");
                                textField1.setCounterEnabled(true);
                                textField1.setCounterMaxLength(6);
                                textField1.setStartIconDrawable(R.drawable.verify_otp);
                                verifyPhone.setIcon(getDrawable(R.drawable.verify_otp));
                                linearLayoutPhoneNumber.setPadding(10,25,10,25);
                                otp.setText("");
                                verifyPhoneNumber("+91"+otpText);
                            }
                            else {
                                return;
                            }
                        }
                    });
                    return;
                }

                otpText = otp.getText().toString();
                if (otpText.isEmpty()) {
                    textField1.setError("Enter 6-digit OTP!");
                    textField1.setErrorIconDrawable(null);
                    return;
                } else {
                    textField1.setError(null);
                }

                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationID,otpText);
                callbacks.onVerificationCompleted(credential);
            }
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                String receivedCode=phoneAuthCredential.getSmsCode();
                if(verificationID.equals(otpText)) {
                    user.updatePhoneNumber(phoneAuthCredential);
                    Map<String, Object> mapRegistration = new HashMap<>();
                    mapRegistration.put("Is Phone Number Verified?",true);
                    documentReference.update(mapRegistration)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Toast.makeText(AuthenticatePhoneNumber.this, "Fail to Update \"Is Phone Number Verified?\" field in the Database!", Toast.LENGTH_SHORT).show();
                                }
                            });
                    phoneNumberText.setText("Your Phone Number has\nbeen verified successfully!");
                    resendMessage.setVisibility(View.GONE);
                    resendOTPMessage.setVisibility(View.GONE);
                    verifyPhoneNumberMessage.setVisibility(View.GONE);
                    linearLayoutPhoneNumber.setVisibility(View.GONE);
                    verifyPhone.setEnabled(false);
                    verifyPhone.setBackgroundColor(getColor(R.color.disabled));
                }
                else {
                    Toast.makeText(AuthenticatePhoneNumber.this, "Incorrect OTP!! Unable to Verify your Phone Number!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                Toast.makeText(AuthenticatePhoneNumber.this, "Phone Number Verification Failed!\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Hemlo Hemlo",e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(AuthenticatePhoneNumber.this, "OTP has been sent to your registered mobile number!", Toast.LENGTH_SHORT).show();
                verificationID=s;
                Token=forceResendingToken;

                resendMessage.setEnabled(false);
                resendMessage.setBackgroundColor(getColor(R.color.disabled));
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull @NotNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                resendMessage.setEnabled(true);
                resendMessage.setBackgroundColor(getColor(R.color.colorGradientStart));
            }
        };
    }

    public void verifyPhoneNumber(String phoneNum) {
        PhoneAuthOptions options=PhoneAuthOptions.newBuilder(firebaseAuth)
                .setActivity(AuthenticatePhoneNumber.this)
                .setPhoneNumber(phoneNum)
                .setTimeout(120L, TimeUnit.SECONDS)
                .setCallbacks(callbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}