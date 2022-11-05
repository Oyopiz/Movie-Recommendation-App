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
import com.elitechinc.movierecommendation.Classes.Reviews;
import com.elitechinc.movierecommendation.R;
import com.elitechinc.movierecommendation.reviewsActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ReviewAdapter extends FirebaseRecyclerAdapter<Reviews, ReviewAdapter.reviewViewholder> {
    public ReviewAdapter(@NonNull FirebaseRecyclerOptions<Reviews> options) {
        super(options);
    }

    FirebaseAuth mAuth;

    @Override
    protected void onBindViewHolder(@NonNull ReviewAdapter.reviewViewholder holder, int position, @NonNull Reviews model) {
        mAuth = FirebaseAuth.getInstance();
        String keybro = getRef(position).getKey();
        holder.reviewbruh.setText(model.getReviews());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(keybro);
                databaseReference.removeValue();
                return false;
            }
        });

    }

    @NonNull
    @Override
    public ReviewAdapter.reviewViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews, parent, false);
        return new ReviewAdapter.reviewViewholder(view);
    }

    public class reviewViewholder extends RecyclerView.ViewHolder {
        TextView reviewbruh;

        public reviewViewholder(@NonNull View itemView) {
            super(itemView);
            reviewbruh = itemView.findViewById(R.id.reviewbruh);

        }
    }
}
