package com.example.gymmanagementuser.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gymmanagementuser.KEYS;
import com.example.gymmanagementuser.R;
import com.example.gymmanagementuser.Tools;
import com.example.gymmanagementuser.databinding.ActivityVerifyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity {
    private static final String TAG = "ActivityVerify";
    private ActivityVerifyBinding activityVerifyBinding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public PhoneAuthProvider.ForceResendingToken resendToken;
    private String code_from_system;
    ProgressDialog Dialog;
    DatabaseReference ref;
    private String phone_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVerifyBinding = ActivityVerifyBinding.inflate(getLayoutInflater());
        setContentView(activityVerifyBinding.getRoot());

        Dialog = new ProgressDialog(this);
        ref = FirebaseDatabase.getInstance().getReference("Users");

        phone_no = Tools.getPref(KEYS.PHONE_NO, null);
        if (phone_no != null) {
            Log.d(TAG, "Phone number: " + phone_no);
            Log.d(TAG, "Sending verification code: ");
            Toast.makeText(VerifyActivity.this, "Sending verification code.", Toast.LENGTH_SHORT).show();
            sendVerificationCode(phone_no);
        }

        activityVerifyBinding.buttonVerActVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!activityVerifyBinding.pinViewVerActPinOTP.getText().toString().isEmpty()) {
                    verifyCode(activityVerifyBinding.pinViewVerActPinOTP.getText().toString());
                }
            }
        });

        activityVerifyBinding.textviewVerActResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityVerifyBinding.textviewVerActResend.getText().equals("Resend")) {
                    Log.d(TAG, "Resending verification code: ");
                    Toast.makeText(VerifyActivity.this, "Resending verification code.", Toast.LENGTH_SHORT).show();
                    resendCountable();
                } else {
                    activityVerifyBinding.textviewVerActResend.setClickable(false);
                }
            }
        });

        resendCountable();
    }

    private void sendVerificationCode(String phone_no) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone_no)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            Log.d(TAG, "onVerificationCompleted:" + credential);
            String code = credential.getSmsCode();
            if (code != null) {
                activityVerifyBinding.pinViewVerActPinOTP.setText(code);
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed: ", e);

            Toast.makeText(VerifyActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

            // Show a message and update the UI
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {

            Log.d(TAG, "onCodeSent:" + verificationId);

            // Save verification ID and resending token so we can use them later
            code_from_system = verificationId;
            resendToken = token;
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(code_from_system, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            checkUserStatus(phone_no);
                        } else {

                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifyActivity.this, "Invalid OTP. ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }

    private void checkUserStatus(String phone_no) {


        Dialog.setMessage("Please wait ...");
        Dialog.show();


        ref.child(phone_no).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    Dialog.dismiss();
                    Log.d(TAG, "Already Exist.");
                    Tools.savePrefBoolean(KEYS.IS_LOGGED_IN, true);
                    startActivity(new Intent(VerifyActivity.this, BMICalculateActivity.class));

                } else {
                    Dialog.dismiss();
                    Log.d(TAG, "Not Exist. ");
                    Tools.savePrefBoolean(KEYS.IS_LOGGED_IN, false);
                    startActivity(new Intent(VerifyActivity.this, RegisterActivity.class));
                }
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Dialog.dismiss();
                Toast.makeText(VerifyActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onCancelled: database error" + databaseError.getMessage());
            }
        });
    }

    public void resendCountable() {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                activityVerifyBinding.textviewVerActResend.setText("Resend code in " + millisUntilFinished / 1000 + " sec");
            }

            public void onFinish() {
                activityVerifyBinding.textviewVerActResend.setText("Resend");
            }
        }.start();
    }
}