package com.example.bloodoor;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodoor.adapter.MyAdapter;
import com.example.bloodoor.models.bloodBankHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class allBloodBanks extends AppCompatActivity {
    private ListView listView;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<bloodBankHelperClass> list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all_blood_banks);
        recyclerView = findViewById(R.id.userlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<bloodBankHelperClass>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);


        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("BloodBanks");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot pincode : dataSnapshot.getChildren()) {
                    for (DataSnapshot user : pincode.getChildren()) {
                        bloodBankHelperClass info = user.getValue(bloodBankHelperClass.class);
                        String s = "Name : " + info.getName() + "\nHandler Name : " + info.getHandlerName() + "\nMobile No. : " + info.getMobileNo() + "\nPhone No. : " + info.getPhoneNo()
                                + "\nEmail : " + info.getEmail() + "\nAddress : " + info.getAddress() + "\nPin Code : " + info.getbbPinCode();
                        list.add(info);
                    }
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(allBloodBanks.this, "Task Failed...", Toast.LENGTH_SHORT).show();
            }
        };
        root.addValueEventListener(postListener);
    }
}
