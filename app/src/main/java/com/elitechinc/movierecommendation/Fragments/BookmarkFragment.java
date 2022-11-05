package com.elitechinc.movierecommendation.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elitechinc.movierecommendation.Adapters.BookmarkAdapter;
import com.elitechinc.movierecommendation.Adapters.HomeAdapter;
import com.elitechinc.movierecommendation.Classes.Home;
import com.elitechinc.movierecommendation.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class BookmarkFragment extends Fragment{
    FirebaseDatabase rootNode;
    FirebaseAuth mAuth;
    BookmarkAdapter adapter;
    DatabaseReference mbase, tenrec;
    RadioGroup radioGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookmark_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerbook);
        rootNode = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mbase = FirebaseDatabase.getInstance().getReference("Preferences");
        Query query = mbase.orderByChild("uid").equalTo(mAuth.getCurrentUser().getUid());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Home> options = new FirebaseRecyclerOptions.Builder<Home>().setQuery(query, Home.class).build();
        adapter = new BookmarkAdapter(options);
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