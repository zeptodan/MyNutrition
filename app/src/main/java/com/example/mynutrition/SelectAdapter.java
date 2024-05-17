package com.example.mynutrition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder>{

    ArrayList<Person> people = new ArrayList<>();
    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select,parent,false);
        ViewHolder Holder = new ViewHolder(view);
        return Holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }
    @Override
    public int getItemCount() {
        return people.size();
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView select;
        private TextView selecttext;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            select = itemView.findViewById(R.id.selectcard);
            selecttext = itemView.findViewById(R.id.selecttext);
        }
    }
}

