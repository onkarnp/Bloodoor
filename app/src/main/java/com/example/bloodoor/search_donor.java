package com.example.bloodoor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bloodoor.adapter.MyAdapter1;
import com.example.bloodoor.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.alterac.blurkit.BlurLayout;

public class search_donor extends AppCompatActivity {

    FirebaseDatabase database;
    ArrayList<User> userList;

    BlurLayout blurLayout;
    private EditText pin_code;
    Button DonorSearchbtn;
    AutoCompleteTextView bloodgrp;
    private ListView listView;
    RecyclerView recyclerView;
    MyAdapter1 myAdapter;
    ArrayList<User> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_search_donor);
        recyclerView = findViewById(R.id.FindDonorRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<User>();
        myAdapter = new MyAdapter1(this, list);
        recyclerView.setAdapter(myAdapter);
        pin_code = (EditText) findViewById(R.id.pincode);
        DonorSearchbtn = (Button) findViewById(R.id.DonorSearchbtn);

        bloodgrp = findViewById(R.id.autoCompleteTextView);
        String[] option = {"Select", "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.options_item, option);
        bloodgrp.setText(arrayAdapter.getItem(0).toString(), false); //to make default value...
        bloodgrp.setAdapter(arrayAdapter);


        database = FirebaseDatabase.getInstance();

        if(list == null)
            list = new ArrayList<User>();

        MyAdapter1 adapter = new MyAdapter1(this, list);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DonorSearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bloodgrp.getText().toString().equals("Select") || pin_code.getText().toString().isEmpty()){
                    Toast.makeText(search_donor.this, "Select blood group and pincode", Toast.LENGTH_SHORT).show();
                    return;
                }
                String pinCode = pin_code.getText().toString();
                String bloodGrp = bloodgrp.getText().toString();

                database.getReference().child("users").child(bloodGrp).child(pinCode).orderByValue().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        adapter.notifyDataSetChanged();
                        User user = null;
//                        (String fullName, String homeAddress, String mobileNo, String email, String pin_code, String date, String bloodgrp, String gender)
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            user = snap.getValue(User.class);
                            User donor = new User(user.getFullName(),user.getHomeAddress(),user.getMobileNo(),user.getEmail(),user.getPinCode(),user.getDate(),user.getBloodgrp(),user.getGender());
                            list.add(donor);
                        }
                        if(user == null) {
                            Toast.makeText(search_donor.this, "No Result", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(search_donor.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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