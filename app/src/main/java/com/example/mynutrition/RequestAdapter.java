package com.example.mynutrition;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynutrition.ui.appointments.AppointmentFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder>{

    ArrayList<Person> people = new ArrayList<>();
    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    int type;

    public RequestAdapter(int type) {
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (type == 0)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sendrequest,parent,false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.getrequest,parent,false);
        ViewHolder Holder = new ViewHolder(view);
        return Holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (type == 0){
            holder.nutname.setText(people.get(position).getName());
            holder.nutnum.setText(people.get(position).getPhonenum());
            holder.send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference dbref = db.getReference("sendrequest").child(people.get(holder.getAdapterPosition()).getId());
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    dbref.setValue(auth.getCurrentUser().getUid());
                }
            });
        }
        else{
            holder.name.setText(people.get(position).getName());
            holder.num.setText(people.get(position).getPhonenum());
            holder.yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return people.size();
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView getcard;
        private TextView name;
        private TextView num;
        private Button yes;
        private Button no;

        private CardView sendcard;
        private TextView nutname;
        private TextView nutnum;
        private Button send;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            getcard = itemView.findViewById(R.id.selectcard);
            name = itemView.findViewById(R.id.name);
            num = itemView.findViewById(R.id.num);
            yes = itemView.findViewById(R.id.yes);
            no = itemView.findViewById(R.id.no);
            sendcard = itemView.findViewById(R.id.sendcard);
            nutname = itemView.findViewById(R.id.nutname);
            nutnum = itemView.findViewById(R.id.nutnum);
            send = itemView.findViewById(R.id.send);
        }
    }
}


