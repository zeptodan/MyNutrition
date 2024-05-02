package com.example.mynutrition;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity2 extends AppCompatActivity {
    private EditText mobileNumber_t,firstName_t,secondName_t,username_t,password;
    private ImageButton arrow;
    private int count=0;
    private String firstName,secondName,username,mobileNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        arrow = findViewById(R.id.arrow);
        arrow.setVisibility(View.INVISIBLE);
        fragment_change(new PhoneNumber_F());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    Name_F f=new Name_F();
    Username_F f2= new Username_F();
    Password_F f3=new Password_F();
    public void continue_button(View v)
    {
        if(count==0) {
            fragment_change(f);
            count++;
            arrow.setVisibility(View.VISIBLE);
        } else if (count==1) {
            fragment_change(f2);
            count++;
        }
        else if(count==2){
            fragment_change(f3);
            count++;
        }
    }

    public void fragment_change(Fragment f)
    {
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.add(R.id.frameLayout,f);
        ft.commit();

    }
    public void back(View v)
    {
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        if(count==1)
        {
            ft.remove(f);
            ft.commit();
            arrow.setVisibility(View.INVISIBLE);
            count--;
        } else if (count==2) {
            ft.remove(f2);
            ft.commit();
            count--;
        }
        else if (count==3) {
            ft.remove(f3);
            ft.commit();
            count--;
        }
    }
}