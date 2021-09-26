package com.example.bloodoor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
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

import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import io.alterac.blurkit.BlurLayout;

public class SignUp_User extends AppCompatActivity {

    BlurLayout blurLayout;
    CardView signupcard,signincard;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView1;
    private EditText date;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1) //this annotation because dropdown menu code requires minimum android jelly bean...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();    //removes action bar
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_sign_up__user);

        setContentView(R.layout.activity_sign_up__user);
        autoCompleteTextView1 = findViewById(R.id.autoCompleteTextView1);
        String []option1 = {"Select", "Male", "Female"};
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, R.layout.options_item, option1);
        autoCompleteTextView1.setText(arrayAdapter1.getItem(0).toString(), false); //to make default value...
        autoCompleteTextView1.setAdapter(arrayAdapter1);
        
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        String []option = {"Select", "O+", "O-","A+", "A-", "B+", "B-", "AB+", "AB-"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.options_item, option);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false); //to make default value...
        autoCompleteTextView.setAdapter(arrayAdapter);

        signupcard = (CardView) findViewById(R.id.signupcard);
        signupcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),enternumber.class);
                startActivity(intent);
            }
        });

        signincard = (CardView) findViewById(R.id.signincard);
        signincard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),enternumber.class);
                startActivity(intent);
            }
        });

        Calendar cal= Calendar.getInstance();
        date = (EditText) findViewById(R.id.dateOfBirth);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(SignUp_User.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        String d=dayOfMonth+"-"+month+"-"+ year;
                        date.setText(d);
                    }
                },year,month,day);
                //Disables past date
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
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
}

