package com.example.mynutrition;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;

public class Chat extends AppCompatActivity {
    RecyclerView chatview;
    ArrayList<Message> messages;
    DatabaseReference dbref;

    String senderId;
    String receiverId;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        chatview = findViewById(R.id.chatview);
        MessageAdapter adapter = new MessageAdapter();
        messages = new ArrayList<>();
        //first display.
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://mynutrition-ab250-default-rtdb.asia-southeast1.firebasedatabase.app/");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        receiverId = getIntent().getExtras().getString("id");
        senderId = auth.getCurrentUser().getUid();
        String[] paths = new String[2];
        paths[0] = senderId;
        paths[1] = receiverId;
        if (paths[0].compareTo(paths[1]) > 0){
            String temp = paths[0];
            paths[0] = paths[1];
            paths[1] = temp;
        }
        path = paths[0] + paths[1];
        dbref = db.getReference("messages/" + path);
        chatview.setAdapter(adapter);
        chatview.setLayoutManager(new LinearLayoutManager(Chat.this));
        //Listener
        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                messages.add(message);
                adapter.setMessages(messages);
                adapter.notifyItemInserted(messages.size()-1);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void sendMessage(View v){
        TextInputEditText edittext = findViewById(R.id.sendMessage);
        String text = edittext.getText().toString();
        if (text.isEmpty()){
            return;
        }
        edittext.setText("");
        Message message = new Message(text,senderId);
        dbref.push().setValue(message);
    }
}