package com.example.gymmanagementuser.ui;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.gymmanagementuser.KEYS;
import com.example.gymmanagementuser.R;
import com.example.gymmanagementuser.Tools;
import com.example.gymmanagementuser.model.UserInfo;
import com.example.gymmanagementuser.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "ActivityRegistration";
    private ActivityRegisterBinding activityRegisterBinding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String full_name, user_email, user_address, user_birthDate, user_gender, user_phoneNumber;
    private int image_rec_code = 1;
    private Uri filepath_uri;
    ProgressDialog Dialog;
    Calendar mcurrentDate;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterBinding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        storageReference = FirebaseStorage.getInstance().getReference("user_images");
        firebaseAuth = FirebaseAuth.getInstance();

        Dialog = new ProgressDialog(this);
        mcurrentDate = Calendar.getInstance();

        //Spinner
        spinnerGenderTypes();


        activityRegisterBinding.imageviewRegActImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });

        activityRegisterBinding.buttonRegActRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userRegistrationValidation() && filepath_uri!=null) {
                  userRegistration("" + user_phoneNumber);
                } else {
                    Toast.makeText(RegisterActivity.this, "Please fill the all Information. ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        activityRegisterBinding.textviewRegActBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentDay = 0;
                int currentYear = 0;
                int currentMonth = 0;
                if (currentYear == 0 || currentMonth == 0 || currentDay == 0) {
                    currentYear = mcurrentDate.get(Calendar.YEAR);
                    currentMonth = mcurrentDate.get(Calendar.MONTH);
                    currentDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                }
                datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                activityRegisterBinding.textviewRegActBirthDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                            }
                        }, currentYear, currentMonth, currentDay);
                datePickerDialog.show();
            }
        });
    }
    private void spinnerGenderTypes() {
        ArrayAdapter<String> hospital_typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item
                , getResources().getStringArray(R.array.gender_types));
        hospital_typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityRegisterBinding.spinnerRegActGender.setAdapter(hospital_typeAdapter);
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    filepath_uri = uri;
                    Picasso.get().load(filepath_uri).into(activityRegisterBinding.imageviewRegActImage);

                }
            });

    //check all Validation
    public boolean userRegistrationValidation() {

        full_name = activityRegisterBinding.edittextRegActFullName.getText().toString();
        user_email = activityRegisterBinding.edittextRegActEmailAddress.getText().toString().trim();
        user_address = activityRegisterBinding.edittextRegActAddress.getText().toString().trim();
        user_birthDate = activityRegisterBinding.textviewRegActBirthDate.getText().toString().trim();
        user_gender = activityRegisterBinding.spinnerRegActGender.getSelectedItem().toString();
        user_phoneNumber = Tools.getPref(KEYS.PHONE_NO, null);


        if (full_name.isEmpty() || user_email.isEmpty() || user_address.isEmpty() || user_birthDate.isEmpty()
                || user_gender.equals("Gender")  || user_phoneNumber.isEmpty()) {
            Log.d(TAG, "userRegistrationValidation: false");
            return false;
        } else {
            Log.d(TAG, "userRegistrationValidation: true");
            return true;
        }

    }

    //Get image extention
    public String GetFileExtention(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    //Registration of User
    public void userRegistration(String user_phoneNumber) {

        Log.d(TAG, "Registration processing.");

        if (filepath_uri != null) {
            Dialog.setMessage("Please wait ...");
            Dialog.show();


            storageReference = storageReference.child(System.currentTimeMillis() + "." + GetFileExtention(filepath_uri));
            storageReference.putFile(filepath_uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String url = uri.toString();
                                    UserInfo userinfo = new UserInfo(full_name, user_email, user_address, user_birthDate, user_gender, user_phoneNumber, url);
                                    databaseReference.child(user_phoneNumber).setValue(userinfo);
                                    Dialog.dismiss();

                                    Log.d(TAG, "Successfully added");
                                    Toast.makeText(RegisterActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                                    Tools.savePrefBoolean(KEYS.IS_LOGGED_IN, true);

                                    startActivity(new Intent(RegisterActivity.this, BMICalculateActivity.class));
                                    finish();
                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Dialog.dismiss();
                    Log.d(TAG, "" + e.getMessage());
                    Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}