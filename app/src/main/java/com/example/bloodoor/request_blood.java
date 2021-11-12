package com.example.bloodoor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bloodoor.models.RequestBlood;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.alterac.blurkit.BlurLayout;

public class request_blood extends AppCompatActivity {

    BlurLayout blurLayout;
    AutoCompleteTextView bloodgrp;
    private EditText patientName, patientNumber, patientEmail, hospitalName, pinCode, requestReason;
    private Button submitRequestButton;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    //this annotation because dropdown menu code requires minimum android jelly bean...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_request_blood);

        patientName = findViewById(R.id.patientName);
        patientNumber = findViewById(R.id.patientNumber);
        patientEmail = findViewById(R.id.patientEmail);
        hospitalName = findViewById(R.id.hospitalName);
        pinCode = findViewById(R.id.pinCode);
        requestReason = findViewById(R.id.requestReason);
        submitRequestButton = (Button) findViewById(R.id.submitRequestButton);
        bloodgrp = findViewById(R.id.autoCompleteTextView);
        loadingBar = new ProgressDialog(this);

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        String[] option = {"Select", "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.options_item, option);
        bloodgrp.setText(arrayAdapter.getItem(0).toString(), false); //to make default value...
        bloodgrp.setAdapter(arrayAdapter);



        submitRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patient_Name = patientName.getText().toString();
                String patient_Number = patientNumber.getText().toString();
                String patient_Email = patientEmail.getText().toString();
                String hospital_Name = hospitalName.getText().toString();
                String pin_Code = pinCode.getText().toString();
                String request_Reason = requestReason.getText().toString();
                String blood_grp = bloodgrp.getText().toString();

                if(blood_grp.isEmpty()| blood_grp.equals("Select"))
                {
                    Toast.makeText(request_blood.this, "Please select your blood group", Toast.LENGTH_SHORT).show();
                }
                else if(patient_Name.isEmpty() || patient_Name.length()<5)
                {
                    showError(patientName, "Please enter valid name");
                }
                else if (patient_Number.length()!=10)
                {
                    showError(patientNumber, "Please enter valid 10 digit number");
                }
                else if(patient_Email.isEmpty() && !isEmailValid(patient_Email))
                {
                    showError(patientEmail, "Please enter valid email address");
                }
                else if(hospital_Name.isEmpty() || hospital_Name.length()<5)
                {
                    showError(hospitalName, "Please enter valid name");
                }
                else if(pin_Code.length()!=6)
                {
                    showError(pinCode, "Please enter correct pin code");
                }
                else if(request_Reason.isEmpty() || request_Reason.length()<5)
                {
                    showError(requestReason, "Please enter valid reason");
                }
                else{
                    createRequest();
                }
            }

            private void createRequest() {
                String patient_Name = patientName.getText().toString();
                String patient_Number = patientNumber.getText().toString();
                String patient_Email = patientEmail.getText().toString();
                String hospital_Name = hospitalName.getText().toString();
                String pin_Code = pinCode.getText().toString();
                String request_Reason = requestReason.getText().toString();
                String blood_grp = bloodgrp.getText().toString();
                String requestStatus = "Pending";
                RequestBlood bloodRequest = new RequestBlood(blood_grp, patient_Name, patient_Number, patient_Email, hospital_Name, pin_Code, request_Reason, requestStatus);
                rootNode = FirebaseDatabase.getInstance();
                FirebaseUser mauth = FirebaseAuth.getInstance().getCurrentUser();
                String userID = mauth.getUid();
                reference = rootNode.getReference("bloodRequests");
                reference.child(pin_Code).child(userID).setValue(bloodRequest);
                Intent intent = new Intent(getApplicationContext(), Homepage_user.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all",
                        "Someone need help!!", patient_Name+ " from "+ pin_Code + " is in search of "+ blood_grp + " donor for " + request_Reason, getApplicationContext(),request_blood.this);
                notificationsSender.SendNotifications();

                Toast.makeText(request_blood.this, "Request Submitted Successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }

    //Functions for making background blur
    @Override
    protected void onStart() {
        super.onStart();
        blurLayout = findViewById(R.id.blurLayout);
        blurLayout.startBlur();
    }

    //FUnctions for making background blur
    @Override
    protected void onStop() {
        blurLayout.pauseBlur();
        super.onStop();
    }

    //Function to show error message when input is not in correct format
    public void showError(EditText input, String s)
    {
        input.setError(s);
        input.requestFocus();
    }

    //Function to check whether an email enters is valid or not
    public boolean isEmailValid(String email)
    {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9]+(\\.[_A-Za-z0-9]+)*@[_A-Za-z0-9]+(\\.[_A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}