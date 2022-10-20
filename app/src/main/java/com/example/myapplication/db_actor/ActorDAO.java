package com.example.myapplication.db_actor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Actor;

import java.util.List;

@Dao
public interface ActorDAO {
    @Insert
    void insert(Actor actor);

    @Query("SELECT * FROM actor ORDER BY `FullName` ASC")
    List<Actor> getAllActors();

    @Update
    void update(Actor actor);

    @Delete
    void delete(Actor actor);

    @Query("DELETE FROM actor")
    void deleterAll();

    @Query("SELECT * FROM actor WHERE FullName LIKE :inputName")
    Actor searchActor(String inputName);
}
