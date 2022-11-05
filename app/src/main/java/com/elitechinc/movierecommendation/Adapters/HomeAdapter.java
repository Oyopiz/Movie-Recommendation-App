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

public class HomeAdapter extends FirebaseRecyclerAdapter<Home, HomeAdapter.homeViewholder> {
    public HomeAdapter(@NonNull FirebaseRecyclerOptions<Home> options) {
        super(options);
    }

    FirebaseAuth mAuth;

    @Override
    protected void onBindViewHolder(@NonNull HomeAdapter.homeViewholder holder, int position, @NonNull Home model) {
        mAuth = FirebaseAuth.getInstance();
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Preferences");
                Home home = new Home(model.getName(), model.getDesc(), mAuth.getCurrentUser().getUid(), model.getUrl(), model.getRatings(), model.getCategory(), model.getImgurl());
                databaseReference.push().setValue(home);
            }
        });
        String keybro = getRef(position).getKey();
        holder.category.setText(model.getCategory());
        Picasso.get().load(model.getImgurl()).into(holder.imgMov);
        holder.desc.setText(model.getDesc());
        holder.name.setText(model.getName());
        holder.rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        DatabaseReference ratingRef = FirebaseDatabase.getInstance().getReference("Movies").child(keybro);
        ratingRef.child("Rating").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                        float rating = Float.parseFloat(dataSnapshot.getValue().toString());
                        holder.simpleRatingBar.setRating(rating);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        holder.more.setOnClickListener(new View.OnClickListener() {
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
        holder.rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(holder.itemView.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog);
                RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
                Button button = dialog.findViewById(R.id.button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Movies").child(keybro);
                        String rateme = String.valueOf(ratingBar.getRating());
                        databaseReference.child("Rating").setValue(rateme);
                        dialog.dismiss();
                        // String rating=String.valueOf(ratingBar.getRating());
                        Toast.makeText(holder.itemView.getContext(), model.getName() + " " + "Rated", Toast.LENGTH_LONG).show();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    @NonNull
    @Override
    public HomeAdapter.homeViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homeui, parent, false);
        return new HomeAdapter.homeViewholder(view);
    }

    public class homeViewholder extends RecyclerView.ViewHolder {
        TextView desc, name, category;
        ImageView imgMov;
        Button bookmark, rate, more;
        RatingBar simpleRatingBar;

        public homeViewholder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.descMov);
            name = itemView.findViewById(R.id.movName);
            imgMov = itemView.findViewById(R.id.imgmov);
            category = itemView.findViewById(R.id.movCategory);
            bookmark = itemView.findViewById(R.id.bookmarkMov);
            simpleRatingBar = itemView.findViewById(R.id.simpleRatingBar);
            rate = itemView.findViewById(R.id.rateMov);
            more = itemView.findViewById(R.id.moreMOv);

        }
    }
}
