package com.example.mynutrition.ui.messages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynutrition.Nutritionist;
import com.example.mynutrition.Person;
import com.example.mynutrition.R;
import com.example.mynutrition.SelectAdapter;
import com.example.mynutrition.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MessageFragment extends Fragment {

    RecyclerView selectview;
    SelectAdapter selectadapter;
    ArrayList<Person> people;
    public MessageFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectview = view.findViewById(R.id.selectview);
        selectadapter = new SelectAdapter(view.getContext());
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
                            selectadapter.setPeople(people);
                            selectview.setAdapter(selectadapter);
                            selectview.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        }
                    });
                }
                else{
                    db.getReference("User").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            Iterable<DataSnapshot> users = task.getResult().getChildren();
                            User user;
                            for (DataSnapshot ds: users){
                                user = ds.getValue(User.class);
                                people.add(user);
                            }
                            selectadapter.setPeople(people);
                            selectview.setAdapter(selectadapter);
                            selectview.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        }
                    });
                }
            }
        });
    }
}