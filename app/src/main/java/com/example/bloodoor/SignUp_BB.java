package com.example.bloodoor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

import io.alterac.blurkit.BlurLayout;

public class SignUp_BB extends AppCompatActivity {

    BlurLayout blurLayout;
    CardView signupcard,signincard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();    //removes action bar
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_sign_up__b_b);

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