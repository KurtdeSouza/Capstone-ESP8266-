package com.example.drone_communication;

public class Message {
    private String text;
    private boolean sentByUser;

    public Message(String text, boolean sentByUser) {
        this.text = text;
        this.sentByUser = sentByUser;
    }

    public String getText() {
        return text;
    }

    public boolean isSentByUser() {
        return sentByUser;
    }
}
