package com.example.gymmanagementuser.viewmode;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymmanagementuser.repository.RepositoryExercise;
import com.example.gymmanagementuser.model.ExerciseInfo;

import java.util.ArrayList;

public class ViewHolderExercise extends ViewModel {

    private static final String TAG = "ViewHolderExercise";
    MutableLiveData<ArrayList<ExerciseInfo>> exerciseStatus;
    public void getData(Context context) {

        if (exerciseStatus != null) {
            return;
        }
        exerciseStatus = RepositoryExercise.getInstance(context).getStatus();
        Log.d(TAG, "getData: "+exerciseStatus.getValue().size());
    }

    public LiveData<ArrayList<ExerciseInfo>> getstatus() {
        return exerciseStatus;
    }
}
