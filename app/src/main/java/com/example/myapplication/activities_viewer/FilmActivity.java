package com.example.myapplication.activities_viewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.class_supported.DataConvert;
import com.example.myapplication.db_film.FilmDatabase;
import com.example.myapplication.model.Film;

public class FilmActivity extends AppCompatActivity {
    private TextView txtFilmName, txtCategory, txtLimitAge, txtPoint, txtSummary;
    private ImageView imgLike, imgPoster;
    private ImageButton btnBack;
    private Button btnTrailer, btnFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        savedInstanceState = this.getIntent().getExtras();
        String key = (String) savedInstanceState.get("filmObj");

        Film film = FilmDatabase.getInstance(FilmActivity.this).filmDAO().searchFilmByName(key);

        if (film == null) return;

        init();
        setData(film);
        setEventForUI(film);
    }

    private void init() {
        txtFilmName = findViewById(R.id.txtFilmNameWatch);
        txtCategory = findViewById(R.id.txtCategory);
        txtLimitAge = findViewById(R.id.txtAge);
        txtPoint = findViewById(R.id.txtPoint);
        txtSummary = findViewById(R.id.txtSummary);
        imgLike = findViewById(R.id.imgLikeFilm);
        imgPoster = findViewById(R.id.posterFilm);
        btnBack = findViewById(R.id.btnBackFilm);
        btnTrailer = findViewById(R.id.btnWatchTrailer);
        btnFilm = findViewById(R.id.btnWatchFilm);
    }

    private void setData(final Film film) {
        txtFilmName.setText(film.getName());
        String category = film.getCategory().replaceAll("[-+.^:,]","");
        txtCategory.setText(category.trim());
        txtLimitAge.setText(film.getLimitAge()+"");
        txtPoint.setText(film.getPoint()/2+"");
        txtSummary.setText(film.getSummary());
        imgPoster.setImageBitmap(DataConvert.convertByArrToImage(film.getAvt()));
    }

    private void setEventForUI(Film film) {
        imgLike.setOnClickListener(v -> like());
        btnBack.setOnClickListener(v -> back());
        btnTrailer.setOnClickListener(v -> watchTrailer(film));
        btnFilm.setOnClickListener(v -> watchFilm(film));
    }

    private void like() {}

    private void back() {
        onBackPressed();
    }

    private void watchTrailer(Film film) {
        String link = film.getLinkTrailer().trim();
        Bundle bundle = new Bundle();
        bundle.putString("linkTrailer", link);
        Intent intent = new Intent(getBaseContext(), WatchActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void watchFilm(final Film film) {
        String link = film.getLinkFilm().trim();
        Bundle bundle = new Bundle();
        bundle.putString("linkTrailer", link);
        Intent intent = new Intent(getBaseContext(), WatchActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}