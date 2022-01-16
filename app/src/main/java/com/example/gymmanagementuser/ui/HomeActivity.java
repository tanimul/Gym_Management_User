package com.example.gymmanagementuser.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gymmanagementuser.KEYS;
import com.example.gymmanagementuser.R;
import com.example.gymmanagementuser.Tools;
import com.example.gymmanagementuser.databinding.ActivityHomeBinding;
import com.example.gymmanagementuser.databinding.NavHeaderLayoutBinding;
import com.example.gymmanagementuser.fragment.DietChartFragment;
import com.example.gymmanagementuser.fragment.RoutineFragment;
import com.example.gymmanagementuser.model.UserInfo;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NavigationBarView.OnItemSelectedListener {
    private static final String TAG = "ActivityHome";
    private ActivityHomeBinding activityHomeBinding;
    private DatabaseReference databaseReference;
    private NavHeaderLayoutBinding navHeaderLayoutBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, activityHomeBinding.layoutHomeActDrawerLayout,
                activityHomeBinding.toolbarHomeActTop,
                R.string.openNavDrawer, R.string.closeNavDrawer);
        activityHomeBinding.layoutHomeActDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navHeaderLayoutBinding = NavHeaderLayoutBinding.bind(activityHomeBinding.navHomeActNavBar.getHeaderView(0));

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        getHeaderInformation(Tools.getPref(KEYS.PHONE_NO, null));

        activityHomeBinding.navHomeActNavBar.setNavigationItemSelectedListener(this);
        activityHomeBinding.bottomNavHomeActBottomNavView1.setOnItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_HomeAct_container, new RoutineFragment()).commit();

    }


    private void getHeaderInformation(String phone_no) {

        databaseReference.child(phone_no).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "Data: " + snapshot.getKey());
                UserInfo userInfo = snapshot.getValue(UserInfo.class);
                navHeaderLayoutBinding.textviewNavHeaderLayoutText.setText("" + userInfo.getUserFullName());
                navHeaderLayoutBinding.textviewNavHeaderLayoutPhoneNumber.setText("" + userInfo.getUserPhoneNumber());
                Picasso.get().load(userInfo.getUserImageLink()).into(navHeaderLayoutBinding.imageviewNavHeaderLayoutImage);

                Tools.savePref(KEYS.FULL_NAME, "" + userInfo.getUserFullName());
                Tools.savePref(KEYS.IMAGE_URI, "" + userInfo.getUserImageLink());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onCancelled: database error" + error.getMessage());
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "Navigation item selected. ");

        switch (item.getItemId()) {
            case R.id.bottom_nav_diet_chart:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_HomeAct_container, new DietChartFragment()).commit();
                break;
            case R.id.bottom_nav_routine:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_HomeAct_container, new RoutineFragment()).commit();
                break;
            case R.id.nav_setting:
                startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                break;
            case R.id.nav_rate_me:
                Toast.makeText(HomeActivity.this, "Rate the app", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_term_condition:
                Toast.makeText(HomeActivity.this, "Term and Condition", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                logout();
                break;


        }
        return true;
    }

    //logout current user
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Tools.savePrefBoolean(KEYS.IS_LOGGED_IN, false);
        finish();
    }
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        closeDrawer();
        if (!Tools.getPrefBoolean(KEYS.IS_LOGGED_IN, false).equals(true)) {
            finish();
        }

    }
    public void closeDrawer() {
        activityHomeBinding.layoutHomeActDrawerLayout.closeDrawer(Gravity.LEFT, false);
    }
}