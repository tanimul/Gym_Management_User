package com.example.gymmanagementuser.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.gymmanagementuser.KEYS;
import com.example.gymmanagementuser.R;
import com.example.gymmanagementuser.Tools;
import com.example.gymmanagementuser.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "ActivityLogin";
    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());


        activityLoginBinding.buttonLoginActLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityLoginBinding.edittextLoginActMobileNumber.length() != 11 || activityLoginBinding.edittextLoginActMobileNumber.equals("")) {
                    Toast.makeText(LoginActivity.this, "Invalid Phone Number.", Toast.LENGTH_SHORT).show();
                } else {
                    Tools.savePref(KEYS.PHONE_NO,"+88"+ activityLoginBinding.edittextLoginActMobileNumber.getText().toString());
                    startActivity(new Intent(LoginActivity.this, VerifyActivity.class));
                    finish();
                }
            }
        });

    }
}