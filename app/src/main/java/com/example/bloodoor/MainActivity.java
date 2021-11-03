package com.example.bloodoor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.MessagePattern;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.os.Handler;
import com.airbnb.lottie.LottieAnimationView;

import java.util.Objects;

import io.alterac.blurkit.BlurLayout;

public class MainActivity extends AppCompatActivity {

    private static  int SPLASH_SCREEN = 3000;
    BlurLayout blurLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_main);

        ImageView logo = findViewById(R.id.logo);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        logo.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,options.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
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