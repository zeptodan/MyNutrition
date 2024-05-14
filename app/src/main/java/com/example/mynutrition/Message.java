package com.example.mynutrition;

import com.google.firebase.Timestamp;

public class Message {
    private String text;
    private String senderId;
    public Message() {
    }

    public Message(String text, String senderId) {
        this.text = text;
        this.senderId = senderId;
    }

    public String getText() {
        return text;
    }

    public String getSenderId() {
        return senderId;
    }
}
