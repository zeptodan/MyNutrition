package com.example.mynutrition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SignUp extends AppCompatActivity {
    private EditText mobileNumber_v,firstName_v,surname_v,password_v,confirmPassword_v,newUsername_v;
    private String mobileNumber,surname,firstName,password,confirmPassword,newUsername;
    private ImageButton arrow;
    private int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        arrow = findViewById(R.id.arrow);
        arrow.setVisibility(View.INVISIBLE);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frameLayout,f);
            ft.commit();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    PhoneNumber_F f=new PhoneNumber_F();
    Name_F f1=new Name_F();
    Password_F f2=new Password_F();
    public void continue_button(View v)
    {
        if(count==0&& validateMobileNumber()) {
            replace_fragment(f1);
            count++;
            arrow.setVisibility(View.VISIBLE);

        } else if (count==1&&validateName()) {

            replace_fragment(f2);
            count++;
        }
        else if(count==2&&validatePassword()){
            Intent home=new Intent(this, Homepage.class);
            startActivity(home);
        }
    }

    public void replace_fragment(Fragment f)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout,f);
        ft.commit();
    }
    public void back(View v)
    {
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        if(count==1)
        {
            replace_fragment(f);
            
            arrow.setVisibility(View.INVISIBLE);
            count--;
        } else if (count==2) {

            replace_fragment(f1);
            
            count--;
        }
    }
    public boolean validatenewUsername(){
        newUsername_v=findViewById(R.id.newUsername);
        newUsername=newUsername_v.getText().toString();
        if (newUsername.isEmpty())
        {
            Toast.makeText(this, "Invalid username", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }
    public boolean validateName()
    {
        firstName_v=findViewById(R.id.firstName);
        surname_v=findViewById(R.id.surname);
        firstName=firstName_v.getText().toString();
        surname=surname_v.getText().toString();
        if(firstName.isEmpty())
        {
            Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }
    public boolean validatePassword(){
        password_v=findViewById(R.id.password);
        confirmPassword_v=findViewById(R.id.confirmPassword);
        password=password_v.getText().toString();
        confirmPassword=confirmPassword_v.getText().toString();
        if(password.length()<8)
        {
            Toast.makeText(this, "The password must have at least 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!(password.equals(confirmPassword)))
        {
            Toast.makeText(this, "The password doesn't match", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }
    public boolean validateMobileNumber() {
        mobileNumber_v = findViewById(R.id.mobileNumber);
        mobileNumber = mobileNumber_v.getText().toString();
        if (mobileNumber.length() != 10 && mobileNumber.length() != 11) {
            Toast.makeText(this, "Invalid mobile nuumber", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mobileNumber.length() == 10 && mobileNumber.charAt(0) != '3') {
            Toast.makeText(this, "Invalid mobile nuumber", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mobileNumber.length() == 11 && mobileNumber.charAt(0) != '0' && mobileNumber.charAt(1) != '3') {
            Toast.makeText(this, "Invalid mobile nuumber", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;

    }
}