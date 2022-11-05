package com.elitechinc.movierecommendation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class preferenceActivity extends AppCompatActivity {
    ToggleButton action, horror, drama, thriller, scifi, comedy, romance, animation;
    FirebaseAuth mAuth;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        mAuth = FirebaseAuth.getInstance();
        action = (ToggleButton) findViewById(R.id.action);
        horror = (ToggleButton) findViewById(R.id.horror);
        drama = (ToggleButton) findViewById(R.id.drama);
        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(preferenceActivity.this, homeActivity.class));
            }
        });
        thriller = (ToggleButton) findViewById(R.id.thriller);
        scifi = (ToggleButton) findViewById(R.id.scifi);
        comedy = (ToggleButton) findViewById(R.id.comedy);
        romance = (ToggleButton) findViewById(R.id.romance);
        animation = (ToggleButton) findViewById(R.id.animation);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Preferences");
        //databaseReference
        action.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("mov1").setValue("Action");
                    Toast.makeText(preferenceActivity.this, "Picked Action", Toast.LENGTH_SHORT).show();
                    // The toggle is enabled
                } else {
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("mov1").removeValue();
                    Toast.makeText(preferenceActivity.this, "Removed Action", Toast.LENGTH_SHORT).show();
                    // The toggle is disabled
                }
            }
        });
        horror.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("mov2").setValue("Horror");
                    Toast.makeText(preferenceActivity.this, "Picked Horror", Toast.LENGTH_SHORT).show();
                    // The toggle is enabled
                } else {
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("mov2").removeValue();
                    Toast.makeText(preferenceActivity.this, "Removed Horror", Toast.LENGTH_SHORT).show();
                    // The toggle is disabled
                }
            }
        });
        drama.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("mov3").setValue("Drama");
                    Toast.makeText(preferenceActivity.this, "Picked Drama", Toast.LENGTH_SHORT).show();
                    // The toggle is enabled
                } else {
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("mov3").removeValue();
                    Toast.makeText(preferenceActivity.this, "Removed Drama", Toast.LENGTH_SHORT).show();
                    // The toggle is disabled
                }
            }
        });
        thriller.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("mov4").setValue("Thriller");
                    Toast.makeText(preferenceActivity.this, "Picked Thriller", Toast.LENGTH_SHORT).show();
                    // The toggle is enabled
                } else {
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("mov4").removeValue();
                    Toast.makeText(preferenceActivity.this, "Removed Thriller", Toast.LENGTH_SHORT).show();
                    // The toggle is disabled
                }
            }
        });
        scifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("mov5").setValue("Sci-Fi");
                    Toast.makeText(preferenceActivity.this, "Picked Sci-Fi", Toast.LENGTH_SHORT).show();
                    // The toggle is enabled
                } else {
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("mov1").setValue("Sci-Fi");
                    Toast.makeText(preferenceActivity.this, "Removed Sci-Fi", Toast.LENGTH_SHORT).show();
                    // The toggle is disabled
                }
            }
        });
        comedy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("mov6").setValue("Comedy");
                    Toast.makeText(preferenceActivity.this, "Picked Comedy", Toast.LENGTH_SHORT).show();
                    // The toggle is enabled
                } else {
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("mov6").removeValue();
                    Toast.makeText(preferenceActivity.this, "Removed Comedy", Toast.LENGTH_SHORT).show();
                    // The toggle is disabled
                }
            }
        });
        romance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("mov7").setValue("Romance");
                    Toast.makeText(preferenceActivity.this, "Picked Romance", Toast.LENGTH_SHORT).show();
                    // The toggle is enabled
                } else {
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("mov7").removeValue();
                    Toast.makeText(preferenceActivity.this, "Removed Animation", Toast.LENGTH_SHORT).show();
                    ;
                    // The toggle is disabled
                }
            }
        });
        animation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("mov8").setValue("Animation");
                    Toast.makeText(preferenceActivity.this, "Picked Animation", Toast.LENGTH_SHORT).show();
                    // The toggle is enabled
                } else {
                    databaseReference.child(mAuth.getCurrentUser().getUid()).child("mov8").removeValue();
                    Toast.makeText(preferenceActivity.this, "Removed Animation", Toast.LENGTH_SHORT).show();
                    // The toggle is disabled
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                mAuth.signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}