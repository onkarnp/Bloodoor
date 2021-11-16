package com.example.bloodoor;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodoor.adapter.MyAdapter1;
import com.example.bloodoor.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class allUsers extends AppCompatActivity {
    private ListView listView;
    RecyclerView recyclerView;
    MyAdapter1 myAdapter;
    ArrayList<User> list;
    FirebaseDatabase root;
    DatabaseReference ref;
    EditText pin_code;
    Button search_users;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all_users);
        recyclerView = findViewById(R.id.userlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<User>();
        myAdapter = new MyAdapter1(this, list);
        recyclerView.setAdapter(myAdapter);
        pin_code = findViewById(R.id.pin_code);
        search_users = (Button) findViewById(R.id.search_users);

        search_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin = pin_code.getText().toString();
                if(pin.isEmpty()){
                    ref = FirebaseDatabase.getInstance().getReference().child("users");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            list.clear();
                            for (DataSnapshot bloodgrp : dataSnapshot.getChildren()) {
                                for (DataSnapshot pincode : bloodgrp.getChildren()) {
                                    for (DataSnapshot user : pincode.getChildren()) {
                                        User info = user.getValue(User.class);
                                        list.add(info);
                                    }
                                }
                            }
                            myAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Getting Post failed, log a message
                            Toast.makeText(allUsers.this, "Task Failed...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    ref = FirebaseDatabase.getInstance().getReference().child("allusers");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            list.clear();
                            for (DataSnapshot user : dataSnapshot.getChildren()) {
                                        User info = user.getValue(User.class);
                                        if(info.getPinCode().equals(pin)){
                                            list.add(info);
                                        }
                            }
                            myAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Getting Post failed, log a message
                            Toast.makeText(allUsers.this, "Task Failed...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                list.clear();
//                for (DataSnapshot bloodgrp : dataSnapshot.getChildren()) {
//                    for (DataSnapshot pincode : bloodgrp.getChildren()) {
//                        for (DataSnapshot user : pincode.getChildren()) {
//                            User info = user.getValue(User.class);
//                            list.add(info);
//                        }
//                    }
//                }
//                myAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Toast.makeText(allUsers.this, "Task Failed...", Toast.LENGTH_SHORT).show();
//            }
//        };

    }
}
