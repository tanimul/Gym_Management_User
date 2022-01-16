package com.example.gymmanagementuser.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;

import com.example.gymmanagementuser.KEYS;
import com.example.gymmanagementuser.R;
import com.example.gymmanagementuser.Tools;
import com.example.gymmanagementuser.databinding.ActivityExerciseDetailsBinding;
import com.example.gymmanagementuser.model.ExerciseInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.squareup.picasso.Picasso;

public class ExerciseDetailsActivity extends AppCompatActivity {
    private static final String TAG = "ActivityExerciseDetails";
    private ActivityExerciseDetailsBinding activityExerciseDetailsBinding;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityExerciseDetailsBinding = ActivityExerciseDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityExerciseDetailsBinding.getRoot());
        databaseReference = FirebaseDatabase.getInstance().getReference("Exercises");
        String exerciseId = getIntent().getStringExtra("exerciseId");

        getExerciseDetails(exerciseId);

    }

    private void getExerciseDetails(String exerciseId) {

        databaseReference.child(exerciseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ExerciseInfo exerciseInfo = snapshot.getValue(ExerciseInfo.class);
                placeExerciseDetails(exerciseInfo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void placeExerciseDetails(ExerciseInfo exerciseInfo) {

        activityExerciseDetailsBinding.textviewExerciseDetailsExerciseName.setText("Exercise Name: " + exerciseInfo.getExerciseName());
        activityExerciseDetailsBinding.textviewExerciseDetailsExerciseDetails.setText("" + exerciseInfo.getExerciseDetails());
        Picasso.get().load(exerciseInfo.getImageLink1()).into(activityExerciseDetailsBinding.imageviewExerciseDetailsImage01);
        Picasso.get().load(exerciseInfo.getImageLink2()).into(activityExerciseDetailsBinding.imageviewExerciseDetailsImage02);
        Picasso.get().load(exerciseInfo.getImageLink3()).into(activityExerciseDetailsBinding.imageviewExerciseDetailsImage03);
        activityExerciseDetailsBinding.textviewExerciseDetailsDay.setText("" + exerciseInfo.getDaysOfWeek());
        activityExerciseDetailsBinding.textviewExerciseDetailsSet.setText("" + exerciseInfo.getSet());
        activityExerciseDetailsBinding.textviewExerciseDetailsReps.setText("" + exerciseInfo.getReps());

        Log.d(TAG, "placeExerciseDetails: ");
        getLifecycle().addObserver(activityExerciseDetailsBinding.videoViewExerciseDetailsExerciseVideoLink);

        activityExerciseDetailsBinding.videoViewExerciseDetailsExerciseVideoLink.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                String videoId = "" + exerciseInfo.getVideoLink();
                youTubePlayer.loadVideo(videoId, 0);
                Log.d(TAG, "onReady: ");
                youTubePlayer.play();
            }
        });
    }
}