package com.example.bloodoor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class Homepage_BB extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BlurLayout blurLayout1;
    CardView cardView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    CardView create_event_card, show_all_event_card, blood_available_card, all_users_card, check_request_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blurLayout1 = findViewById(R.id.blurLayout1);
        setContentView(R.layout.activity_homepage__b_b);

        create_event_card = (CardView) findViewById(R.id.create_event);
        create_event_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), create_donation_event.class);
                startActivity(intent);
            }
        });

        show_all_event_card = (CardView) findViewById(R.id.show_all_event);
        show_all_event_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AllEvents.class);
                startActivity(intent);
            }
        });

        blood_available_card = (CardView) findViewById(R.id.blood_available);
        blood_available_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), available_blood.class);
                startActivity(intent);
            }
        });

        all_users_card = (CardView) findViewById(R.id.all_users);
        all_users_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), allUsers.class);
                startActivity(intent);
                Toast.makeText(Homepage_BB.this, "To check all users, click on search", Toast.LENGTH_SHORT).show();
            }
        });

        check_request_card = (CardView) findViewById(R.id.check_requests);
        check_request_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), check_requests.class);
                startActivity(intent);
                Toast.makeText(Homepage_BB.this, "To check all requests, click on search", Toast.LENGTH_SHORT).show();
            }
        });

        cardView = (CardView) findViewById(R.id.cardview);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Homepage_BB.this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.home);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                break;
            case R.id.profile:
                Intent intent = new Intent(Homepage_BB.this, profileBB.class);
                startActivity(intent);
                break;
            case R.id.about:
                Intent intent1 = new Intent(Homepage_BB.this, AboutUS.class);
                startActivity(intent1);
                break;
            case R.id.faq:
                Intent intent2 = new Intent(Homepage_BB.this, FAQpage.class);
                startActivity(intent2);
                break;
            case R.id.logOut:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Homepage_BB.this);
                alertDialogBuilder.setTitle("Confirm Exit..!!");
                alertDialogBuilder.setIcon(R.drawable.ic_exit);
                alertDialogBuilder.setMessage("Are you sure ?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), options.class);
                        mAuth.signOut();
                        startActivity(intent);
                        Toast.makeText(Homepage_BB.this, "Logged out successfully", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Homepage_BB.this, "Cancelled..", Toast.LENGTH_LONG).show();
                    }
                });
                alertDialogBuilder.show();
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
