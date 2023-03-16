package com.example.chatapp;

public class Firebasemodel {
    String name;
    String UID;
    String image;
    String status;


    public Firebasemodel() {
    }

    public Firebasemodel(String name, String UID, String image, String status) {
        this.name = name;
        this.UID = UID;
        this.image = image;
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getUID() {
        return UID;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }
}
