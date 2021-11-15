package com.example.bloodoor;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodoor.adapter.MyAdapter;
import com.example.bloodoor.models.Events;
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
    EditText pin_code;
    Button search_banks;
    ArrayList<bloodBankHelperClass> list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all_blood_banks);

        pin_code = findViewById(R.id.pin_code);
        search_banks = (Button) findViewById(R.id.search_banks);

        recyclerView = findViewById(R.id.userlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<bloodBankHelperClass>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);


        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("BloodBanks");

        search_banks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin = pin_code.getText().toString();
                if(!pin.isEmpty()){
                    root.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            list.clear();
                            for (DataSnapshot pincode : dataSnapshot.getChildren()) {
                                for (DataSnapshot user : pincode.getChildren()) {
                                    bloodBankHelperClass info = user.getValue(bloodBankHelperClass.class);
                                    if(info.getbbPinCode().equals(pin))
                                        list.add(info);
                                }
                            }
                            myAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(allBloodBanks.this, "Task Failed...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    root.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            list.clear();
                            for (DataSnapshot pincode : dataSnapshot.getChildren()) {
                                for (DataSnapshot user : pincode.getChildren()) {
                                    bloodBankHelperClass info = user.getValue(bloodBankHelperClass.class);
                                    list.add(info);
                                }
                            }
                            myAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(allBloodBanks.this, "Task Failed...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}
