package com.example.mynutrition;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Bmi extends AppCompatActivity {

    private EditText weightInput;
    private EditText heightInput;
    private TextView bmiResult;
    private TextView normalRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bmi);

        weightInput = findViewById(R.id.weightInput);
        heightInput = findViewById(R.id.heightInput);
        bmiResult = findViewById(R.id.bmiResult);
        normalRange = findViewById(R.id.normalRange);
        Button calculateButton = findViewById(R.id.button3);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void calculateBMI() {
        String weightStr = weightInput.getText().toString();
        String heightStr = heightInput.getText().toString();

        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            Toast.makeText(this, "Please enter both weight and height", Toast.LENGTH_SHORT).show();
            return;
        }

        double weight = Double.parseDouble(weightStr);
        double height = Double.parseDouble(heightStr);

        if (weight <= 0 || height <= 0) {
            Toast.makeText(this, "Weight and height must be positive numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        double heightInMeters = height * 0.0254; // Convert height from inches to meters
        double bmi = weight / (heightInMeters * heightInMeters);
        String bmiCategory = getBMICategory(bmi);

        bmiResult.setText(String.format("BMI: %.2f - %s", bmi, bmiCategory));
        normalRange.setText(getNormalBMIRange());
    }

    private String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 24.9) {
            return "Normal weight";
        } else if (bmi < 29.9) {
            return "Overweight";
        } else {
            return "Obesity";
        }
    }

    private String getNormalBMIRange() {
        return "Normal BMI range: 18.5 - 24.9";
    }
}
