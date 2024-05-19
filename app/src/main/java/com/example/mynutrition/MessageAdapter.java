package com.example.mynutrition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    ArrayList<Message> messages = new ArrayList<>();
    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message,parent,false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receivermessage,parent,false);
        ViewHolder Holder = new ViewHolder(view);
        return Holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (userid.equals(messages.get(position).getSenderId()))
            holder.imessage.setText(messages.get(position).getText());
        else
            holder.receivermessages.setText(messages.get(position).getText());

    }

    @Override
    public int getItemViewType(int position) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser().getUid().equals(messages.get(position).getSenderId()))
            return 0;
        return 1;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView imessage;
        private TextView receivermessages;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imessage = itemView.findViewById(R.id.messages);
            receivermessages = itemView.findViewById(R.id.receivermessages);
        }
    }
}
