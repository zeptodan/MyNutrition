package com.example.mynutrition;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
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
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open_drawer, R.string.navigation_open_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav_view.bringToFront();
        nav_view.setNavigationItemSelectedListener(menuItem -> {
            int itemID=menuItem.getItemId();
            if(itemID==R.id.logout)
            {
                Toast.makeText(Homepage.this, "You have been logged out", Toast.LENGTH_SHORT).show();
                FirebaseAuth auth=FirebaseAuth.getInstance();
                auth.signOut();
                Intent intent= new Intent(this,LogIn.class);
                startActivity(intent);
                return true;
            }

            else if (itemID==R.id.register_rider){
                Intent intent= new Intent(this,RegisterRider.class);
                startActivity(intent);
                Toast.makeText(Homepage.this, "register rider", Toast.LENGTH_SHORT).show();

                return true;
            }
            else if (itemID==R.id.register_nutrition){
                Intent intent= new Intent(this,RegisterNutritionist.class);
                startActivity(intent);
                Toast.makeText(Homepage.this, "Register nutritionist", Toast.LENGTH_SHORT).show();

                return true;
            }
            else if (itemID==R.id.feedback){
                Intent intent= new Intent(this,Feedback.class);
                startActivity(intent);
                Toast.makeText(Homepage.this, "feedback", Toast.LENGTH_SHORT).show();

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
    public void bmiCalc(View v)
    {
        Intent bmi=new Intent(this,Bmi.class);
        startActivity(bmi);
    }
    public void getplan(View v)
    {
        Intent plan=new Intent(this,Getplan.class);
        startActivity(plan);
    }
    }
