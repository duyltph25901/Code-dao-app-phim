package com.example.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "film")
public class Film implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long id;
    private byte[] avt, poster;
    private String linkTrailer, linkFilm;
    @ColumnInfo(name = "Film Name")
    private String name;
    @ColumnInfo(name = "Actors")
    private String actors;
    @ColumnInfo(name = "Directors")
    private String directors;
    private String premiereSchedule;
    @ColumnInfo(name = "Category")
    private String category;
    private String summary;
    @ColumnInfo(name = "Time")
    private int time;
    @ColumnInfo(name = "Limit Age")
    private int limitAge;
    @ColumnInfo(name = "Age")
    private double point;

    public Film(byte[] avt, byte[] poster, String linkTrailer,
                String linkFilm, String name, String actors, String directors,
                String premiereSchedule, String category, String summary, int time,
                int limitAge, double point) {
        this.avt = avt;
        this.poster = poster;
        this.linkTrailer = linkTrailer;
        this.linkFilm = linkFilm;
        this.name = name;
        this.actors = actors;
        this.directors = directors;
        this.premiereSchedule = premiereSchedule;
        this.category = category;
        this.summary = summary;
        this.time = time;
        this.limitAge = limitAge;
        this.point = point;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getAvt() {
        return avt;
    }

    public void setAvt(byte[] avt) {
        this.avt = avt;
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    public String getLinkTrailer() {
        return linkTrailer;
    }

    public void setLinkTrailer(String linkTrailer) {
        this.linkTrailer = linkTrailer;
    }

    public String getLinkFilm() {
        return linkFilm;
    }

    public void setLinkFilm(String linkFilm) {
        this.linkFilm = linkFilm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getPremiereSchedule() {
        return premiereSchedule;
    }

    public void setPremiereSchedule(String premiereSchedule) {
        this.premiereSchedule = premiereSchedule;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLimitAge() {
        return limitAge;
    }

    public void setLimitAge(int limitAge) {
        this.limitAge = limitAge;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }
}
