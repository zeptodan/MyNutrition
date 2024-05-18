package com.example.mynutrition;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

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
            }
             else if(vehiclereg.isEmpty())
             {
                 Toast.makeText(this, "Invalid vehicle registration number", Toast.LENGTH_SHORT).show();
             }
            else if (CNIC.isEmpty()||CNIC.length()<11) {
                Toast.makeText(this, "Invalid CNIC", Toast.LENGTH_SHORT).show();
            }
            else
                 Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }
}