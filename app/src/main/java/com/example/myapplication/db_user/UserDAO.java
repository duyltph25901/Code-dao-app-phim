package com.example.myapplication.db_user;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.UserApp;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(UserApp user);

    @Update
    void update(UserApp user);

    @Query("SELECT * FROM userApp WHERE EmailUser LIKE :email")
    List<UserApp> searchUserByEmail(String email);

    @Query("SELECT * FROM userApp WHERE EmailUser LIKE :email")
    UserApp searchByEmail(String email);
}
