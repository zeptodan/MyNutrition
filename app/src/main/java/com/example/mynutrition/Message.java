package com.example.mynutrition;

import com.google.firebase.Timestamp;

public class Message {
    String text;
    String senderId;
    Timestamp time;

    public Message() {
    }

    public Message(String text, String senderId, Timestamp time) {
        this.text = text;
        this.senderId = senderId;
        this.time = time;
    }
}
