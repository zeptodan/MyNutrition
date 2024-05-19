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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterNutritionist extends AppCompatActivity {
    TextInputEditText instituteName_v, CNIC_v;
    String institureName,CNIC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_nutritionist);

        instituteName_v=findViewById(R.id.institutename);
        CNIC_v=findViewById(R.id.CNIC);





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void registern(View v){
        institureName=instituteName_v.getText().toString();
        CNIC=CNIC_v.getText().toString();
        if(institureName.isEmpty())
        {
            Toast.makeText(this, "Invalid Institute name", Toast.LENGTH_SHORT).show();
            return;
        } else if (CNIC.isEmpty()||CNIC.length()<11) {
            Toast.makeText(this, "Invalid CNIC", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        db.getReference("user").child(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                User user = task.getResult().getValue(User.class);
                Nutritionist nutritionist = new Nutritionist(user.getName(),user.getPhonenum(),user.getId(),false,CNIC,institureName);
                db.getReference("nutritionist").child(user.getId()).setValue(nutritionist).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent home=new Intent(RegisterNutritionist.this, Homepage.class);
                        startActivity(home);
                        finish();
                    }
                });
            }
        });
    }
}