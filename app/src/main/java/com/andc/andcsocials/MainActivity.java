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

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FloatingActionButton home;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private String userID="";
    private DocumentReference documentReference;
    private TextView username;
    private Fragment fragment;

    public interface ReadStudentName {
        void onResponse(String name);
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
                        .setTopRightCorner(CornerFamily.ROUNDED,30)
                        .setBottomRightCorner(CornerFamily.ROUNDED,30)
                        .build());
        toolbar.getOverflowIcon().setTint(getColor(R.color.colorGradientStart));

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        firestore=FirebaseFirestore.getInstance();

        View headerLayout = navigationView.getHeaderView(0);
        username=headerLayout.findViewById(R.id.username);

        userID=user.getUid();

        readStudentName(new ReadStudentName() {
            @Override
            public void onResponse(String name) {
                username.setText(name);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id=item.getItemId();
                switch (id) {
                    case R.id.profileMenuItem:
                        break;
                    case R.id.applicationStatusMenuItem:
                        break;
                    case R.id.verifyPhoneNumberMenuItem:
                        Intent verifyPhoneNumberIntent=new Intent(getApplicationContext(), AuthenticatePhoneNumber.class);
                        verifyPhoneNumberIntent.putExtra("registrationType","Student");
                        startActivity(verifyPhoneNumberIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                    case R.id.verifyEmailMenuItem:
                        Intent verifyEmailIntent=new Intent(getApplicationContext(), AuthenticateEmail.class);
                        verifyEmailIntent.putExtra("registrationType","Student");
                        startActivity(verifyEmailIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                    case R.id.settingsMenuItem:
                        break;
                    case R.id.supportMenuItem:
                        break;
                    case R.id.logoutMenuItem:
                        logOut();
                        break;
                    case R.id.deleteMenuItem:
                        break;
                }
                return true;
            }
        });

        home=findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new StudentHome();
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentManager.popBackStack();
                fragmentTransaction.replace(R.id.frame,fragment).commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        Toast.makeText(this, username.getText().toString(), Toast.LENGTH_SHORT).show();

    }

    private void readStudentName(ReadStudentName readStudentName) {
        documentReference=firestore.collection("Student")
                .document(userID);

        documentReference.addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value,
                                @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                readStudentName.onResponse(value.getString("Full Name"));
            }
        });
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
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment f) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,f).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.addToBackStack(null);
    }

    public void logOut() {
        firebaseAuth.signOut();
        Intent startSignIn=new Intent(getApplicationContext(),SignIn.class);
        startSignIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        MainActivity.this.finish();
        startActivity(startSignIn, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}