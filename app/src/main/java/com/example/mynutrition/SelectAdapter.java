package com.example.mynutrition;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynutrition.ui.appointments.AppointmentFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder>{

    ArrayList<Person> people = new ArrayList<>();
    Context context;
    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public SelectAdapter(Context context) {
        this.context = context;
    }

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
        holder.selecttextname.setText(people.get(position).getName());
        holder.selecttextphone.setText(people.get(position).getPhonenum());
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat=new Intent(context, Chat.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", people.get(holder.getAdapterPosition()).getId());
                chat.putExtras(bundle);
                context.startActivity(chat);
            }
        });
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
        private TextView selecttextname;
        private TextView selecttextphone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            select = itemView.findViewById(R.id.selectcard);
            selecttextname = itemView.findViewById(R.id.selecttextname);
            selecttextphone = itemView.findViewById(R.id.selecttextphone);
        }
    }
}

