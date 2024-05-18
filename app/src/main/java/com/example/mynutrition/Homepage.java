package com.example.mynutrition;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.NavigationUI;

import com.example.mynutrition.ui.Bmi;
import com.example.mynutrition.ui.Getplan;
import com.example.mynutrition.ui.appointments.AppointmentFragment;
import com.example.mynutrition.ui.home.HomeFragment;
import com.example.mynutrition.ui.messages.MessageFragment;
import com.example.mynutrition.ui.notifications.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


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
        drawerLayout = findViewById(R.id.drawerlayout);
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
                Toast.makeText(Homepage.this, "logout", Toast.LENGTH_SHORT).show();
                return true;
            }

            else if (itemID==R.id.register_rider){
                Toast.makeText(Homepage.this, "registor rider", Toast.LENGTH_SHORT).show();
                return true;
            }
            else if (itemID==R.id.register_nutrition){
                Toast.makeText(Homepage.this, "Registor nutrionist", Toast.LENGTH_SHORT).show();
                return true;
            }
            else if (itemID==R.id.feedback){
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
    public void getplan(View view){
        Intent getplan= new Intent(this, Getplan.class);
        startActivity (getplan);
    }

    public void bmiCalc(View view){
        Intent bmiCalc= new Intent(this, Bmi.class);
        startActivity (bmiCalc);
    }
    }
