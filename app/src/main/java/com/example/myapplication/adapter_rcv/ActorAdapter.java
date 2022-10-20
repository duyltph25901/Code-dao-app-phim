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
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ActorViewHolder>{
    private List<Actor> actors;
    private ClickItem clickItem;
    private Context context;

    public ActorAdapter(Context contex, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActorViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_actor, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        Actor actor = actors.get(position);

        if (actor == null) return;

        holder.img.setImageBitmap(DataConvert.convertByArrToImage(actor.getImg()));
        holder.txtFullName.setText(actor.getFullName());
        holder.txtDOB.setText(actor.getDOB());
        holder.txtUpdate.setOnClickListener(v -> clickItem.update(actor));
        holder.txtDelete.setOnClickListener(v -> clickItem.delete(actor));
        holder.layoutItem.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_four));
    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    public class ActorViewHolder extends RecyclerView.ViewHolder {
        private TextView txtFullName, txtDOB, txtUpdate, txtDelete;
        private RoundedImageView img;
        private ConstraintLayout layoutItem;

        public ActorViewHolder(@NonNull View itemView) {
            super(itemView);

            txtFullName = itemView.findViewById(R.id.txtFullNameActorItem);
            txtDOB = itemView.findViewById(R.id.txtDOBActorItem);
            txtUpdate = itemView.findViewById(R.id.txtUpdateActorItem);
            txtDelete = itemView.findViewById(R.id.txtDeleteActorItem);
            img = itemView.findViewById(R.id.imgActorItem);
            layoutItem = itemView.findViewById(R.id.layoutActorItem);
        }
    }

    public interface ClickItem {
        void update(Actor actor);
        void delete(Actor actor);
    }
}
