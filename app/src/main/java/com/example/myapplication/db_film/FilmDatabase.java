package com.example.myapplication.db_film;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.model.Film;

@Database(entities = {Film.class}, version = 1, exportSchema = false)
public abstract class FilmDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "filmManagement.db";
    private static FilmDatabase instance;

    public static synchronized FilmDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FilmDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }

        return instance;
    }

    public abstract FilmDAO filmDAO();
}
