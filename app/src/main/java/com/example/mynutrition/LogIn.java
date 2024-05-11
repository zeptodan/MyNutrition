package com.example.mynutrition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {
    private TextInputEditText email_v,password_v;
    private String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){
            if (auth.getCurrentUser().isEmailVerified()){
                Intent home=new Intent(this, Homepage.class);
                startActivity(home);
                finish();
            }
        }
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
       email_v=findViewById(R.id.username);
       password_v=findViewById(R.id.password);
       email=email_v.getText().toString();
       password=password_v.getText().toString();
       FirebaseAuth auth = FirebaseAuth.getInstance();
       auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                   if(auth.getCurrentUser().isEmailVerified()){
                       Intent home=new Intent(LogIn.this, Homepage.class);
                       startActivity(home);
                       finish();
                   }
                   else{
                       Toast.makeText(LogIn.this, "Please verify your email.", Toast.LENGTH_SHORT).show();
                   }
               }
               else{
                   Toast.makeText(LogIn.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
               }
           }
       });
        if (email.isEmpty()||password.length()<8)
        {
            Toast.makeText(this, "Invalid Username or password", Toast.LENGTH_SHORT).show();
        }

   }
}