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

        institureName=instituteName_v.getText().toString();
        CNIC=CNIC_v.getText().toString();



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void registern(View v){
        if(institureName.isEmpty())
        {
            Toast.makeText(this, "Invalid Institute name", Toast.LENGTH_SHORT).show();
        } else if (CNIC.isEmpty()||CNIC.length()<11) {
            Toast.makeText(this, "Invalid CNIC", Toast.LENGTH_SHORT).show();
        }
    }

}