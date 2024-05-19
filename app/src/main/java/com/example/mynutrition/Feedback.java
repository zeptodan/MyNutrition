package com.example.mynutrition;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Feedback extends AppCompatActivity {
TextView feedback1_v,rickroll_v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feedback);
        feedback1_v=findViewById(R.id.feedback1);
        rickroll_v=findViewById(R.id.rickroll);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
   public void rickroll(View v){
        gotoUrl("https://tinyurl.com/mynutrition2");
    }
    public void  gotoUrl(String s)
    {
        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {

        }
        feedback1_v.setText("For your actual feedback contact:");
        rickroll_v.setTextColor(getColor(R.color.black));
        rickroll_v.setText("03257257441");
    }

}