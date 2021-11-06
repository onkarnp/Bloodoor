package com.example.bloodoor;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list.clear();
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    User info = snapshot.getValue(User.class);
                    String s = "FullName : " + info.getFullName() + "\nHome Address : " + info.getHomeAddress() + "\nMobile No. : " + info.getMobileNo() + "\nEmail : " + info.getEmail() +
                            "\nCity : " + info.getCity() + "\nDoB : " + info.getDate() + "\nBlood Group : " + info.getBloodgrp() + "\nGender : " + info.getGender();
                    list.add(info);
                }
                myAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
