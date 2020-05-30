package com.example.assignment4;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactScreen extends AppCompatActivity {
    TextView name, phone, email;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_screen);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        imageView = findViewById(R.id.image);
        String getName = getIntent().getStringExtra("name");
        String getEmail = getIntent().getStringExtra("email");
        User user = ContactList.myAppDatabase.myDao().getUser(getName, getEmail);
        name.setText(user.name);
        phone.setText(user.number);
        email.setText(user.email);
        Uri uri = Uri.parse(user.image_uri);
        imageView.setImageURI(uri);
    }
}
