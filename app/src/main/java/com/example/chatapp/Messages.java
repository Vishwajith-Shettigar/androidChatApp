package com.example.chatapp;

public class Messages {

    String message;
    String senderUID;
    long timestamp;
    String currenttime;

    public Messages() {
    }

    public Messages(String message, String senderUID, long timestamp, String currenttime) {
        this.message = message;
        this.senderUID = senderUID;
        this.timestamp = timestamp;
        this.currenttime = currenttime;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderUID() {
        return senderUID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getCurrenttime() {
        return currenttime;
    }

    public void setSenderUID(String senderUID) {
        this.senderUID = senderUID;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime;
    }
}
