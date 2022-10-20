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

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder>{
    private List<Film> films;
    private ClickItem clickItem;

    public FilmAdapter(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    public void setFilms(List<Film> films) {
        this.films = films;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilmViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_film, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        Film film = films.get(position);

        if (film == null) return;

        holder.bindHolder(film);
        holder.txtUpdate.setOnClickListener(v -> clickItem.update(film));
        holder.txtDelete.setOnClickListener(v -> clickItem.delete(film));
        holder.constraintLayout.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_one));
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imgFilm;
        TextView txtName, txtCreatedBy, txtStory;
        TextView txtUpdate, txtDelete;
        RatingBar ratingBar;
        ConstraintLayout constraintLayout;

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);

            init(itemView);
        }

        private void init(View itemView) {
            imgFilm = itemView.findViewById(R.id.imgFilmItem);
            txtName = itemView.findViewById(R.id.txtNameFilmItem);
            txtCreatedBy = itemView.findViewById(R.id.txtCreateByFilmItem);
            txtStory = itemView.findViewById(R.id.txtStoryFilmItemFilmItem);
            txtUpdate = itemView.findViewById(R.id.txtUpdateFilm);
            txtDelete = itemView.findViewById(R.id.txtDeleteFilm);
            ratingBar = itemView.findViewById(R.id.ratingBarFilmItemFilmItem);
            constraintLayout = itemView.findViewById(R.id.layoutFilmItem);
        }

        private void bindHolder(final Film film) {
            imgFilm.setImageBitmap(DataConvert.convertByArrToImage(film.getAvt()));
            txtName.setText(film.getName());
            txtCreatedBy.setText(film.getDirectors());
            txtStory.setText(film.getSummary());
            ratingBar.setRating((float) film.getPoint()/2);
        }
    }

    public interface ClickItem {
        void delete(Film film);
        void update(Film film);
    }
}
