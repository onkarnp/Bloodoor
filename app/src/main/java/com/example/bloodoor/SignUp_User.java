package com.example.bloodoor;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.bloodoor.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.alterac.blurkit.BlurLayout;

public class SignUp_User extends AppCompatActivity {

    BlurLayout blurLayout;
    CardView signupcard, signincard;
    private EditText date, name, pincode, address, Email, phoneno;
    AutoCompleteTextView gender, bloodgrp;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference ref;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    //this annotation because dropdown menu code requires minimum android jelly bean...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_sign_up__user);

        setContentView(R.layout.activity_sign_up__user);
        gender = findViewById(R.id.autoCompleteTextView1);
        String[] option1 = {"Select", "Male", "Female"};
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, R.layout.options_item, option1);
        gender.setText(arrayAdapter1.getItem(0).toString(), false); //to make default value...
        gender.setAdapter(arrayAdapter1);

        bloodgrp = findViewById(R.id.autoCompleteTextView);
        String[] option = {"Select", "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.options_item, option);
        bloodgrp.setText(arrayAdapter.getItem(0).toString(), false); //to make default value...
        bloodgrp.setAdapter(arrayAdapter);

        name = findViewById(R.id.enterFullName);
        address = findViewById(R.id.bankAddress);
        phoneno = findViewById(R.id.mobileNumber);
        Email = findViewById(R.id.emailID);
        pincode = findViewById(R.id.pin_code);
        signupcard = (CardView) findViewById(R.id.signupcard);
        signincard = (CardView) findViewById(R.id.signincard);

        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            loadingBar.setMessage("Fetching Your Data");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            String userID = mAuth.getCurrentUser().getUid();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("allusers");
            ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        for(DataSnapshot dataSnapshot : task.getResult().getChildren()){
                            if(userID.equals(dataSnapshot.getKey())){
                                loadingBar.dismiss();
                                Toast.makeText(SignUp_User.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp_User.this,Homepage_user.class));
                                finish();
                            }
                        }
                    }
                    else{
                        loadingBar.dismiss();
                        Toast.makeText(SignUp_User.this, "You are not a registered user :(\n Please sign up", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp_User.this,options.class));
                    }
                }
            });
        }

        signupcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = name.getText().toString();
                String homeAddress = address.getText().toString();
                String mobileNo = phoneno.getText().toString();
                String email = Email.getText().toString();
                String pin_code = pincode.getText().toString();
                String Date = date.getText().toString();
                String Bloodgrp = bloodgrp.getText().toString();
                String Gender = gender.getText().toString();

                if(fullName.isEmpty() || fullName.length()<5)
                {
                    showError(name, "Please enter valid name");
                }
                else if (mobileNo.length()!=10)
                {
                    showError(phoneno, "Please enter valid 10 digit number");
                }
                else if(email.isEmpty() && !isEmailValid(email))
                {
                    showError(Email, "Please enter valid email address");
                }
                else if (homeAddress.isEmpty() || homeAddress.length()<5)
                {
                    showError(address, "Please enter valid home address");
                }
                else if(pin_code.length()!=6)
                {
                    showError(pincode, "Please enter correct pin code");
                }
                else if(Date.isEmpty())
                {
                    showError(date, "Please select correct date");
                }
                else if(Gender.isEmpty() | Gender.equals("Select"))
                {
                    Toast.makeText(SignUp_User.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                }
                else if(Bloodgrp.isEmpty()| Bloodgrp.equals("Select"))
                {
                    Toast.makeText(SignUp_User.this, "Please select your blood group", Toast.LENGTH_SHORT).show();
                }
                else{
                    createUser();
                }
            }


            //function for registration
            public void createUser(){
                String fullName = name.getText().toString();
                String homeAddress = address.getText().toString();
                String mobileNo = phoneno.getText().toString();
                String email = Email.getText().toString();
                String pin_code = pincode.getText().toString();
                String Date = date.getText().toString();
                String Bloodgrp = bloodgrp.getText().toString();
                String Gender = gender.getText().toString();
                User user = new User(fullName, homeAddress, mobileNo, email,
                        pin_code, Date, Bloodgrp, Gender);
                if (!phoneno.getText().toString().trim().isEmpty()) {
                    if ((phoneno.getText().toString().trim()).length() == 10) {


                        signupcard.setVisibility(View.INVISIBLE);
//                        loadingBar.setTitle("Create Account");
                        loadingBar.setMessage("Signing up");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + phoneno.getText().toString(),
                                90,
                                TimeUnit.SECONDS,
                                SignUp_User.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                        signupcard.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        signupcard.setVisibility(View.VISIBLE);
                                        loadingBar.dismiss();
                                        Toast.makeText(SignUp_User.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                        signupcard.setVisibility(View.INVISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), verifyotp_User.class);
                                        User user = new User(fullName, homeAddress, mobileNo, email,
                                                pin_code, Date, Bloodgrp, Gender);
                                        intent.putExtra("mobile", phoneno.getText().toString());
                                        intent.putExtra("backendotp", backendotp);
                                        intent.putExtra("User", user);
                                        intent.putExtra("from","SignUp_User");
                                        loadingBar.dismiss();
                                        startActivity(intent);

                                    }
                                }
                        );

//                        Intent intent = new Intent(getApplicationContext(),verifyotp.class);
//                        intent.putExtra("mobile",entermobilenumber.getText().toString());
//                        startActivity(intent);

                    } else {
                        loadingBar.dismiss();
                        signupcard.setVisibility(View.VISIBLE);
                        Toast.makeText(SignUp_User.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loadingBar.dismiss();
                    signupcard.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUp_User.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }

            }


        });

        signincard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), enternumberUser.class);
                startActivity(intent);
            }
        });

        Calendar cal = Calendar.getInstance();
        date = (EditText) findViewById(R.id.dateOfBirth);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUp_User.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String d = dayOfMonth + "-" + month + "-" + year;
                        date.setText(d);
                    }
                }, year, month, day);
                //Disables past date
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                //Show date picker dialog
                datePickerDialog.show();
            }
        });


    }

    //FUnctions for making background blur
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

