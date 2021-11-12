package com.example.bloodoor;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodoor.adapter.BloodRequestAdapter;
import com.example.bloodoor.models.RequestBlood;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.alterac.blurkit.BlurLayout;

public class check_requests extends AppCompatActivity {

    BlurLayout blurLayout;
    EditText pin_code;
    Button search_button;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    ArrayList<RequestBlood> requestList;
    BloodRequestAdapter bloodRequestAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_check_requests);
        pin_code = findViewById(R.id.pin_code);
        search_button = (Button) findViewById(R.id.search_requests);
        recyclerView = findViewById(R.id.blood_requests);
        requestList = new ArrayList<RequestBlood>();
        bloodRequestAdapter = new BloodRequestAdapter(this, requestList);
        recyclerView.setAdapter(bloodRequestAdapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();

        if (requestList == null)
            requestList = new ArrayList<RequestBlood>();

        BloodRequestAdapter bloodRequestAdapter = new BloodRequestAdapter(this, requestList);
        recyclerView.setAdapter(bloodRequestAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pin_code.getText().toString().isEmpty()) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events");
                    mAuth = FirebaseAuth.getInstance();
                    String userID = mAuth.getCurrentUser().getUid();
                    String pinCode = pin_code.getText().toString();
                    database.getReference().child("bloodRequests").child(pinCode).orderByValue().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            requestList.clear();
                            bloodRequestAdapter.notifyDataSetChanged();
                            RequestBlood requestBlood = null;

                            for (DataSnapshot snap : snapshot.getChildren()) {
                                requestBlood = snap.getValue(RequestBlood.class);
                                RequestBlood request = new RequestBlood(requestBlood.getBloodgrp(), requestBlood.getPatientName(), requestBlood.getPatientNumber(), requestBlood.getPatientEmail(), requestBlood.getHospitalName(), requestBlood.getPinCode(), requestBlood.getRequestReason(), requestBlood.getRequestStatus());
                                requestList.add(request);
                            }
                            if (requestBlood == null) {
                                Toast.makeText(check_requests.this, "No Result", Toast.LENGTH_SHORT).show();
                            }
                            bloodRequestAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    database.getReference().child("bloodRequests").orderByValue().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            requestList.clear();
                            bloodRequestAdapter.notifyDataSetChanged();
                            RequestBlood requestBlood = null;

                            for (DataSnapshot snap : snapshot.getChildren()) {
                                for (DataSnapshot snap1 : snap.getChildren()) {


                                    requestBlood = snap1.getValue(RequestBlood.class);
//                                (String bloodgrp, String patientName, String patientNumber, String patientEmail, String hospitalName, String pinCode, String requestReason, String requestStatus
                                    RequestBlood request = new RequestBlood(requestBlood.getBloodgrp(), requestBlood.getPatientName(), requestBlood.getPatientNumber(), requestBlood.getPatientEmail(), requestBlood.getHospitalName(), requestBlood.getPinCode(), requestBlood.getRequestReason(), requestBlood.getRequestStatus());
                                    requestList.add(request);
                                }
                                if (requestBlood == null) {
                                    Toast.makeText(check_requests.this, "No Result", Toast.LENGTH_SHORT).show();
                                }
                                bloodRequestAdapter.notifyDataSetChanged();
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    //Functions for making background blur
    @Override
    protected void onStart() {
        super.onStart();
        blurLayout = findViewById(R.id.blurLayout);
        blurLayout.startBlur();
    }

    //FUnctions for making background blur
    @Override
    protected void onStop() {
        blurLayout.pauseBlur();
        super.onStop();
    }
}