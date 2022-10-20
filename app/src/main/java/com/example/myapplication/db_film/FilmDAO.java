package com.example.myapplication.db_film;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Film;

import java.util.List;

@Dao
public interface FilmDAO {
    @Insert
    void insert(Film film);

    @Query("SELECT * FROM film ORDER BY `Film Name` ASC ")
    List<Film> getFilms();

    @Update
    void update(Film film);

    @Delete
    void delete(Film film);

    @Query("SELECT * FROM film WHERE `Film Name` LIKE '%' || :nameInput || '%' ORDER BY `Film Name` ASC")
    List<Film> searchFilm(String nameInput);

    @Query("SELECT * FROM film WHERE `Category` LIKE '%' || :categoryInput || '%' ORDER BY `Film Name` ASC")
    List<Film> searchFilmByCategory(String categoryInput);

    @Query("SELECT * FROM film WHERE `Film Name` LIKE :nameInput ")
    Film searchFilmByName(String nameInput);

    @Query("DELETE FROM film")
    void deleteAllFilm();
}
