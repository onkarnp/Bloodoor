package com.example.bloodoor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodoor.models.bloodBankHelperClass;
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

public class profileUpdate_BB extends AppCompatActivity {

    BlurLayout blurLayout;
    private EditText fullname, holderName, mobileNo, phoneNo, email, bankAdd, pincode;
    private FirebaseAuth authProfile;
    private ProgressBar progressBar;
    private String textFullName, textHolderName, textMobile, textPhone, textAddress, textPincode, textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_profile_update__b_b);

        progressBar = findViewById(R.id.progressbar_Update_Profile);

        fullname = findViewById(R.id.updateFullName);
        holderName = findViewById(R.id.updateHolderName);
        mobileNo = findViewById(R.id.updateMobileNumber);
        phoneNo = findViewById(R.id.updatePhoneNumber);
        email = findViewById(R.id.updateEmailID);
        bankAdd = findViewById(R.id.updateBankAdd);
        pincode = findViewById(R.id.updateCityPincode);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        //show profile data
        showProfile(firebaseUser);

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
        //Validate mobile no. using Matcher and Pattern...
        String mobileRegex = "[6-9][0-9]{9}";
        Matcher mobileMatcher;
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        mobileMatcher = mobilePattern.matcher(textMobile);

        if (TextUtils.isEmpty(textFullName)) {
            Toast.makeText(profileUpdate_BB.this, "Please enter your Full Name...", Toast.LENGTH_SHORT).show();
            fullname.setError("Full Name is required...");
            fullname.requestFocus();
        } else if (TextUtils.isEmpty(textHolderName)) {
            Toast.makeText(profileUpdate_BB.this, "Please enter your Holder Name...", Toast.LENGTH_SHORT).show();
            email.setError("Holder Name is required...");
            email.requestFocus();
        } else if (TextUtils.isEmpty(textEmail)) {
            Toast.makeText(profileUpdate_BB.this, "Please enter your EmailID...", Toast.LENGTH_SHORT).show();
            email.setError("EmailID is required...");
            email.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
            Toast.makeText(profileUpdate_BB.this, "Please re-enter your EmailID...", Toast.LENGTH_SHORT).show();
            email.setError("Valid EmailID is required...");
            email.requestFocus();
        } else if (TextUtils.isEmpty(textAddress)) {
            Toast.makeText(profileUpdate_BB.this, "Please enter your Home Address...", Toast.LENGTH_SHORT).show();
            bankAdd.setError("Home Address is required...");
            bankAdd.requestFocus();
        } else if (TextUtils.isEmpty(textPincode)) {
            Toast.makeText(profileUpdate_BB.this, "Please enter your City Pincode...", Toast.LENGTH_SHORT).show();
            pincode.setError("City pincode is required...");
            pincode.requestFocus();
        } else if (TextUtils.isEmpty(textMobile)) {
            Toast.makeText(profileUpdate_BB.this, "Please enter your Mobile Number...", Toast.LENGTH_SHORT).show();
            mobileNo.setError("Mobile Number is required...");
            mobileNo.requestFocus();
        } else if (textMobile.length() != 10) {
            Toast.makeText(profileUpdate_BB.this, "Please re-enter your Mobile Number...", Toast.LENGTH_SHORT).show();
            mobileNo.setError("Mobile Number should be 10 digits...");
            mobileNo.requestFocus();
        } else if (!mobileMatcher.find()) {
            Toast.makeText(profileUpdate_BB.this, "Please re-enter your Mobile Number...", Toast.LENGTH_SHORT).show();
            mobileNo.setError("Mobile Number is not valid...");
            mobileNo.requestFocus();
        } else if (TextUtils.isEmpty(textPhone)) {
            Toast.makeText(profileUpdate_BB.this, "Please enter your Phone Number...", Toast.LENGTH_SHORT).show();
            mobileNo.setError("Phone Number is required...");
            mobileNo.requestFocus();
        } else {
            //Obtain data enter by user...
            textFullName = fullname.getText().toString();
            textHolderName = holderName.getText().toString();
            textMobile = mobileNo.getText().toString();
            textPhone = phoneNo.getText().toString();
            textAddress = bankAdd.getText().toString();
            textPincode = pincode.getText().toString();
            textEmail = email.getText().toString();

            //Enter user data into the firebase realtime database. Set up dependencies
            bloodBankHelperClass writeUserDetails = new bloodBankHelperClass(textFullName, textHolderName, textMobile, textPhone, textEmail, textAddress, textPincode);

            //Extract data from the databse for "Registered User"...
            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("ALLBloodbanks");

            String userID = firebaseUser.getUid();

            progressBar.setVisibility(View.VISIBLE);

            referenceProfile.child(userID).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        //Setting new display name
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                        firebaseUser.updateProfile(profileUpdates);

                        Toast.makeText(profileUpdate_BB.this, "Update Successful...", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(profileUpdate_BB.this, profileUser.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e) {
                            Toast.makeText(profileUpdate_BB.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("ALLBloodbanks");

        progressBar.setVisibility(View.VISIBLE);

        referenceProfile.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bloodBankHelperClass profileUser = snapshot.getValue(bloodBankHelperClass.class);
                if (profileUser != null) {
                    textFullName = profileUser.getName();
                    textHolderName = profileUser.getHandlerName();
                    textMobile = profileUser.getMobileNo();
                    textPhone = profileUser.getPhoneNo();
                    textEmail = profileUser.getEmail();
                    textAddress = profileUser.getAddress();
                    textPincode = profileUser.getbbPinCode();

                    fullname.setText(textFullName);
                    holderName.setText(textHolderName);
                    mobileNo.setText(textMobile);
                    phoneNo.setText(textPhone);
                    email.setText(textEmail);
                    bankAdd.setText(textAddress);
                    pincode.setText(textPincode);
                } else {
                    Toast.makeText(profileUpdate_BB.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profileUpdate_BB.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
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

    //FUnctions for making background blur
    @Override
    protected void onStop() {
        blurLayout.pauseBlur();
        super.onStop();
    }
}