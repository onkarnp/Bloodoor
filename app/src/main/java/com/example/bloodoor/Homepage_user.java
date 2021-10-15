package com.example.bloodoor;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import io.alterac.blurkit.BlurLayout;

public class Homepage_user extends AppCompatActivity {

    BlurLayout blurLayout1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();    //removes action bar
        blurLayout1 = findViewById(R.id.blurLayout);
        setContentView(R.layout.activity_homepage_user);


    }

    @Override
    protected void onStart() {
        super.onStart();
        blurLayout1 = findViewById(R.id.blurLayout1);
        blurLayout1.startBlur();
    }

    @Override
    protected void onStop() {
        blurLayout1.pauseBlur();
        super.onStop();
    }
}