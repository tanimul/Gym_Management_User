package com.example.gymmanagementuser.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.example.gymmanagementuser.KEYS;
import com.example.gymmanagementuser.Tools;
import com.example.gymmanagementuser.databinding.ActivitySettingBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {
    private static final String TAG = "ActivitySetting";
    private ActivitySettingBinding activitySettingBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingBinding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(activitySettingBinding.getRoot());

        //   checkDayNightMode();

        activitySettingBinding.imageButtonSettingActBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        activitySettingBinding.buttonSettingActLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Tools.savePrefBoolean(KEYS.IS_LOGGED_IN, false);
                onBackPressed();
            }
        });

//        activitySettingBinding.switchSettingActDartMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked) {
//                    Log.d(TAG, "Checked");
//                    Tools.savePrefBoolean(KEYS.IsDartMode, true);
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                } else {
//                    Log.d(TAG, "Unchecked");
//                    Tools.savePrefBoolean(KEYS.IsDartMode, false);
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                }
//            }
//        });
    }

//    private void checkDayNightMode() {
//        if (Tools.getPrefBoolean(KEYS.IsDartMode, false)) {
//            activitySettingBinding.switchSettingActDartMode.setChecked(true);
//        } else {
//            activitySettingBinding.switchSettingActDartMode.setChecked(false);
//        }
//    }
}