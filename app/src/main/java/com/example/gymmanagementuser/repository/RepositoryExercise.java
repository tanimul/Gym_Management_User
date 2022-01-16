package com.example.gymmanagementuser.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.gymmanagementuser.KEYS;
import com.example.gymmanagementuser.Tools;
import com.example.gymmanagementuser.interfaces.DataLoadListener;
import com.example.gymmanagementuser.model.ExerciseInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.ArrayList;

public class RepositoryExercise {
    private static final String TAG = "RepositoryExercise";
    static RepositoryExercise instance;
    public ArrayList<ExerciseInfo> exerciseInfos = new ArrayList<>();
    static DataLoadListener dataLoadListener;

    public static RepositoryExercise getInstance(Context context) {
        Log.d(TAG, "getInstance: ");
        if (instance == null) {
            instance = new RepositoryExercise();
        }
        dataLoadListener = (DataLoadListener) context;
        return instance;
    }

    public MutableLiveData<ArrayList<ExerciseInfo>> getStatus() {
        Log.d(TAG, "getStatus: ");
        loadStatus();
        MutableLiveData<ArrayList<ExerciseInfo>> status = new MutableLiveData<>();
        status.setValue(exerciseInfos);
        return status;
    }

    private void loadStatus() {
        Log.d(TAG, "loadStatus: ");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Exercises");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exerciseInfos.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ExerciseInfo exerciseInfo = snapshot1.getValue(ExerciseInfo.class);
                    Log.d(TAG, "LoanInfo:" + exerciseInfo.getTotalDaysOfWeek());
                    if (Tools.getPref(KEYS.TOTAL_EXERCISE_DAYS, null).equals(exerciseInfo.getTotalDaysOfWeek())) {
                        exerciseInfos.add(exerciseInfo);
                    }

                }
                dataLoadListener.onDataLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
