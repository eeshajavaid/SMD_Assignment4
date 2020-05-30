package com.example.assignment4;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.net.URI;

@Entity
public class User {
    String name;
    @PrimaryKey
    @NonNull String number;
    String email;
    String image_uri;

    public User(String name, String number, String email, String image_uri) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.image_uri = image_uri;
    }


}
