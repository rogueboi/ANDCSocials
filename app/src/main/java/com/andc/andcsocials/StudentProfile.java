package com.andc.andcsocials;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentProfile extends Fragment {

    private TextView studentName, studentEmail, studentCourse,
            studentYearOfAdmission, studentCollegeRollNumber, studentPhoneNumber;
    private CircleImageView studentProfilePicture, studentEmailverified, studentPhoneNumberVerified;
    private FloatingActionButton changeProfilePicture;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private DocumentReference documentReference;

    public StudentProfile() { }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationView navigationView=(NavigationView)getActivity().findViewById(R.id.nav_view);
        navigationView.getCheckedItem().setChecked(true);
        navigationView.getCheckedItem().setEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_student_profile, container, false);
        studentName=view.findViewById(R.id.studentName);
        studentEmail=view.findViewById(R.id.studentEmail);
        studentCourse=view.findViewById(R.id.studentCourse);
        studentYearOfAdmission=view.findViewById(R.id.studentYearOfAdmission);
        studentCollegeRollNumber=view.findViewById(R.id.studentCollegeRollNumber);
        studentPhoneNumber=view.findViewById(R.id.studentPhoneNumber);
        studentProfilePicture=view.findViewById(R.id.studentProfilePicture);
        studentEmailverified=view.findViewById(R.id.studentEmailVerified);
        studentPhoneNumberVerified=view.findViewById(R.id.studentPhoneNumberVerified);
        changeProfilePicture=view.findViewById(R.id.changeProfilePicture);

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        firestore=FirebaseFirestore.getInstance();

        if (user.getPhotoUrl()!=null) {
            Glide.with(getContext()).load(user.getPhotoUrl())
                    .into(studentProfilePicture);
        }

        documentReference=firestore.document(getArguments().getString("Document Reference"));

        documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                studentName.setText(value.getString("Name"));
                studentEmail.setText(value.getString("Email"));
                studentCourse.setText(value.getString("Course"));
                studentYearOfAdmission.setText(value.get("Year of Admission").toString());
                studentCollegeRollNumber.setText(value.getString("Roll No"));
                studentPhoneNumber.setText("+"+value.get("Phone Number").toString());

                if (value.getBoolean("Is Email Verified?")) {
                    studentEmailverified.setVisibility(View.VISIBLE);
                }
                else {
                    studentEmailverified.setVisibility(View.GONE);
                }

                if (value.getBoolean("Is Phone Number Verified?")) {
                    studentPhoneNumberVerified.setVisibility(View.VISIBLE);
                }
                else {
                    studentPhoneNumberVerified.setVisibility(View.GONE);
                }
            }
        });

        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode()== Activity.RESULT_OK) {
                            Uri image=result.getData().getData();
                            Bitmap bitmap;
                            try {
                                bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),image);
                                handleUpload(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        changeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(openGalleryIntent);
            }
        });

        return view;
    }

    private void handleUpload(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);

        StorageReference storageReference= FirebaseStorage.getInstance().getReference()
                .child("Profile Pictures (Student)")
                .child(user.getUid()+".jpeg");

        storageReference.putBytes(byteArrayOutputStream.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "Profile Picture Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                        setProfilePicture(storageReference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(getActivity(), "Error Occurred!\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setProfilePicture(StorageReference storageReference) {
        storageReference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder()
                                .setPhotoUri(uri).build();

                        user.updateProfile(userProfileChangeRequest)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getActivity(), "Profile Picture Changed Sucessfully!", Toast.LENGTH_SHORT).show();
                                        Glide.with(getContext()).load(user.getPhotoUrl())
                                                .into(studentProfilePicture);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Toast.makeText(getActivity(), "Error Occurred!\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NavigationView navigationView=(NavigationView)getActivity().findViewById(R.id.nav_view);
        navigationView.getCheckedItem().setChecked(false);
        navigationView.getCheckedItem().setEnabled(true);
    }
}