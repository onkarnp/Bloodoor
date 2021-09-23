package com.example.bloodoor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class enternumber extends AppCompatActivity {

    EditText entermobilenumber;
    Button getotpbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enternumber);

        entermobilenumber = findViewById(R.id.input_mobile_number);
        getotpbutton = findViewById(R.id.buttongetotp);

        final ProgressBar progressBar = findViewById(R.id.progressbar_sending_otp);

        getotpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!entermobilenumber.getText().toString().trim().isEmpty()) {
                    if ((entermobilenumber.getText().toString().trim()).length() == 10) {

                        progressBar.setVisibility(View.VISIBLE);
                        getotpbutton.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + entermobilenumber.getText().toString(),
                                90,
                                TimeUnit.SECONDS,
                                enternumber.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        getotpbutton.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        getotpbutton.setVisibility(View.INVISIBLE);
                                        Toast.makeText(enternumber.this, "Please check your internet connection...", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        getotpbutton.setVisibility(View.INVISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), verifyotp.class);
                                        intent.putExtra("mobile", entermobilenumber.getText().toString());
                                        intent.putExtra("backendotp",backendotp);
                                        startActivity(intent);
                                    }
                                }
                        );

//                        Intent intent = new Intent(getApplicationContext(),verifyotp.class);
//                        intent.putExtra("mobile",entermobilenumber.getText().toString());
//                        startActivity(intent);

                    } else {
                        Toast.makeText(enternumber.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(enternumber.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}