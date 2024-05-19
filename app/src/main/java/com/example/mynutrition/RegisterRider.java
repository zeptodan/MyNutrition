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

public class RegisterRider extends AppCompatActivity {
    TextInputEditText vehiclemod_v,vehiclereg_v, CNIC_v;
    String CNIC,vehiclemod,vehiclereg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_rider);

        CNIC_v=findViewById(R.id.CNIC);
        vehiclemod_v=findViewById(R.id.vehiclemod);
        vehiclereg_v=findViewById(R.id.vehiclereg);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void registerr(View v){
        CNIC=CNIC_v.getText().toString();
        vehiclemod=vehiclemod_v.getText().toString();
        vehiclereg=vehiclereg_v.getText().toString();
         if (vehiclemod.isEmpty()) {
            Toast.makeText(this, "Invalid registration number", Toast.LENGTH_SHORT).show();
             return;
        }
         else if(vehiclereg.isEmpty())
         {
             Toast.makeText(this, "Invalid vehicle registration number", Toast.LENGTH_SHORT).show();
             return;
         }
        else if (CNIC.length()!=12) {
            Toast.makeText(this, "Invalid CNIC", Toast.LENGTH_SHORT).show();
             return;
        }
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://mynutrition-ab250-default-rtdb.asia-southeast1.firebasedatabase.app/");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String id = auth.getCurrentUser().getUid();
        DatabaseReference dbref = db.getReference("User");
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        db.getReference("nutritionist").child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Nutritionist nutritionist = task.getResult().getValue(Nutritionist.class);
                if (nutritionist == null){
                    dbref.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            User user = task.getResult().getValue(User.class);
                            Rider rider = new Rider(user.getName(),user.getPhonenum(),user.getId(),CNIC,vehiclemod,vehiclereg);
                            db.getReference("rider").child(user.getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    Rider rider = task.getResult().getValue(Rider.class);
                                    if (rider == null){
                                        db.getReference("rider").child(user.getId()).setValue(rider).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                db.getReference("User").child(user.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Intent home=new Intent(RegisterRider.this, Homepage.class);
                                                        startActivity(home);
                                                        finish();
                                                    }
                                                });
                                            }
                                        });
                                    }
                                    else
                                        Toast.makeText(RegisterRider.this, "You are already registered as rider", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                else
                    Toast.makeText(RegisterRider.this, "You are already registered as nutritionist", Toast.LENGTH_SHORT).show();
            }
        });
    }
}