package com.elitechinc.movierecommendation.Adapters;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elitechinc.movierecommendation.Classes.Home;
import com.elitechinc.movierecommendation.R;
import com.elitechinc.movierecommendation.detailsActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class RecommendAdapter extends FirebaseRecyclerAdapter<Home, RecommendAdapter.recomViewholder> {
    public RecommendAdapter(@NonNull FirebaseRecyclerOptions<Home> options) {
        super(options);
    }

    FirebaseAuth mAuth;

    @Override
    protected void onBindViewHolder(@NonNull RecommendAdapter.recomViewholder holder, int position, @NonNull Home model) {
        mAuth = FirebaseAuth.getInstance();
        String keybro = getRef(position).getKey();
        Picasso.get().load(model.getImgurl()).into(holder.imgMov);
        holder.name.setText(model.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), detailsActivity.class);
                intent.putExtra("key", keybro);
                intent.putExtra("desc", model.getDesc());
                intent.putExtra("url", model.getUrl());
                intent.putExtra("name", model.getName());
                intent.putExtra("img", model.getImgurl());
                intent.putExtra("cat", model.getCategory());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public RecommendAdapter.recomViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend, parent, false);
        return new RecommendAdapter.recomViewholder(view);
    }

    public class recomViewholder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imgMov;

        public recomViewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.movRecom);
            imgMov = itemView.findViewById(R.id.imgRecom);

        }
    }
}
