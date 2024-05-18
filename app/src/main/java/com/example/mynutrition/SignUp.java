package com.example.mynutrition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private EditText mobileNumber_v,firstName_v,surname_v,password_v,confirmPassword_v,email_v;
    private String mobileNumber,surname,firstName,password,confirmPassword,email;
    private ImageButton arrow;
    private  int  count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        arrow = findViewById(R.id.arrow);
        arrow.setVisibility(View.INVISIBLE);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frameLayout,new PhoneNumber_F());
        ft.commit();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    public void goToLogin(View v)
    {
        Intent login= new Intent(this,LogIn.class);
        startActivity(login);
        finish();
    }
    public void continue_button(View v) {
        if(count==0&& validateMobileNumberEmail()) {
        replace_fragment(new Name_F());
        count++;
        arrow.setVisibility(View.VISIBLE);
        } else if (count==1&&validateName()) {

            replace_fragment(new Password_F());
            count++;
        }
        else if(count==2&&validatePassword()){
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignUp.this, "You have been registered. Please verify your email.", Toast.LENGTH_SHORT).show();
                        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    FirebaseDatabase db = FirebaseDatabase.getInstance("https://mynutrition-ab250-default-rtdb.asia-southeast1.firebasedatabase.app/");
                                    DatabaseReference dbref = db.getReference("User");
                                    User user = new User(firstName + " " + surname,mobileNumber);
                                    dbref.child(auth.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            count++;
                                            Intent signup=new Intent(SignUp.this, LogIn.class);
                                            startActivity(signup);
                                            finish();
                                        }
                                    });
                                }
                                else{
                                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
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
            replace_fragment(new PhoneNumber_F());
            
            arrow.setVisibility(View.INVISIBLE);
            count--;
        } else if (count==2) {
            replace_fragment(new Name_F());
            count--;
        }
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
    public boolean validateMobileNumberEmail() {
        mobileNumber_v = findViewById(R.id.mobileNumber);
        mobileNumber = mobileNumber_v.getText().toString();
        email_v=findViewById(R.id.email);
        email=email_v.getText().toString();
        if (mobileNumber.length() != 10 && mobileNumber.length() != 11) {
            Toast.makeText(this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mobileNumber.length() == 10 && mobileNumber.charAt(0) != '3') {
            Toast.makeText(this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mobileNumber.length() == 11 && mobileNumber.charAt(0) != '0' && mobileNumber.charAt(1) != '3') {
            Toast.makeText(this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!email.contains("@") || !email.contains(".")) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent login= new Intent(this,LogIn.class);
        startActivity(login);
        super.onBackPressed();
    }
}