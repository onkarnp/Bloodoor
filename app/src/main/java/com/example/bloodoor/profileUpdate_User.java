package com.example.bloodoor;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import io.alterac.blurkit.BlurLayout;

public class profileUpdate_User extends AppCompatActivity {

    BlurLayout blurLayout;
    private EditText date, name, city, address, Email, phoneno;
    AutoCompleteTextView gender, bloodgrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update__user);
        Objects.requireNonNull(getSupportActionBar()).hide();    //removes action bar
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_profile_update__user);

        setContentView(R.layout.activity_profile_update__user);
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
        address = findViewById(R.id.homeAddress);
        phoneno = findViewById(R.id.mobileNumber);
        Email = findViewById(R.id.emailID);
        city = findViewById(R.id.city);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "MeriendaOne-Regular.ttf");
        ((TextView) findViewById(R.id.userProfileHeading)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txv_general_info)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txv_name)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txv_mobileNumber)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txv_emailId)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txv_homeAdd)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txv_city)).setTypeface(typeface);
        ((TextView) findViewById(R.id.updateDOB)).setTypeface(typeface);
        ((TextView) findViewById(R.id.autoCompleteTextView1)).setTypeface(typeface);
        ((TextView) findViewById(R.id.autoCompleteTextView)).setTypeface(typeface);

        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "MeriendaOne-Regular.ttf");
        ((Button) findViewById(R.id.btn_update)).setTypeface(typeface1);


    }
}