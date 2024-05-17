package com.example.mynutrition.ui.appointments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynutrition.Person;
import com.example.mynutrition.R;
import com.example.mynutrition.SelectAdapter;

import java.util.ArrayList;


public class AppointmentFragment extends Fragment {
    RecyclerView selectview;
    SelectAdapter selectadapter;
    ArrayList<Person> people;
    public AppointmentFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        selectview = container.findViewById(R.id.selectview);
        selectadapter = new SelectAdapter();
        people  = new ArrayList<>();
        selectadapter.setPeople(people);
        selectview.setAdapter(selectadapter);
        return inflater.inflate(R.layout.fragment_appointment, container, false);
    }
}