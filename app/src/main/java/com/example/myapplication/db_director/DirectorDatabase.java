package com.example.myapplication.db_director;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.model.Director;

@Database(entities = {Director.class}, version = 1, exportSchema = false)
public abstract class DirectorDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "directorManagement.db";
    private static DirectorDatabase instance;

    public static synchronized DirectorDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), DirectorDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }

        return instance;
    }

    public abstract DirectorDAO directorDAO();
}
