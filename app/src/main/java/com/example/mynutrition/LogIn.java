package com.example.mynutrition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class LogIn extends AppCompatActivity {
    private TextInputEditText username_v,password_v;
    private String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
   public void signUpTextView(View v)
   {
       Intent move_to_signUp=new Intent(this, SignUp.class);
       startActivity(move_to_signUp);
       finish();
   }
   public void loginButton(View v)
   {
       username_v=findViewById(R.id.username);
        password_v=findViewById(R.id.password);
        username=username_v.getText().toString();
        password=password_v.getText().toString();
        if (username.isEmpty()||password.length()<8)
        {
            Toast.makeText(this, "Invalid Username or password", Toast.LENGTH_SHORT).show();
        }

   }
}