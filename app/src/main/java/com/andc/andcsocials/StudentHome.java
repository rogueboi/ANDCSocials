package com.andc.andcsocials;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class StudentHome extends Fragment {

    private ViewPager2 societyInformation;
    int[] societyLogo = {R.drawable.andc_logo,R.drawable.andc_logo,R.drawable.andc_logo,
            R.drawable.andc_logo,R.drawable.andc_logo,R.drawable.andc_logo,
            R.drawable.andc_logo,R.drawable.andc_logo,R.drawable.andc_logo,R.drawable.andc_logo};
    String[] societyName = {"Dhwani","Dhun","Dhwani","Dhun","Dhwani","Dhun","Dhwani","Dhun","Dhwani","Dhun"};

    private FirebaseFirestore firestore;
    private boolean check=true;
    private CollectionReference collectionReference;
    private TextView societyType;
    SocietyInformationAdapter adapter;


    public StudentHome() { }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_student_home, container, false);
        societyInformation=view.findViewById(R.id.societyInformation);

        check=Boolean.parseBoolean(getArguments().getString("Society Type"));
        societyType=view.findViewById(R.id.societyType);
        firestore=FirebaseFirestore.getInstance();




        if (check) {
            collectionReference=firestore.collection("Society").document("Co-curricular")
                    .collection("SocietyID");
            societyType.setText("CO-CURRICULAR SOCIETIES");
        }
        else {
            collectionReference=firestore.collection("Society").document("Departmental")
                    .collection("SocietyID");
            societyType.setText("DEPARTMENTAL SOCIETIES");
        }

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value,
                                @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if (error!=null) {
                    Toast.makeText(getActivity(), "Error Occured!\n"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                int i = 0;
                List<SocietyInformation> list=new ArrayList<>();
                for (DocumentSnapshot documentSnapshot : value) {
                    String Description=documentSnapshot.getString("Description")
                            +"\n\nTeacher Coordinator: "+documentSnapshot.getString("Teacher Coordinator")
                            +"\nStudent Coordinator: "+documentSnapshot.getString("Student Coordinator")
                            +"\nContact: +"+documentSnapshot.get("Phone Number");
                    list.add(new SocietyInformation(societyLogo[i++],documentSnapshot.getString("Name"),Description));
                }
                adapter=new SocietyInformationAdapter(list);
                societyInformation.setClipToPadding(false);
                societyInformation.setClipChildren(false);
                societyInformation.setOffscreenPageLimit(3);
                societyInformation.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
                societyInformation.setAdapter(adapter);
                CompositePageTransformer transformer=new CompositePageTransformer();
                transformer.addTransformer(new MarginPageTransformer(5));
                transformer.addTransformer(new ViewPager2.PageTransformer() {
                    @Override
                    public void transformPage(@NonNull @NotNull View page, float position) {
                        float v=1-Math.abs(position);

                        page.setScaleY(0.8f+v*0.2f);
                    }
                });
                societyInformation.setPageTransformer(transformer);
            }
        });




        return view;
    }
}