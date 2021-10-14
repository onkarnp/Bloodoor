package com.example.bloodoor;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Objects;

import io.alterac.blurkit.BlurLayout;

public class options extends AppCompatActivity {

    BlurLayout blurLayout;
    public CardView bbcard,usercard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();    //removes action bar
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_options);

        bbcard = (CardView) findViewById(R.id.option_bb);
        bbcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUp_BB.class);
                startActivity(intent);
            }
        });

        usercard = (CardView) findViewById(R.id.option_user);
        usercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp_User.class);
                startActivity(intent);
            }
        });

        Typeface typeface = Typeface.createFromAsset(getAssets(), "MeriendaOne-Regular.ttf");
        ((TextView) findViewById(R.id.option)).setTypeface(typeface);
        ((TextView) findViewById(R.id.option1)).setTypeface(typeface);
        ((TextView) findViewById(R.id.option2)).setTypeface(typeface);
    }

    //FUnctions for making background blurr
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