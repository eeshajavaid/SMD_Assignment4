package com.example.assignment4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ContactList extends AppCompatActivity {
    public static MyAppDatabase myAppDatabase;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        myAppDatabase = Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"database").allowMainThreadQueries().build();

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<User> users = new ArrayList<>();
        //User user = new User("Eesha",123,"eeshajaved22@gmail.com");
        //myAppDatabase.myDao().addUser(user);
        //deleteDatabase("database");
        users = myAppDatabase.myDao().getUsers();

        adapter = new RecyclerAdapter(users, this);
        recyclerView.setAdapter(adapter);
        LinearLayout addContact = findViewById(R.id.addContact);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactList.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
