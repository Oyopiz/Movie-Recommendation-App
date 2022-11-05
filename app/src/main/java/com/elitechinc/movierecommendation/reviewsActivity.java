package com.elitechinc.movierecommendation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.elitechinc.movierecommendation.Adapters.HomeAdapter;
import com.elitechinc.movierecommendation.Adapters.RecommendAdapter;
import com.elitechinc.movierecommendation.Adapters.ReviewAdapter;
import com.elitechinc.movierecommendation.Classes.Home;
import com.elitechinc.movierecommendation.Classes.Reviews;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class reviewsActivity extends AppCompatActivity {
    String key;
    String reviews;
    TextView namedet, descdet, category;
    Button trailer;
    ImageView imgdetmov;
    FirebaseDatabase rootNode;
    FirebaseAuth mAuth;
    ReviewAdapter adapter;
    DatabaseReference mbase;
    FloatingActionButton floatReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        floatReview = findViewById(R.id.floatReview);
        key = getIntent().getStringExtra("keyme");
        reviews = getIntent().getStringExtra("reviews");
        RecyclerView recyclerView = findViewById(R.id.recyclerReview);
        rootNode = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mbase = FirebaseDatabase.getInstance().getReference("Preferences").child(key).child("Reviews");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Reviews> options = new FirebaseRecyclerOptions.Builder<Reviews>().setQuery(mbase, Reviews.class).build();
        adapter = new ReviewAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        floatReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(reviewsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.review);
                Button button = dialog.findViewById(R.id.done);
                EditText editText = dialog.findViewById(R.id.editReview);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Preferences").child(key).child("Reviews");
                        Reviews reviews = new Reviews(editText.getText().toString(), mAuth.getCurrentUser().getUid());
                        databaseReference.push().setValue(reviews);
                        Toast.makeText(reviewsActivity.this, "Review added", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        // String rating=String.valueOf(ratingBar.getRating());
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

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