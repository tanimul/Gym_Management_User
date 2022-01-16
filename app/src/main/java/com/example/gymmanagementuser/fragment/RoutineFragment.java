package com.example.gymmanagementuser.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymmanagementuser.KEYS;
import com.example.gymmanagementuser.R;
import com.example.gymmanagementuser.Tools;
import com.example.gymmanagementuser.databinding.FragmentDietChartBinding;
import com.example.gymmanagementuser.databinding.FragmentRoutineBinding;
import com.example.gymmanagementuser.ui.ExerciseRoutineActivity;

public class RoutineFragment extends Fragment {
    private static final String TAG = "RoutineFragment";
    private FragmentRoutineBinding fragmentRoutineBinding;

    public RoutineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentRoutineBinding = FragmentRoutineBinding.inflate(inflater, container, false);
        View view = fragmentRoutineBinding.getRoot();

        fragmentRoutineBinding.textviewRoutineFragHeader.setText("Routine: " + Tools.getPref(KEYS.BODY_TYPE, null));
        Intent intent = new Intent(getActivity(), ExerciseRoutineActivity.class);

        fragmentRoutineBinding.buttonRoutineFragThreeDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.savePref(KEYS.TOTAL_EXERCISE_DAYS, "3 Days");
                startActivity(intent);
            }
        });

        fragmentRoutineBinding.buttonRoutineFragFiveDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.savePref(KEYS.TOTAL_EXERCISE_DAYS, "5 Days");
                startActivity(intent);
            }
        });

        fragmentRoutineBinding.buttonRoutineFragSixDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.savePref(KEYS.TOTAL_EXERCISE_DAYS, "6 Days");
                startActivity(intent);
            }
        });

        return view;
    }
}