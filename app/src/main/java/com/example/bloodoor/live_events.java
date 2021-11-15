package com.example.bloodoor;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodoor.adapter.MyAdapter3;
import com.example.bloodoor.models.Events;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.alterac.blurkit.BlurLayout;

public class live_events extends AppCompatActivity {

    BlurLayout blurLayout;
    RecyclerView recyclerView;
    MyAdapter3 myAdapter;
    EditText pin_code;
    Button search_events;
    ArrayList<Events> list;
    private FirebaseAuth mAuth;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_live_events);

        pin_code = findViewById(R.id.pin_code);
        search_events = (Button) findViewById(R.id.search_events);
        recyclerView = findViewById(R.id.liveEventList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new MyAdapter3(this, list);
        recyclerView.setAdapter(myAdapter);

        mAuth = FirebaseAuth.getInstance();


        search_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin = pin_code.getText().toString();
                if(!pin.isEmpty()){
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                            list.clear();
                            for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    if((String.valueOf(snap.child("pin").getValue()).equals(pin)))
                                    {
                                        if ((String.valueOf(snap.child("status").getValue()).equals("Upcoming")) | (String.valueOf(snap.child("status").getValue()).equals("Live"))) {
                                            Events info = snap.getValue(Events.class);
                                            list.add(info);
                                        }
                                    }
                                }
                            }
                            myAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                            list.clear();
                            for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    if ((String.valueOf(snap.child("status").getValue()).equals("Upcoming")) | (String.valueOf(snap.child("status").getValue()).equals("Live"))) {
                                        Events info = snap.getValue(Events.class);
                                        list.add(info);
                                    }
                                }
                            }
                            myAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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