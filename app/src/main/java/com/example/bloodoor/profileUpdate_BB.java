package com.example.bloodoor;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import io.alterac.blurkit.BlurLayout;

public class profileUpdate_BB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update__b_b);
        BlurLayout blurLayout;
        Objects.requireNonNull(getSupportActionBar()).hide();    //removes action bar
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_profile_update__b_b);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "MeriendaOne-Regular.ttf");
        ((TextView) findViewById(R.id.userProfileHeading)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txv_general_info)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txv_name)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txv_holderName)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txv_mobileNumber)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txv_phoneNumber)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txv_emailId)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txv_bankAdd)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txv_city)).setTypeface(typeface);

        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "MeriendaOne-Regular.ttf");
        ((Button) findViewById(R.id.btn_update)).setTypeface(typeface1);

    }
}