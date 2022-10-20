package com.example.myapplication.adapter_rcv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.class_supported.DataConvert;
import com.example.myapplication.model.Film;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class RecommendFilmAdapter extends RecyclerView.Adapter<RecommendFilmAdapter.ActionFilmViewHolder>{
    private List<Film> films;
    private ClickItem clickItem;

    public RecommendFilmAdapter(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    public void setFilms(List<Film> films) {
        this.films = films;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ActionFilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActionFilmViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_film_recommend, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ActionFilmViewHolder holder, int position) {
        Film film = films.get(position);

        if (film == null) return;

        holder.bindHolder(film);
        holder.layoutItem.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_four));
        holder.layoutItem.setOnClickListener(v -> clickItem.clickItem(film));
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class ActionFilmViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imgFilm;
        TextView txtFilmName, txtYear, txtCategory;
        RatingBar ratingBar;
        ConstraintLayout layoutItem;

        public ActionFilmViewHolder(@NonNull View itemView) {
            super(itemView);

            init(itemView);
        }

        private void init(View itemView) {
            imgFilm = itemView.findViewById(R.id.imgFilm);
            txtFilmName = itemView.findViewById(R.id.txtFilmName);
            txtYear = itemView.findViewById(R.id.txtYearFilm);
            txtCategory = itemView.findViewById(R.id.txtCategoryFilm);
            ratingBar = itemView.findViewById(R.id.ratingBarFilm);
            layoutItem = itemView.findViewById(R.id.layoutFilmCategory);
        }

        private void bindHolder(final Film film) {
            imgFilm.setImageBitmap(DataConvert.convertByArrToImage(film.getAvt()));
            txtFilmName.setText(film.getName());
            ratingBar.setRating((float) film.getPoint()/2);
            String category = film.getCategory().replaceAll("[-+.^:,]","");
            txtCategory.setText(category);
            String date = film.getPremiereSchedule();
            String[] arr = date.split("/");
            String year = arr[2].trim();
            txtYear.setText(year);
        }
    }

    public interface ClickItem {
        void clickItem(Film film);
    }
}
