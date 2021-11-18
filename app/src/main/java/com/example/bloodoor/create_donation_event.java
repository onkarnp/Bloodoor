package com.example.bloodoor;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodoor.models.Events;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import io.alterac.blurkit.BlurLayout;

public class create_donation_event extends AppCompatActivity {

    private BlurLayout blurLayout;
    private EditText bankName, name, startDate, endDate, description, duration, venue, pin;
    private ProgressDialog loadingBar;

    //Initiation of Firebase attributes
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_create_donation_event);

        bankName = findViewById(R.id.eventBloodBankName);
        name = findViewById(R.id.eventName);
        startDate = findViewById(R.id.eventStartDate);
        endDate = findViewById(R.id.eventEndDate);
        description = findViewById(R.id.eventDescription);
        duration = findViewById(R.id.eventDuration);
        venue = findViewById(R.id.eventAddress);
        pin = findViewById(R.id.eventPin);
        Button createEvent = findViewById(R.id.createEventButton);

        loadingBar = new ProgressDialog(this);
        Calendar cal = Calendar.getInstance();

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(create_donation_event.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String d = dayOfMonth + "-" + month + "-" + year;
                        startDate.setText(d);
                    }
                }, year, month, day);
                //Disables past date
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                //Show date picker dialog
                datePickerDialog.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(create_donation_event.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String d = dayOfMonth + "-" + month + "-" + year;
                        endDate.setText(d);
                    }
                }, year, month, day);
                //Disables past date
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                //Show date picker dialog
                datePickerDialog.show();
            }
        });

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setTitle("Create Event");
                loadingBar.setMessage("Your event is being created...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                String textBankName = bankName.getText().toString();
                if (TextUtils.isEmpty(bankName.getText())) {
                    loadingBar.dismiss();
                    Toast.makeText(create_donation_event.this, "Please enter your Blood Bank name...", Toast.LENGTH_SHORT).show();
                    bankName.setError("Blood Bank name is required...");
                    bankName.requestFocus();
                } else {
                    String textEventName = name.getText().toString();
                    if (TextUtils.isEmpty(name.getText())) {
                        loadingBar.dismiss();
                        Toast.makeText(create_donation_event.this, "Please enter name of event...", Toast.LENGTH_SHORT).show();
                        name.setError("Name of event is required...");
                        name.requestFocus();
                    } else {
                        String textStartDate = startDate.getText().toString();
                        if (TextUtils.isEmpty(startDate.getText())) {
                            loadingBar.dismiss();
                            Toast.makeText(create_donation_event.this, "Please enter start date of event...", Toast.LENGTH_SHORT).show();
                            startDate.setError("Start date of event is required...");
                            startDate.requestFocus();
                        } else {
                            String textEndDate = endDate.getText().toString();
                            if (TextUtils.isEmpty(endDate.getText())) {
                                loadingBar.dismiss();
                                Toast.makeText(create_donation_event.this, "Please enter end date of event...", Toast.LENGTH_SHORT).show();
                                endDate.setError("End date of event is required...");
                                endDate.requestFocus();
                            } else {
                                String textDescription = description.getText().toString();
                                if (TextUtils.isEmpty(description.getText())) {
                                    loadingBar.dismiss();
                                    Toast.makeText(create_donation_event.this, "Please enter description of event...", Toast.LENGTH_SHORT).show();
                                    description.setError("Description of event is required...");
                                    description.requestFocus();
                                } else {
                                    String textDuration = duration.getText().toString();
                                    if (TextUtils.isEmpty(duration.getText())) {
                                        loadingBar.dismiss();
                                        Toast.makeText(create_donation_event.this, "Please enter duration of event...", Toast.LENGTH_SHORT).show();
                                        duration.setError("Duration of event is required...");
                                        duration.requestFocus();
                                    } else {
                                        String textVenue = venue.getText().toString();
                                        if (TextUtils.isEmpty(venue.getText())) {
                                            loadingBar.dismiss();
                                            Toast.makeText(create_donation_event.this, "Please enter venue of event...", Toast.LENGTH_SHORT).show();
                                            venue.setError("Venue of event is required...");
                                            venue.requestFocus();
                                        } else {
                                            String textVenuePin = pin.getText().toString();
                                            if (TextUtils.isEmpty(pin.getText()) | textVenuePin.length() != 6) {
                                                loadingBar.dismiss();
                                                Toast.makeText(create_donation_event.this, "Please enter pin for the venue...", Toast.LENGTH_SHORT).show();
                                                pin.setError("Please enter correct pin...");
                                                pin.requestFocus();
                                            } else {
                                                String status = "Upcoming";
                                                rootNode = FirebaseDatabase.getInstance();
                                                mAuth = FirebaseAuth.getInstance();
                                                String userID = mAuth.getCurrentUser().getUid();
                                                reference = rootNode.getReference("Events");
                                                Events events = new Events(textBankName, textEventName, textStartDate, textEndDate, textDescription, status, textDuration, textVenue, textVenuePin);
                                                reference.child(textStartDate).child(userID).setValue(events);

                                                String s = textDuration;
                                                String[] split = s.split("::");
                                                String firstSubString = split[0];
                                                String secondSubString = split[1];
                                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all",
                                                        "Blood Donation Event", textBankName + " is organising a blood donation event on " + textStartDate + " from " + firstSubString + " to " + secondSubString + ". \nDo come at " + textVenue + ", " + textVenuePin + " and Donate blood.", getApplicationContext(), create_donation_event.this);
                                                notificationsSender.SendNotifications();

                                                loadingBar.dismiss();
                                                Toast.makeText(create_donation_event.this, "Event created successfully (:", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), Homepage_BB.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
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