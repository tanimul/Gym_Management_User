package com.example.gymmanagementuser.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymmanagementuser.KEYS;
import com.example.gymmanagementuser.Tools;
import com.example.gymmanagementuser.adapter.DietChartAdapter;
import com.example.gymmanagementuser.databinding.FragmentDietChartBinding;
import com.example.gymmanagementuser.model.DietChartInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DietChartFragment extends Fragment {
    private static final String TAG = "DietChartFragment";
    private FragmentDietChartBinding fragmentDietChartBinding;
    private DatabaseReference databaseReference;
    private DietChartAdapter dietChartAdapter;
    ArrayList<DietChartInfo> dietChartInfos;
    ProgressDialog Dialog;

    public DietChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDietChartBinding = FragmentDietChartBinding.inflate(inflater, container, false);
        View view = fragmentDietChartBinding.getRoot();

        fragmentDietChartBinding.textviewDietChartFragHeader.setText("Diet Chart: " + Tools.getPref(KEYS.BODY_TYPE, null));

        Dialog = new ProgressDialog(getActivity());

        databaseReference = FirebaseDatabase.getInstance().getReference("Diet Chart");
        getDietChartList();
        dietChartInfos = new ArrayList<>();
        dietChartAdapter = new DietChartAdapter(dietChartInfos);
        fragmentDietChartBinding.recyclerviewDietChartFragDietChart.setLayoutManager(new LinearLayoutManager(view.getContext()));
        fragmentDietChartBinding.recyclerviewDietChartFragDietChart.setAdapter(dietChartAdapter);



        return view;
    }

    private void getDietChartList() {
        Log.d(TAG, "getDietChartList: ");
        Dialog.setMessage("Please wait ...");
        Dialog.show();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dietChartInfos.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    DietChartInfo dietChartInfo = snapshot1.getValue(DietChartInfo.class);
                    Log.d(TAG, "DietInfo:" + snapshot1.getValue(DietChartInfo.class));
                    if (dietChartInfo.getBodyType().equals(Tools.getPref(KEYS.BODY_TYPE, null))) {
                        dietChartInfos.add(dietChartInfo);
                    }
                    Dialog.dismiss();
                }
                dietChartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
Dialog.dismiss();
            }
        });
    }
}
