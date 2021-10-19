package com.example.bloodoor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import io.alterac.blurkit.BlurLayout;

public class SignUp_BB extends AppCompatActivity {

    BlurLayout blurLayout;
    CardView signupcard, signincard;
    private EditText name, handlerName, mobileNo, phoneNo, email, address, city;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();    //removes action bar
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_sign_up__b_b);

        //Hooks
        name = findViewById(R.id.enterBloodBankName);
        handlerName = findViewById(R.id.enterFullName);
        mobileNo = findViewById(R.id.mobileNumber);
        phoneNo = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.emailID);
        address = findViewById(R.id.homeAddress);
        city = findViewById(R.id.city);



        //Save data in Firebase on Button Click
        signupcard = (CardView) findViewById(R.id.signupcard);
        signupcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference().child("BloodBanks");

                //Get all the values in the strings...
                String regName = name.getText().toString();
                String regHandlerName = handlerName.getText().toString();
                String regMobileNo = mobileNo.getText().toString();
                String regPhoneNo = phoneNo.getText().toString();
                String regEmail = email.getText().toString();
                String regAddress = address.getText().toString();
                String regCity = city.getText().toString();

                Intent intent = new Intent(getApplicationContext(), verifyotp_BB.class);
                intent.putExtra("mobileNo",regMobileNo);
                startActivity(intent);

                //Storing data in the Firebase...
                //bloodBankHelperClass helperClass = new bloodBankHelperClass(regName, regHandlerName, regMobileNo, regPhoneNo, regEmail, regAddress, regCity);
                //reference.child(regMobileNo).setValue(helperClass);
            }
        });

        signincard = (CardView) findViewById(R.id.signincard);
        signincard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), enternumber.class);
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