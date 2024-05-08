package com.example.mynutrition;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mynutrition.ui.appointments.AppointmentFragment;
import com.example.mynutrition.ui.home.HomeFragment;
import com.example.mynutrition.ui.messages.MessageFragment;
import com.example.mynutrition.ui.notifications.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Homepage extends AppCompatActivity {
    HomeFragment hf= new HomeFragment();
    AppointmentFragment af=new AppointmentFragment();
    MessageFragment mf=new MessageFragment();
    NotificationFragment nf= new NotificationFragment();
    FragmentManager fm= getSupportFragmentManager();
    FragmentTransaction ft= fm.beginTransaction();
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottomNavigation);
        ft.add(R.id.framelayoutmain,hf);
        ft.commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.message)
                {
                    replace_fragment(new MessageFragment());
                }
                else if(id==R.id.appointment)
                {
                    replace_fragment(new AppointmentFragment());
                }
                else if(id==R.id.home1)
                {
                    replace_fragment(new HomeFragment());
                }
                else if(id==R.id.notification)
                {
                    replace_fragment(new NotificationFragment());
                }
                return true;
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void replace_fragment(Fragment f)
    {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.framelayoutmain,f);
        ft.commit();
    }
    }