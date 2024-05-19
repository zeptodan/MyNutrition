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

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
        } else if (CNIC.length()!=12) {
            Toast.makeText(this, "Invalid CNIC", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://mynutrition-ab250-default-rtdb.asia-southeast1.firebasedatabase.app/");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String id = auth.getCurrentUser().getUid();
        DatabaseReference dbref = db.getReference("User");
        db.getReference("rider").child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Rider rider = task.getResult().getValue(Rider.class);
                if (rider == null){
                    dbref.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            User user = task.getResult().getValue(User.class);
                            Nutritionist nutritionist = new Nutritionist(user.getName(),user.getPhonenum(),user.getId(),true,CNIC,institureName);
                            db.getReference("nutritionist").child(user.getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    Nutritionist nutritionist = task.getResult().getValue(Nutritionist.class);
                                    if (nutritionist == null){
                                        db.getReference("nutritionist").child(user.getId()).setValue(nutritionist).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                db.getReference("User").child(user.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
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
                                    else
                                        Toast.makeText(RegisterNutritionist.this, "You are already registered sa nutritionist", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                else
                    Toast.makeText(RegisterNutritionist.this, "You are already registered as rider", Toast.LENGTH_SHORT).show();
            }
        });
    }

}