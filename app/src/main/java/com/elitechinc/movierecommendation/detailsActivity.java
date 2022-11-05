package com.elitechinc.movierecommendation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.elitechinc.movierecommendation.Adapters.HomeAdapter;
import com.elitechinc.movierecommendation.Adapters.RecommendAdapter;
import com.elitechinc.movierecommendation.Classes.Home;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class detailsActivity extends AppCompatActivity {
    String key;
    String desc;
    String name;
    String url;
    String img;
    String cat;
    TextView namedet, descdet, category;
    Button trailer;
    ImageView imgdetmov;
    FirebaseDatabase rootNode;
    FirebaseAuth mAuth;
    RecommendAdapter adapter;
    DatabaseReference mbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        key = getIntent().getStringExtra("key");
        desc = getIntent().getStringExtra("desc");
        name = getIntent().getStringExtra("name");
        url = getIntent().getStringExtra("url");
        img = getIntent().getStringExtra("img");
        cat = getIntent().getStringExtra("cat");
        imgdetmov = findViewById(R.id.imgdetmov);
        Picasso.get().load(img).into(imgdetmov);
        namedet = findViewById(R.id.movdetName);
        descdet = findViewById(R.id.descdetMov);
        category = findViewById(R.id.movdetCategory);
        category.setText(cat);
        trailer = findViewById(R.id.trailerMov);
        namedet.setText(name);
        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(detailsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.youtubeplayer);
                Button button = dialog.findViewById(R.id.close);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                YouTubePlayerView youTubePlayerView = dialog.findViewById(R.id.youtube_player_view);
                getLifecycle().addObserver(youTubePlayerView);
                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Movies").child(key);
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String vidID = snapshot.child("url").getValue(String.class);
                                String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";
                                Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
                                Matcher matcher = compiledPattern.matcher(vidID);
                                if (matcher.find()) {
                                    String vid = matcher.group(1);
                                    youTubePlayer.loadVideo(vid, 0);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        descdet.setText(desc);
        RecyclerView recyclerView = findViewById(R.id.recyclerRecom);
        rootNode = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mbase = FirebaseDatabase.getInstance().getReference("Movies");
        Query query = mbase.orderByChild("category").equalTo(cat);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        FirebaseRecyclerOptions<Home> options = new FirebaseRecyclerOptions.Builder<Home>().setQuery(query, Home.class).build();
        adapter = new RecommendAdapter(options);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onStart() {
        adapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        adapter.stopListening();
        super.onStop();
    }

}