package com.example.bloodoor;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Objects;

public class FAQpage extends AppCompatActivity {

    LinearLayout A1, A2, A3, A4, A5, A6, A7, A8, A9;
    CardView C1, C2, C3, C4, C5, C6, C7, C8, C9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_f_a_qpage);

        A1 = findViewById(R.id.Ans1);
        A2 = findViewById(R.id.Ans2);
        A3 = findViewById(R.id.Ans3);
        A4 = findViewById(R.id.Ans4);
        A5 = findViewById(R.id.Ans5);
        A6 = findViewById(R.id.Ans6);
        A7 = findViewById(R.id.Ans7);
        A8 = findViewById(R.id.Ans8);
        A9 = findViewById(R.id.Ans9);

        C1 = findViewById(R.id.cardView1);
        C2 = findViewById(R.id.cardView2);
        C3 = findViewById(R.id.cardView3);
        C4 = findViewById(R.id.cardView4);
        C5 = findViewById(R.id.cardView5);
        C6 = findViewById(R.id.cardView6);
        C7 = findViewById(R.id.cardView7);
        C8 = findViewById(R.id.cardView8);
        C9 = findViewById(R.id.cardView9);

    }

    //methods for visibility
    public void Que1Clicked(View view) {
        if(A1.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(C1, new AutoTransition());
            A1.setVisibility(View.VISIBLE);
        }else{
            TransitionManager.beginDelayedTransition(C1, new AutoTransition());
            A1.setVisibility(View.GONE);
        }
    }

    public void Que2Clicked(View view) {
        if(A2.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(C2, new AutoTransition());
            A2.setVisibility(View.VISIBLE);
        }else{
            TransitionManager.beginDelayedTransition(C2, new AutoTransition());
            A2.setVisibility(View.GONE);
        }
    }

    public void Que3Clicked(View view) {
        if(A3.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(C3, new AutoTransition());
            A3.setVisibility(View.VISIBLE);
        }else{
            TransitionManager.beginDelayedTransition(C3, new AutoTransition());
            A3.setVisibility(View.GONE);
        }
    }

    public void Que4Clicked(View view) {
        if(A4.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(C4, new AutoTransition());
            A4.setVisibility(View.VISIBLE);
        }else{
            TransitionManager.beginDelayedTransition(C4, new AutoTransition());
            A4.setVisibility(View.GONE);
        }
    }

    public void Que5Clicked(View view) {
        if(A5.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(C5, new AutoTransition());
            A5.setVisibility(View.VISIBLE);
        }else{
            TransitionManager.beginDelayedTransition(C5, new AutoTransition());
            A5.setVisibility(View.GONE);
        }
    }

    public void Que6Clicked(View view) {
        if(A6.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(C6, new AutoTransition());
            A6.setVisibility(View.VISIBLE);
        }else{
            TransitionManager.beginDelayedTransition(C6, new AutoTransition());
            A6.setVisibility(View.GONE);
        }
    }

    public void Que7Clicked(View view) {
        if(A7.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(C7, new AutoTransition());
            A7.setVisibility(View.VISIBLE);
        }else{
            TransitionManager.beginDelayedTransition(C7, new AutoTransition());
            A7.setVisibility(View.GONE);
        }
    }

    public void Que8Clicked(View view) {
        if(A8.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(C8, new AutoTransition());
            A8.setVisibility(View.VISIBLE);
        }else{
            TransitionManager.beginDelayedTransition(C8, new AutoTransition());
            A8.setVisibility(View.GONE);
        }
    }

    public void Que9Clicked(View view) {
        if(A9.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(C9, new AutoTransition());
            A9.setVisibility(View.VISIBLE);
        }else{
            TransitionManager.beginDelayedTransition(C9, new AutoTransition());
            A9.setVisibility(View.GONE);
        }
    }
}