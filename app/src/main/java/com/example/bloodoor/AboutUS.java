package com.example.bloodoor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUS extends AppCompatActivity {


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about_u_s);

        Element adsElement = new Element();
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.about_us)
                .setDescription("This app solely developed to reduce problems faced by blood banks and blood donors...")
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("CONNECT WITH US !")
                .addEmail("Grno24technologies@gmail.com")
                .addYoutube("UCbekhhidkzkGryM7miSYs_w")
                .addPlayStore("com.example.myapplication")
                .addInstagram("pranav_sp_309")
                .addFacebook("Pranav Pawar")
                .addTwitter("Your Twitter id")
                .addItem(createCopyright())
                .create();
        setContentView(aboutPage);
    }

    private Element createCopyright() {
        Element copyright = new Element();
        @SuppressLint("DefaultLocale") final String copyrightString = String.format("Copyright %d by BlooDoor", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        copyright.setIconDrawable(R.drawable.ic_copyright);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutUS.this, copyrightString, Toast.LENGTH_SHORT).show();
            }
        });
        return copyright;
    }
}
