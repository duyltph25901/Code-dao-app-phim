package com.example.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "userApp")
public class UserApp implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo (name = "UserName")
    private String name;
    @ColumnInfo (name = "EmailUser")
    private String email;
    @ColumnInfo (name = "PassUser")
    private String pass;

    public UserApp(String name, String email, String pass) {
        this.name = name;
        this.email = email;
        this.pass = pass;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
