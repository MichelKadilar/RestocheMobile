package com.restoche;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import models.Resto;

public class RestoAdapter extends RecyclerView.Adapter<RestoAdapter.RestoHolder> {
    private Context context;
    private List<Resto> restoList;

    public RestoAdapter(Context context, List<Resto> restoList) {
        this.context = context;
        this.restoList = restoList;
    }

    @NonNull
    @Override
    public RestoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card,parent,false);
        return new RestoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestoHolder holder, int position) {
        Resto resto=restoList.get(position);
        holder.titre.setText(resto.getTitle());
        holder.localisation.setText(resto.getLocalisation());
        holder.ratingBar.setRating(resto.getRatings());

        String imageUrl = resto.getImage();
        Picasso.get().load(imageUrl).into(holder.imageView);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , ActivityRestaurantProfile.class);
                intent.putExtra("resto", resto);
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return restoList.size();
    }
    public class RestoHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titre,localisation;
        RatingBar ratingBar;
        ConstraintLayout constraintLayout;

        public RestoHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_carte);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            titre=itemView.findViewById(R.id.titre);
            localisation=itemView.findViewById(R.id.localisation);
            constraintLayout=itemView.findViewById(R.id.detail_constraint);

        }
    }
}
