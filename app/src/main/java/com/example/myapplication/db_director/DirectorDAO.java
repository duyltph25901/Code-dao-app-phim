package com.example.myapplication.db_director;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Director;

import java.util.List;

@Dao
public interface DirectorDAO {
    @Insert
    void insert(Director director);

    @Query("SELECT * FROM director ORDER BY `FullName` ASC")
    List<Director> getAllDirectors();

    @Update
    void update(Director director);

    @Delete
    void delete(Director director);

    @Query("Delete from director")
    void deleteAll();

    @Query("SELECT * FROM director WHERE FullName LIKE :inputName")
    Director searchDirector(String inputName);
}
