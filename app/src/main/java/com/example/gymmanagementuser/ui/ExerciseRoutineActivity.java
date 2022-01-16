package com.example.gymmanagementuser.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gymmanagementuser.KEYS;
import com.example.gymmanagementuser.Tools;
import com.example.gymmanagementuser.viewmode.ViewHolderExercise;
import com.example.gymmanagementuser.adapter.ExerciseAdapter;
import com.example.gymmanagementuser.databinding.ActivityExerciseRoutineBinding;
import com.example.gymmanagementuser.interfaces.DataLoadListener;
import com.example.gymmanagementuser.model.ExerciseInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public class ExerciseRoutineActivity extends AppCompatActivity implements DataLoadListener {
    private static final String TAG = "ExerciseRoutineDetails";
    private ActivityExerciseRoutineBinding activityExerciseRoutineBinding;
    private ExerciseAdapter exerciseAdapter;
    //private ViewHolderExercise viewHolderExercise;
    DatabaseReference databaseReference;
    ArrayList<ExerciseInfo> exerciseInfos;
    ProgressDialog Dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityExerciseRoutineBinding = ActivityExerciseRoutineBinding.inflate(getLayoutInflater());
        setContentView(activityExerciseRoutineBinding.getRoot());

        String days = Tools.getPref(KEYS.TOTAL_EXERCISE_DAYS, null);
        activityExerciseRoutineBinding.textviewExerciseRoutineActHeader.setText("Exercise: " + days);

        databaseReference = FirebaseDatabase.getInstance().getReference("Exercises");

        Dialog = new ProgressDialog(ExerciseRoutineActivity.this);

        if (days.equals("3 Days")) {
            activityExerciseRoutineBinding.dayPickerExerciseRoutineActDayPicker.setDayEnabled(MaterialDayPicker.Weekday.SUNDAY, false);
            activityExerciseRoutineBinding.dayPickerExerciseRoutineActDayPicker.setDayEnabled(MaterialDayPicker.Weekday.TUESDAY, false);
            activityExerciseRoutineBinding.dayPickerExerciseRoutineActDayPicker.setDayEnabled(MaterialDayPicker.Weekday.THURSDAY, false);
            activityExerciseRoutineBinding.dayPickerExerciseRoutineActDayPicker.setDayEnabled(MaterialDayPicker.Weekday.FRIDAY, false);
        } else if (days.equals("5 Days")) {
            activityExerciseRoutineBinding.dayPickerExerciseRoutineActDayPicker.setDayEnabled(MaterialDayPicker.Weekday.THURSDAY, false);
            activityExerciseRoutineBinding.dayPickerExerciseRoutineActDayPicker.setDayEnabled(MaterialDayPicker.Weekday.FRIDAY, false);
        } else {
            activityExerciseRoutineBinding.dayPickerExerciseRoutineActDayPicker.setDayEnabled(MaterialDayPicker.Weekday.FRIDAY, false);
        }
        init();

        activityExerciseRoutineBinding.dayPickerExerciseRoutineActDayPicker.setDaySelectionChangedListener(new MaterialDayPicker.DaySelectionChangedListener() {
            @Override
            public void onDaySelectionChanged(List<MaterialDayPicker.Weekday> selectDays) {
                Toast.makeText(ExerciseRoutineActivity.this, "" + selectDays, Toast.LENGTH_SHORT).show();
                exerciseAdapter.getFilter().filter(selectDays.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
            }
        });
    }

    private void init() {
//        viewHolderExercise = new ViewModelProvider(this).get(ViewHolderExercise.class);
//        viewHolderExercise.getData(ExerciseRoutineActivity.this);
//        exerciseAdapter = new ExerciseAdapter(viewHolderExercise.getstatus().getValue());
//        activityExerciseRoutineBinding.recyclerviewExerciseRoutineActExerciseList.setLayoutManager(new LinearLayoutManager(this));
//        activityExerciseRoutineBinding.recyclerviewExerciseRoutineActExerciseList.setAdapter(exerciseAdapter);
//        Log.d(TAG, "Status List:" + viewHolderExercise.getstatus().getValue().size());

        exerciseInfos = new ArrayList<>();
        exerciseAdapter = new ExerciseAdapter(exerciseInfos, exerciseInfos);
        activityExerciseRoutineBinding.recyclerviewExerciseRoutineActExerciseList.setLayoutManager(new LinearLayoutManager(this));
        activityExerciseRoutineBinding.recyclerviewExerciseRoutineActExerciseList.setAdapter(exerciseAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        getRoutineList();
    }

    @Override
    public void onDataLoaded() {
//        viewHolderExercise.getstatus().observe(this, new Observer<ArrayList<ExerciseInfo>>() {
//            @Override
//            public void onChanged(ArrayList<ExerciseInfo> exerciseInfos) {
//                Log.d(TAG, "onChanged: " + exerciseInfos.size());
//                exerciseAdapter.notifyDataSetChanged();
//            }
//        });
    }

    private void getRoutineList() {
        Dialog.setMessage("Please wait ...");
        Dialog.show();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exerciseInfos.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ExerciseInfo exerciseInfo = snapshot1.getValue(ExerciseInfo.class);
                    Log.d(TAG, "LoanInfo:" + exerciseInfo.getTotalDaysOfWeek());
                    if (Tools.getPref(KEYS.TOTAL_EXERCISE_DAYS, null).equals(exerciseInfo.getTotalDaysOfWeek())
                            && Tools.getPref(KEYS.BODY_TYPE, null).equals(exerciseInfo.getBodyType())) {
                        exerciseInfos.add(exerciseInfo);
                    }

                }
                exerciseAdapter.notifyDataSetChanged();
                Dialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Dialog.cancel();
            }
        });
    }
}