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
import com.elitechinc.movierecommendation.MainActivity;
import com.elitechinc.movierecommendation.R;
import com.elitechinc.movierecommendation.detailsActivity;
import com.elitechinc.movierecommendation.homeActivity;
import com.elitechinc.movierecommendation.reviewsActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookmarkAdapter extends FirebaseRecyclerAdapter<Home, BookmarkAdapter.bookmarkViewholder> {
    public BookmarkAdapter(@NonNull FirebaseRecyclerOptions<Home> options) {
        super(options);
    }

    FirebaseAuth mAuth;

    @Override
    protected void onBindViewHolder(@NonNull BookmarkAdapter.bookmarkViewholder holder, int position, @NonNull Home model) {
        mAuth = FirebaseAuth.getInstance();
        String keybro = getRef(position).getKey();
        holder.rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), reviewsActivity.class);
                intent.putExtra("keyme", keybro);
                intent.putExtra("desc", model.getDesc());
                intent.putExtra("url", model.getUrl());
                intent.putExtra("name", model.getName());
                intent.putExtra("img", model.getImgurl());
                intent.putExtra("cat", model.getCategory());
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Preferences").child(keybro);
                databaseReference.removeValue();
            }
        });
        Picasso.get().load(model.getImgurl()).into(holder.imgMov);
        holder.desc.setText(model.getDesc());
        holder.name.setText(model.getName());
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
    public BookmarkAdapter.bookmarkViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmarks, parent, false);
        return new BookmarkAdapter.bookmarkViewholder(view);
    }

    public class bookmarkViewholder extends RecyclerView.ViewHolder {
        TextView desc, name;
        ImageView imgMov, imgDelete;
        Button rate, review;

        public bookmarkViewholder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.descMov);
            name = itemView.findViewById(R.id.movName);
            imgMov = itemView.findViewById(R.id.imgmov);
            rate = itemView.findViewById(R.id.bookRate);
            review = itemView.findViewById(R.id.review);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
