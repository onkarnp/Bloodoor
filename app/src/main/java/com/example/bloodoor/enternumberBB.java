package com.example.bloodoor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import io.alterac.blurkit.BlurLayout;

public class enternumberBB extends AppCompatActivity {

    EditText entermobilenumber;
    Button getotpbutton;
    BlurLayout blurLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blurLayout1 = findViewById(R.id.blurLayout);
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
                                enternumberBB.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        getotpbutton.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        getotpbutton.setVisibility(View.VISIBLE);
                                        Toast.makeText(enternumberBB.this, "Network Error:(", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        getotpbutton.setVisibility(View.INVISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), verifyotp_BB.class);
                                        intent.putExtra("mobile", entermobilenumber.getText().toString());
                                        intent.putExtra("backendotp",backendotp);
                                        intent.putExtra("from","enternumber");
                                        startActivity(intent);
                                    }
                                }
                        );

//                        Intent intent = new Intent(getApplicationContext(),verifyotp.class);
//                        intent.putExtra("mobile",entermobilenumber.getText().toString());
//                        startActivity(intent);

                    } else {
                        Toast.makeText(enternumberBB.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(enternumberBB.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        blurLayout1 = findViewById(R.id.blurLayout1);
        blurLayout1.startBlur();
    }

    @Override
    protected void onStop() {
        blurLayout1.pauseBlur();
        super.onStop();
    }
}