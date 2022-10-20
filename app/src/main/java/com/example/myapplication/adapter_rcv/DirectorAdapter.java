package com.example.myapplication.adapter_rcv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.class_supported.DataConvert;
import com.example.myapplication.model.Actor;
import com.example.myapplication.model.Director;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class DirectorAdapter extends RecyclerView.Adapter<DirectorAdapter.DirectorViewHolder>{
    private List<Director> directors;
    private ClickItem clickItem;
    private Context context;

    public DirectorAdapter(Context contex, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DirectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DirectorViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_director, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DirectorViewHolder holder, int position) {
        Director director = directors.get(position);

        if (director == null) return;

        holder.img.setImageBitmap(DataConvert.convertByArrToImage(director.getImg()));
        holder.txtFullName.setText(director.getFullName());
        holder.txtDOB.setText(director.getDOB());
        holder.txtUpdate.setOnClickListener(v -> clickItem.update(director));
        holder.txtDelete.setOnClickListener(v -> clickItem.delete(director));
        holder.layoutItem.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_four));
    }

    @Override
    public int getItemCount() {
        return directors.size();
    }

    public class DirectorViewHolder extends RecyclerView.ViewHolder {
        private TextView txtFullName, txtDOB, txtUpdate, txtDelete;
        private RoundedImageView img;
        private ConstraintLayout layoutItem;

        public DirectorViewHolder(@NonNull View itemView) {
            super(itemView);

            txtFullName = itemView.findViewById(R.id.txtFullNameDirectorItem);
            txtDOB = itemView.findViewById(R.id.txtDOBDirectorItem);
            txtUpdate = itemView.findViewById(R.id.txtUpdateDirectorItem);
            txtDelete = itemView.findViewById(R.id.txtDeleteDirectorItem);
            img = itemView.findViewById(R.id.imgDirectorItem);
            layoutItem = itemView.findViewById(R.id.layoutDirectorItem);
        }
    }

    public interface ClickItem {
        void update(Director director);
        void delete(Director director);
    }
}
