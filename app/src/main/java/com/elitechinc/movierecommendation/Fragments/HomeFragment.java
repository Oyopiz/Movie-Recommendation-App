package com.elitechinc.movierecommendation.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elitechinc.movierecommendation.Adapters.HomeAdapter;
import com.elitechinc.movierecommendation.Classes.Home;
import com.elitechinc.movierecommendation.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {
    FirebaseDatabase rootNode;
    FirebaseAuth mAuth;
    HomeAdapter adapter;
    DatabaseReference mbase, tenrec;
    RadioGroup radioGroup;
    ProgressBar loading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerhome);
        loading = view.findViewById(R.id.load);
        rootNode = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mbase = FirebaseDatabase.getInstance().getReference("Movies");
        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loading.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Home> options = new FirebaseRecyclerOptions.Builder<Home>().setQuery(mbase, Home.class).build();
        adapter = new HomeAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;

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
