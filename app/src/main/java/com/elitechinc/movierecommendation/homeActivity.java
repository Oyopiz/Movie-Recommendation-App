package com.elitechinc.movierecommendation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.elitechinc.movierecommendation.Fragments.BookmarkFragment;
import com.elitechinc.movierecommendation.Fragments.HomeFragment;
import com.elitechinc.movierecommendation.Fragments.Recommendations;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class homeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth mAuth;
    private long pressedTime;
    int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_home);
        loadFragment(new HomeFragment());
        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    private void askPermissions() {

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.homemenu:
                fragment = new HomeFragment();
                break;

            case R.id.recmenu:
                fragment = new Recommendations();
                break;

            case R.id.bookmenu:
                fragment = new BookmarkFragment();
                break;

        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
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
            case R.id.Admin:
                startActivity(new Intent(this, postActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void addLifeCycleCallBack(YouTubePlayerView youTubePlayerView) {
        getLifecycle().addObserver(youTubePlayerView);
    }
}

