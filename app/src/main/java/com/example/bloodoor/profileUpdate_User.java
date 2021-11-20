package com.example.bloodoor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodoor.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.alterac.blurkit.BlurLayout;

public class profileUpdate_User extends AppCompatActivity {

    BlurLayout blurLayout;
    private EditText fullname, mobileNo, email, homeAdd, pincode, dob;
    private RadioGroup radioGroupGenderUpdate, radioGroupBloodGruopUpdate;
    private RadioButton radioGroupUpdateSelectedGender, radioGroupUpdateSelectedBloodGruop;
    private FirebaseAuth authProfile;
    private ProgressBar progressBar;
    private String textGender, textBloodGrp, textDoB, textMobile, textFullName, textAddress, textPincode, textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update__user);
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_profile_update__user);

        progressBar = findViewById(R.id.progressbar_Update_Profile);

        fullname = findViewById(R.id.updateFullName);
        mobileNo = findViewById(R.id.updateMobileNumber);
        email = findViewById(R.id.updateEmailID);
        homeAdd = findViewById(R.id.updateHomeAdd);
        pincode = findViewById(R.id.updateCityPincode);
        dob = findViewById(R.id.updateDOB);

        radioGroupGenderUpdate = findViewById(R.id.update_radio_gender);
        radioGroupBloodGruopUpdate = findViewById(R.id.update_radio_bloodGroup);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        //show profile data
        showProfile(firebaseUser);

        dob.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String textSADoB[] = textDoB.split("/");

                int day = Integer.parseInt(textSADoB[0]);
                int month = Integer.parseInt(textSADoB[1]) - 1;
                int year = Integer.parseInt(textSADoB[2]);

                DatePickerDialog picker;
                picker = new DatePickerDialog(profileUpdate_User.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dob.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        Button buttonUpdateProfile = findViewById(R.id.btn_update);
        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(firebaseUser);
            }
        });

    }

    //Update profile...
    private void updateProfile(FirebaseUser firebaseUser) {
        int selectedGenderID = radioGroupGenderUpdate.getCheckedRadioButtonId();
        radioGroupUpdateSelectedGender = findViewById(selectedGenderID);
        int selectedBloodGroupID = radioGroupBloodGruopUpdate.getCheckedRadioButtonId();
        radioGroupUpdateSelectedBloodGruop = findViewById(selectedBloodGroupID);


        //Validate mobile no. using Matcher and Pattern...
        String mobileRegex = "[6-9][0-9]{9}";
        Matcher mobileMatcher;
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        mobileMatcher = mobilePattern.matcher(textMobile);

        if (TextUtils.isEmpty(textFullName)) {
            Toast.makeText(profileUpdate_User.this, "Please enter your Full Name...", Toast.LENGTH_SHORT).show();
            fullname.setError("Full Name is required...");
            fullname.requestFocus();
        } else if (TextUtils.isEmpty(textEmail)) {
            Toast.makeText(profileUpdate_User.this, "Please enter your EmailID...", Toast.LENGTH_SHORT).show();
            email.setError("EmailID is required...");
            email.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
            Toast.makeText(profileUpdate_User.this, "Please re-enter your EmailID...", Toast.LENGTH_SHORT).show();
            email.setError("Valid EmailID is required...");
            email.requestFocus();
        } else if (TextUtils.isEmpty(textAddress)) {
            Toast.makeText(profileUpdate_User.this, "Please enter your Home Address...", Toast.LENGTH_SHORT).show();
            homeAdd.setError("Home Address is required...");
            homeAdd.requestFocus();
        } else if (TextUtils.isEmpty(textPincode)) {
            Toast.makeText(profileUpdate_User.this, "Please enter your City Pincode...", Toast.LENGTH_SHORT).show();
            pincode.setError("City pincode is required...");
            pincode.requestFocus();
        } else if (TextUtils.isEmpty(textDoB)) {
            Toast.makeText(profileUpdate_User.this, "Please enter your Date of Birth...", Toast.LENGTH_SHORT).show();
            dob.setError("Date of Birth is required...");
            dob.requestFocus();
        } else if (TextUtils.isEmpty(textMobile)) {
            Toast.makeText(profileUpdate_User.this, "Please enter your Mobile Number...", Toast.LENGTH_SHORT).show();
            mobileNo.setError("Mobile Number is required...");
            mobileNo.requestFocus();
        } else if (textMobile.length() != 10) {
            Toast.makeText(profileUpdate_User.this, "Please re-enter your Mobile Number...", Toast.LENGTH_SHORT).show();
            mobileNo.setError("Mobile Number should be 10 digits...");
            mobileNo.requestFocus();
        } else if (!mobileMatcher.find()) {
            Toast.makeText(profileUpdate_User.this, "Please re-enter your Mobile Number...", Toast.LENGTH_SHORT).show();
            mobileNo.setError("Mobile Number is not valid...");
            mobileNo.requestFocus();
        } else if (TextUtils.isEmpty(radioGroupUpdateSelectedGender.getText())) {
            Toast.makeText(profileUpdate_User.this, "Please select your Gender...", Toast.LENGTH_SHORT).show();
            radioGroupUpdateSelectedGender.setError("Gender is required...");
            radioGroupUpdateSelectedGender.requestFocus();
        } else if (TextUtils.isEmpty(radioGroupUpdateSelectedBloodGruop.getText())) {
            Toast.makeText(profileUpdate_User.this, "Please select your Blood Group...", Toast.LENGTH_SHORT).show();
            radioGroupUpdateSelectedBloodGruop.setError("Blood Group is required...");
            radioGroupUpdateSelectedBloodGruop.requestFocus();
        } else {
            //Obtain data enter by user...
            textGender = radioGroupUpdateSelectedGender.getText().toString();
            textBloodGrp = radioGroupUpdateSelectedBloodGruop.getText().toString();
            textFullName = fullname.getText().toString();
            textDoB = dob.getText().toString();
            textMobile = mobileNo.getText().toString();
            textAddress = homeAdd.getText().toString();
            textPincode = pincode.getText().toString();
            textEmail = email.getText().toString();

            //Enter user data into the firebase realtime database. Set up dependencies
            User writeUserDetails = new User(textFullName, textAddress, textMobile, textEmail, textPincode, textDoB, textBloodGrp, textGender);

            //Extract data from the databse for "Registered User"...
            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("allusers");

            String userID = firebaseUser.getUid();

            progressBar.setVisibility(View.VISIBLE);

            referenceProfile.child(userID).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        //Setting new display name
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().
                                setDisplayName(textFullName).build();
                        firebaseUser.updateProfile(profileUpdates);

                        Toast.makeText(profileUpdate_User.this, "Update Successfull...", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(profileUpdate_User.this, profileUser.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e) {
                            Toast.makeText(profileUpdate_User.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    //fetch data from firebase and show accordingly...
    private void showProfile(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("allusers");

        progressBar.setVisibility(View.VISIBLE);

        referenceProfile.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User profileUser = snapshot.getValue(User.class);
                if (profileUser != null) {
                    textFullName = profileUser.getFullName();
                    textMobile = profileUser.getMobileNo();
                    textEmail = profileUser.getEmail();
                    textAddress = profileUser.getHomeAddress();
                    textPincode = profileUser.getPinCode();
                    textDoB = profileUser.getDate();
                    textGender = profileUser.getGender();
                    textBloodGrp = profileUser.getBloodgrp();

                    fullname.setText(textFullName);
                    mobileNo.setText(textMobile);
                    email.setText(textEmail);
                    homeAdd.setText(textAddress);
                    pincode.setText(textPincode);
                    dob.setText(textDoB);

                    //Show gender through radio button
                    if (textGender.equals("Male")) {
                        radioGroupUpdateSelectedGender = findViewById(R.id.radio_male);
                    } else {
                        radioGroupUpdateSelectedGender = findViewById(R.id.radio_female);
                    }
                    radioGroupUpdateSelectedGender.setChecked(true);

                    //Show blood group through radio button
                    if (textBloodGrp.equals("O+")) {
                        radioGroupUpdateSelectedBloodGruop = findViewById(R.id.radio_OP);
                    } else if (textBloodGrp.equals("O-")) {
                        radioGroupUpdateSelectedBloodGruop = findViewById(R.id.radio_ON);
                    } else if (textBloodGrp.equals("AB+")) {
                        radioGroupUpdateSelectedBloodGruop = findViewById(R.id.radio_ABP);
                    } else if (textBloodGrp.equals("AB-")) {
                        radioGroupUpdateSelectedBloodGruop = findViewById(R.id.radio_ABN);
                    } else if (textBloodGrp.equals("A+")) {
                        radioGroupUpdateSelectedBloodGruop = findViewById(R.id.radio_AP);
                    } else if (textBloodGrp.equals("A-")) {
                        radioGroupUpdateSelectedBloodGruop = findViewById(R.id.radio_AN);
                    } else if (textBloodGrp.equals("B+")) {
                        radioGroupUpdateSelectedBloodGruop = findViewById(R.id.radio_BP);
                    } else if (textBloodGrp.equals("B-")) {
                        radioGroupUpdateSelectedBloodGruop = findViewById(R.id.radio_BN);
                    }

                } else {
                    Toast.makeText(profileUpdate_User.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profileUpdate_User.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    //Functions for making background blurr
    @Override
    protected void onStart() {
        super.onStart();
        blurLayout = findViewById(R.id.blurLayout);
        blurLayout.startBlur();
    }

    //FUnctions for making background blurr
    @Override
    protected void onStop() {
        blurLayout.pauseBlur();
        super.onStop();
    }
}