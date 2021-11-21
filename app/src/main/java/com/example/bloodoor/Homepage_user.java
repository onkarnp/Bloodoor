package com.example.bloodoor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import io.alterac.blurkit.BlurLayout;

public class Homepage_user extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    CardView search_donor_card, request_blood_card, live_events_card, find_banks_card, all_BloodBanks_Card, check_request_card;
    BlurLayout blurLayout1;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private Spinner spType;
    private Button btFind;
    private Context context;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blurLayout1 = findViewById(R.id.blurLayout1);
        setContentView(R.layout.activity_homepage_user);


        dialog = new Dialog(Homepage_user.this);
        dialog.setContentView(R.layout.find_location);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.anim.fade_in;
        CardView blood_bank_card= dialog.findViewById(R.id.blood_bank_card);
        CardView hospital_card= dialog.findViewById(R.id.hospitals_card);
        CardView atm_card= dialog.findViewById(R.id.atm_card);
        CardView bank_card= dialog.findViewById(R.id.banks_card);

        blood_bank_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("geo:0, 0?q=" + "nearby blood banks");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        hospital_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("geo:0, 0?q=" + "nearby hospitals");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        atm_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("geo:0, 0?q=" + "nearby atms");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        bank_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("geo:0, 0?q=" + "nearby banks");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        search_donor_card = (CardView) findViewById(R.id.search_donor);
        search_donor_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), search_donor.class);
                startActivity(intent);
            }
        });

        request_blood_card = (CardView) findViewById(R.id.request_blood);
        request_blood_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), request_blood.class);
                startActivity(intent);
            }
        });

        live_events_card = (CardView) findViewById(R.id.live_events);
        live_events_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), live_events.class);
                startActivity(intent);
                Toast.makeText(Homepage_user.this, "To check all live/upcoming events,\nclick on search", Toast.LENGTH_SHORT).show();
            }
        });

        find_banks_card = (CardView) findViewById(R.id.find_banks);
        find_banks_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        all_BloodBanks_Card = (CardView) findViewById(R.id.all_BloodBanks);
        all_BloodBanks_Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), allBloodBanks.class);
                startActivity(intent);
                Toast.makeText(Homepage_user.this, "To see all blood banks,\nclick on search", Toast.LENGTH_SHORT).show();
            }
        });

        check_request_card = (CardView) findViewById(R.id.check_requests);
        check_request_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), check_requests.class);
                startActivity(intent);
                Toast.makeText(Homepage_user.this, "To see all blood requests,\nclick on search", Toast.LENGTH_SHORT).show();
            }
        });


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BlooDoor");

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Homepage_user.this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.home);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                break;
            case R.id.profile:
                Intent intent = new Intent(Homepage_user.this, profileUser.class);
                startActivity(intent);
                break;
            case R.id.about:
                Intent intent1 = new Intent(Homepage_user.this, AboutUS.class);
                startActivity(intent1);
                break;
            case R.id.faq:
                Intent intent2 = new Intent(Homepage_user.this, FAQpage.class);
                startActivity(intent2);
                break;
            case R.id.logOut:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Homepage_user.this);
                alertDialogBuilder.setTitle("Confirm Exit..!!");
                alertDialogBuilder.setIcon(R.drawable.ic_exit);
                alertDialogBuilder.setMessage("Are you sure ?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent3 = new Intent(getApplicationContext(),options.class);
                        mAuth.signOut();
                        startActivity(intent3);
                        Toast.makeText(Homepage_user.this,"Logged out successfully",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Homepage_user.this,"Cancelled..",Toast.LENGTH_LONG).show();
                    }
                });
                alertDialogBuilder.show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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