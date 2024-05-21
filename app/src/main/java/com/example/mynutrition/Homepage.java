package com.example.mynutrition;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mynutrition.ui.appointments.AppointmentFragment;
import com.example.mynutrition.ui.home.HomeFragment;
import com.example.mynutrition.ui.messages.MessageFragment;
import com.example.mynutrition.ui.notifications.NotificationFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    TextView nameofuser, emaildrawer;
    NavigationView nav_view;
    Toolbar toolbar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        drawerLayout = findViewById(R.id.drawerLayout);
        nav_view = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        View headerView= nav_view.getHeaderView(0);

        nameofuser = headerView.findViewById(R.id.nameofuser);
        emaildrawer = headerView.findViewById(R.id.emaildrawer);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open_drawer, R.string.navigation_open_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.bringToFront();
        nav_view.bringToFront();
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://mynutrition-ab250-default-rtdb.asia-southeast1.firebasedatabase.app/");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        db.getReference("User").child(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                User user = task.getResult().getValue(User.class);
                if (user == null){
                    db.getReference("nutritionist").child(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            Nutritionist nutritionist = task.getResult().getValue(Nutritionist.class);
                            if (nutritionist == null){
                                db.getReference("rider").child(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        Rider rider = task.getResult().getValue(Rider.class);
                                        nameofuser.setText(rider.getName());
                                        emaildrawer.setText(auth.getCurrentUser().getEmail());
                                    }
                                });
                            }
                            else{
                                nameofuser.setText(nutritionist.getName());
                                emaildrawer.setText(auth.getCurrentUser().getEmail());
                            }
                        }
                    });
                }
                else{
                    nameofuser.setText(user.name);
                    emaildrawer.setText(auth.getCurrentUser().getEmail());
                }
            }
        });
        nav_view.setNavigationItemSelectedListener(menuItem -> {
            int itemID=menuItem.getItemId();
            if(itemID==R.id.logout)
            {
                Toast.makeText(Homepage.this, "You have been logged out", Toast.LENGTH_SHORT).show();
                auth.signOut();
                Intent intent= new Intent(this,LogIn.class);
                startActivity(intent);
                return true;
            }

            else if (itemID==R.id.register_rider){
                Intent intent= new Intent(this,RegisterRider.class);
                startActivity(intent);

                return true;
            }
            else if (itemID==R.id.register_nutrition){
                Intent intent= new Intent(this,RegisterNutritionist.class);
                startActivity(intent);

                return true;
            }
            else if (itemID==R.id.feedback){
                Intent intent= new Intent(this,Feedback.class);
                startActivity(intent);

                return true;
            }
            return false;
        });
        ft.add(R.id.framelayoutmain, new HomeFragment());
        ft.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
            else
                super.onBackPressed();
    }
    public void replace_fragment(Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.framelayoutmain, f);
        ft.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.message) {
            replace_fragment(new MessageFragment());
        } else if (id == R.id.appointment) {
            replace_fragment(new AppointmentFragment());
        } else if (id == R.id.home1) {
            replace_fragment(new HomeFragment());
        } else if (id == R.id.notification) {
            replace_fragment(new NotificationFragment());
        }
        return true;
    }

    public void bmiCalc(View v) {
        Intent bmi = new Intent(this, Bmi.class);
        startActivity(bmi);
    }
    public void getplan(View v)
    {
        Intent plan=new Intent(this,Getplan.class);
        startActivity(plan);
    }
    }
