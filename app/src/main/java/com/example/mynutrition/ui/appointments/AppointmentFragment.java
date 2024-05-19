package com.example.mynutrition.ui.appointments;

import android.health.connect.datatypes.NutritionRecord;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynutrition.Chat;
import com.example.mynutrition.Homepage;
import com.example.mynutrition.Nutritionist;
import com.example.mynutrition.Person;
import com.example.mynutrition.R;
import com.example.mynutrition.RequestAdapter;
import com.example.mynutrition.SelectAdapter;
import com.example.mynutrition.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;


public class AppointmentFragment extends Fragment {
    RecyclerView requestview;
    RequestAdapter requestadapter;
    ArrayList<Person> people;
    public AppointmentFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestview = view.findViewById(R.id.requestview);
        people  = new ArrayList<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://mynutrition-ab250-default-rtdb.asia-southeast1.firebasedatabase.app/");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String id = auth.getCurrentUser().getUid();
        DatabaseReference dbref = db.getReference("Nutritionist").child(id);
        dbref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Nutritionist nutritionist = task.getResult().getValue(Nutritionist.class);
                if (nutritionist == null){
                    requestadapter = new RequestAdapter(0);
                    db.getReference("Nutritionist").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            Iterable<DataSnapshot> nutritionists = task.getResult().getChildren();
                            Nutritionist nutritionist;
                            for (DataSnapshot ds: nutritionists){
                                nutritionist = ds.getValue(Nutritionist.class);
                                if (nutritionist.isAvailable())
                                    people.add(nutritionist);
                            }
                            requestadapter.setPeople(people);
                            requestview.setAdapter(requestadapter);
                            requestview.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        }
                    });
                }
                else{
                    requestadapter = new RequestAdapter(1);
                }
            }
        });
    }
}