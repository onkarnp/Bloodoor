package com.example.bloodoor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodoor.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.alterac.blurkit.BlurLayout;

public class profileUser extends AppCompatActivity {

    private TextView fullname, mobileNo, email, homeAdd, city, dob, bloodGroup, gender;
    private TextView fullnameLabel, usernameLabel;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String userID;
    private Button updateProfileButton;
    private ProgressDialog loadingBar;
    BlurLayout blurLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes tite bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_profile_user);

        reference = FirebaseDatabase.getInstance().getReference("allusers");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        fullname = findViewById(R.id.user_name_profile);
        mobileNo = findViewById(R.id.mobile_number_profile);
        email = findViewById(R.id.email_id_profile);
        homeAdd = findViewById(R.id.home_address_profile);
        city = findViewById(R.id.pin_code_profile);
        dob = findViewById(R.id.dateOfBirth_profile);
        bloodGroup = findViewById(R.id.blood_gruop_profile);
        gender = findViewById(R.id.gender_profile);
        fullnameLabel = findViewById(R.id.full_name_field);
        usernameLabel = findViewById(R.id.user_name_field);
        updateProfileButton = (Button) findViewById(R.id.btn_update);
        loadingBar = new ProgressDialog(this);
        loadingBar.setTitle("User Profile");
        loadingBar.setMessage("Fetching user profile details, " + "Please wait for a moment...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        reference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User profileUser = snapshot.getValue(User.class);
                if (profileUser != null) {
                    String user_username = profileUser.getFullName();
                    String user_mobileNo = profileUser.getMobileNo();
                    String user_emailID = profileUser.getEmail();
                    String user_homeAdd = profileUser.getHomeAddress();
                    String user_city = profileUser.getPinCode();
                    String user_dob = profileUser.getDate();
                    String user_bloodGroup = profileUser.getBloodgrp();
                    String user_gender = profileUser.getGender();
                    loadingBar.dismiss();

                    fullnameLabel.setText(user_username);
                    usernameLabel.setText(user_homeAdd);
                    fullname.setText(user_username);
                    mobileNo.setText(user_mobileNo);
                    email.setText(user_emailID);
                    homeAdd.setText(user_homeAdd);
                    city.setText(user_city);
                    dob.setText(user_dob);
                    bloodGroup.setText(user_bloodGroup);
                    gender.setText(user_gender);
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingBar.dismiss();
                Toast.makeText(profileUser.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
            }
        });

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), profileUpdate_User.class);
                startActivity(intent);
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