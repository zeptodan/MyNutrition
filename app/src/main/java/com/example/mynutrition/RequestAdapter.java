package com.example.mynutrition;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynutrition.ui.appointments.AppointmentFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder>{

    ArrayList<Person> people = new ArrayList<>();
    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    int type;
    Context context;
    public RequestAdapter(int type,Context context) {
        this.type = type;
        this.context = context;
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
                    FirebaseDatabase db = FirebaseDatabase.getInstance("https://mynutrition-ab250-default-rtdb.asia-southeast1.firebasedatabase.app/");
                    db.getReference("useraccepted").child(userid).child(people.get(holder.getAdapterPosition()).getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            Nutritionist nutritionist = task.getResult().getValue(Nutritionist.class);
                            if (nutritionist == null){
                                DatabaseReference dbref = db.getReference("sendrequest").child(people.get(holder.getAdapterPosition()).getId()).child(userid);
                                db.getReference("User").child(userid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        User user = task.getResult().getValue(User.class);
                                        dbref.setValue(user);
                                        Toast.makeText(context, "Request Sent.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else{
                                Toast.makeText(context, "Request has already been accepted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
        else{
            holder.name.setText(people.get(position).getName());
            holder.num.setText(people.get(position).getPhonenum());
            holder.yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase db = FirebaseDatabase.getInstance("https://mynutrition-ab250-default-rtdb.asia-southeast1.firebasedatabase.app/");
                    DatabaseReference dbref = db.getReference("sendrequest").child(userid).child(people.get(holder.getAdapterPosition()).getId());
                    dbref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            db.getReference("User").child(people.get(holder.getAdapterPosition()).getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    User user = task.getResult().getValue(User.class);
                                    db.getReference("nutaccepted").child(userid).child(people.get(holder.getAdapterPosition()).getId()).setValue(user);
                                    db.getReference("nutritionist").child(userid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            Nutritionist nutritionist = task.getResult().getValue(Nutritionist.class);
                                            db.getReference("useraccepted").child(people.get(holder.getAdapterPosition()).getId()).child(userid).setValue(nutritionist);
                                            people.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(holder.getAdapterPosition());
                                            Toast.makeText(context, "Request Accepted.", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            });
                        }
                    });
                }
            });
            holder.no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase db = FirebaseDatabase.getInstance("https://mynutrition-ab250-default-rtdb.asia-southeast1.firebasedatabase.app/");
                    DatabaseReference dbref = db.getReference("sendrequest").child(userid).child(people.get(holder.getAdapterPosition()).getId());
                    dbref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            people.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                            Toast.makeText(context, "Request Denied.", Toast.LENGTH_SHORT).show();
                        }
                    });
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


