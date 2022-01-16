package com.example.gymmanagementuser.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gymmanagementuser.KEYS;
import com.example.gymmanagementuser.R;
import com.example.gymmanagementuser.Tools;
import com.example.gymmanagementuser.databinding.ActivityBmicalculateBinding;

public class BMICalculateActivity extends AppCompatActivity {
    private static final String TAG = "ActivityBMICalculate";
    private ActivityBmicalculateBinding activityBmicalculateBinding;
    double weight = 0.0;
    double height = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBmicalculateBinding = ActivityBmicalculateBinding.inflate(getLayoutInflater());
        setContentView(activityBmicalculateBinding.getRoot());

        activityBmicalculateBinding.buttonBmiCalculateActSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userRegistrationValidation()) {
                    bmiCalculation(weight, height);
                } else {
                    Toast.makeText(BMICalculateActivity.this, "Please give your Weight and Height properly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //check all Validation
    public boolean userRegistrationValidation() {

        if (activityBmicalculateBinding.edittextBmiCalculateActWeight.getText().toString().trim().isEmpty()
                || activityBmicalculateBinding.edittextBmiCalculateActHeight.getText().toString().trim().isEmpty()) {
            Log.d(TAG, "userRegistrationValidation: false");
            return false;
        } else {
            weight = Double.parseDouble(activityBmicalculateBinding.edittextBmiCalculateActWeight.getText().toString().replaceAll("\\s+", ""));
            height = Double.parseDouble(activityBmicalculateBinding.edittextBmiCalculateActHeight.getText().toString().replaceAll("\\s+", ""));
            Log.d(TAG, "userRegistrationValidation: true");
            return true;
        }

    }

    private void bmiCalculation(double weight, double height) {
        double weight_kg = weight;
        double height_mtr = height / 3.281;
        Log.d(TAG, "bmiCalculation: " + weight_kg);
        Log.d(TAG, "bmiCalculation: " + height_mtr);

        float BMI = (float) (weight_kg / (height_mtr * height_mtr));
        Tools.savePrefFloat(KEYS.BMI, BMI);

        if (BMI < 18.5) {
            Tools.savePref(KEYS.BODY_TYPE, "Ectomorph");
        } else if (BMI >= 18.5 && BMI < 24.9) {
            Tools.savePref(KEYS.BODY_TYPE, "Mesomorph");
        } else {
            Tools.savePref(KEYS.BODY_TYPE, "Endomorph");
        }


        Toast.makeText(BMICalculateActivity.this, "Your BMI is: " + BMI, Toast.LENGTH_LONG).show();
        startActivity(new Intent(BMICalculateActivity.this, HomeActivity.class));
        finish();
    }
}