package com.elitechinc.movierecommendation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.elitechinc.movierecommendation.Classes.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class postActivity extends AppCompatActivity {
    EditText movName, movDesc, trailerurl;
    ImageView imgpost;
    Uri selectedImage;
    FirebaseStorage storage;
    Spinner mainspinner;
    FirebaseAuth MaUTH;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        movName = findViewById(R.id.movName);
        movDesc = findViewById(R.id.movDesc);
        MaUTH = FirebaseAuth.getInstance();
        imgpost = findViewById(R.id.imgIMG);
        trailerurl = findViewById(R.id.YTurl);
        mainspinner = findViewById(R.id.mainSpinner);
        List<String> categories = new ArrayList<String>();
        categories.add("Action");
        categories.add("Horror");
        categories.add("Drama");
        categories.add("Thriller");
        categories.add("Sci-Fi");
        categories.add("Comedy");
        categories.add("Western");
        categories.add("Crime");
        categories.add("Romance");
        categories.add("Adventure");
        categories.add("Narrative");
        categories.add("Romance");
        categories.add("Animation");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainspinner.setAdapter(dataAdapter);
    }

    public void banner(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 45);
    }

    public void upload(View view) {
        storage = FirebaseStorage.getInstance();
        StorageReference reference1 = storage.getReference().child("images").child(UUID.randomUUID().toString());
        reference1.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String vId = null;

                            String pattern = "http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|(?:be-nocookie|be)\\.com\\/(?:watch|[\\w]+\\?(?:feature=[\\w]+.[\\w]+\\&)?v=|v\\/|e\\/|embed\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)";

                            Pattern compiledPattern = Pattern.compile(pattern);
                            Matcher matcher = compiledPattern.matcher(trailerurl.getText().toString());
                            if(matcher.find()){
                                vId= matcher.group();
                                String videoIdme = vId;
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Movies");
                                String category = mainspinner.getSelectedItem().toString();
                                Home home = new Home(movName.getText().toString(), movDesc.getText().toString(), MaUTH.getCurrentUser().getUid(), videoIdme, "", category, uri.toString());
                                databaseReference.push().setValue(home);
                                finish();
                            }
                        }
                    });
                }
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getData() != null) {
                imgpost.setImageURI(data.getData());
                selectedImage = data.getData();
            }
        }
    }
}