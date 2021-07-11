package com.andc.andcsocials;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity implements ResetPasswordDialog.ResetPasswordDialogListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FloatingActionButton home;
    private SwipeRefreshLayout refreshMainActivity;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private String userID="";
    private static String dR="";
    private boolean check=true, internetGone=false;
    private DocumentReference documentReference;
    private TextView username, noInternetMessage;
    private Fragment fragment;
    public static int k=0;

    @Override
    public void checkSignOutStatus(String newPassword) {
        user.updatePassword(newPassword)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Password successfully reset!", Toast.LENGTH_SHORT).show();
                        logOut();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(MainActivity.this, "Fail to reset password!\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=findViewById(R.id.drawerLayout);

        toolbar=findViewById(R.id.bottomAppBar);
        setSupportActionBar(toolbar);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        toggle.setHomeAsUpIndicator(R.drawable.menu);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView=findViewById(R.id.nav_view);
        MaterialShapeDrawable navViewBackground = (MaterialShapeDrawable) navigationView.getBackground();
        navViewBackground.setShapeAppearanceModel(
                navViewBackground.getShapeAppearanceModel()
                        .toBuilder()
                        .setBottomRightCorner(CornerFamily.ROUNDED,30)
                        .build());
        toolbar.getOverflowIcon().setTint(getColor(R.color.colorGradientStart));

        refreshMainActivity=findViewById(R.id.refreshMainActivity);
        noInternetMessage=findViewById(R.id.noInternetMessage);
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo()==null) {
            internetGone=true;
            noInternetMessage.setVisibility(View.VISIBLE);
        }
        refreshMainActivity.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (connectivityManager.getActiveNetworkInfo()==null) {
                    noInternetMessage.setVisibility(View.VISIBLE);
                    internetGone=true;
                }
                else {
                    noInternetMessage.setVisibility(View.GONE);
                    if (internetGone) {
                        internetGone=false;
                        finish();
                        startActivity(getIntent(),ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }
                refreshMainActivity.setRefreshing(false);
            }
        });

        fragment=new StudentHome();
        Bundle bundle = new Bundle();
        bundle.putString("Society Type",valueOf(check));
        fragment.setArguments(bundle);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment).commit();

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        firestore=FirebaseFirestore.getInstance();

        View headerLayout = navigationView.getHeaderView(0);
        username=headerLayout.findViewById(R.id.username);

        userID=user.getUid();

        if (k==0) {
            getDocumentReference();
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id=item.getItemId();
                switch (id) {
                    case R.id.profileMenuItem:
                        if (!dR.isEmpty()) {
                            fragment = new StudentProfile();
                            Bundle bundle = new Bundle();
                            bundle.putString("Document Reference", dR);
                            fragment.setArguments(bundle);
                            loadFragment(fragment);
                        }
                        break;
                    case R.id.applicationStatusMenuItem:
                        break;
                    case R.id.verifyPhoneNumberMenuItem:
                        if (!dR.isEmpty()) {
                            Intent verifyPhoneNumberIntent = new Intent(getApplicationContext(), AuthenticatePhoneNumber.class);
                            verifyPhoneNumberIntent.putExtra("documentReference", dR);
                            startActivity(verifyPhoneNumberIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                        break;
                    case R.id.verifyEmailMenuItem:
                        if (!dR.isEmpty()) {
                            Intent verifyEmailIntent = new Intent(getApplicationContext(), AuthenticateEmail.class);
                            verifyEmailIntent.putExtra("documentReference", dR);
                            startActivity(verifyEmailIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                        break;
                    case R.id.settingsMenuItem:
                        break;
                    case R.id.supportMenuItem:
                        startActivity(new Intent(getApplicationContext(),Support.class),ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                    case R.id.logoutMenuItem:
                        logOut();
                        break;
                    case R.id.resetPasswordMenuItem:
                        ResetPasswordDialog resetPasswordDialog=new ResetPasswordDialog();
                        resetPasswordDialog.show(getSupportFragmentManager(),"Reset Password Dialog");
                        break;
                }
                return true;
            }
        });

        home=findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navigationView.getCheckedItem()!=null) {
                    navigationView.getCheckedItem().setChecked(false);
                    navigationView.getCheckedItem().setEnabled(true);
                }
                fragment=new StudentHome();
                Bundle bundle = new Bundle();
                bundle.putString("Society Type",valueOf(check));
                fragment.setArguments(bundle);
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.frame,fragment).commit();
                fragmentTransaction.addToBackStack(null);
            }
        });
    }

    private void getDocumentReference() {
        firestore.collectionGroup("StudentID")
                .whereEqualTo("Email",user.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                documentReference=document.getReference();
                                String name=document.getString("Name");
                                if (user.getDisplayName()==null) {
                                    UserProfileChangeRequest request=new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name).build();
                                    user.updateProfile(request);
                                }
                            }
                        }
                    }
                });

        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo()!=null && k==0) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int i=0;
                    while (documentReference==null) {
                        try {
                            Thread.sleep(500);
                            i++;
                            if (i>10)
                                return;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    documentReference.addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                            username.setText(value.getString("Name"));
                        }
                    });

                    dR=documentReference.getPath();
                    k++;
                }
            });
            thread.start();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!dR.equals("") && k>0) {
            documentReference=firestore.document(dR);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                    username.setText(value.getString("Name"));
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.options_menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.switchMenuItem:
                Bundle bundle = new Bundle();
                check=!check;
                bundle.putString("Society Type",valueOf(check));
                StudentHome studentHome=new StudentHome();
                studentHome.setArguments(bundle);
                loadFragment(studentHome);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment f) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction.replace(R.id.frame,f).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.addToBackStack(null);
    }

    public void logOut() {
        firebaseAuth.signOut();
        Intent startSignIn=new Intent(getApplicationContext(),SignIn.class);
        startSignIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        MainActivity.this.finishAffinity();
        startActivity(startSignIn, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        k=0;
    }
}