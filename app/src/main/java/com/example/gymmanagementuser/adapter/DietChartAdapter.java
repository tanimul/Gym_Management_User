package com.example.gymmanagementuser.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymmanagementuser.R;
import com.example.gymmanagementuser.model.DietChartInfo;

import java.util.ArrayList;

public class DietChartAdapter extends RecyclerView.Adapter<DietChartAdapter.DietChartViewHolder> {
    private static final String TAG = "DietChartAdapter";
    private ArrayList<DietChartInfo> dietChartInfos;

    public DietChartAdapter(ArrayList<DietChartInfo> dietChartInfos) {
        this.dietChartInfos = dietChartInfos;
    }

    @NonNull
    @Override
    public DietChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_diet_chart, parent, false);
        return new DietChartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DietChartViewHolder holder, int position) {
        DietChartInfo dietChartInfo = dietChartInfos.get(position);
        Log.d(TAG, "onBindViewHolder: ");
        holder.dietChartName.setText(dietChartInfo.getDietChartName());
        holder.dietChartTime.setText(dietChartInfo.getDietChartTime());
        holder.bodyTypes.setText(dietChartInfo.getBodyType());
    }

    @Override
    public int getItemCount() {
        return dietChartInfos.size();
    }


    public class DietChartViewHolder extends RecyclerView.ViewHolder {
        private TextView dietChartName, dietChartTime, bodyTypes;

        public DietChartViewHolder(@NonNull View itemView) {
            super(itemView);
            dietChartName = itemView.findViewById(R.id.textview_dietChartLayout_dietChart);
            dietChartTime = itemView.findViewById(R.id.textview_dietChartLayout_time);
            bodyTypes = itemView.findViewById(R.id.textview_exerciseLayout_bodyTypes);
        }
    }
}
