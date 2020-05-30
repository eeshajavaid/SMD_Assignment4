package com.example.assignment4;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyDao {
    @Insert
    public void addUser(User user);

    @Query("select * from User")
    public List<User> getUsers();

    @Query("select * from User where name like :name and email like :email")
    public User getUser(String name, String email);
}
