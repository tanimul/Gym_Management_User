package com.example.gymmanagementuser.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.gymmanagementuser.KEYS;
import com.example.gymmanagementuser.R;
import com.example.gymmanagementuser.Tools;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1500;
    private static final String TAG = "ActivitySplash";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Tools.getPrefBoolean(KEYS.IS_LOGGED_IN, false)) {
                    startActivity(new Intent(SplashActivity.this, BMICalculateActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}