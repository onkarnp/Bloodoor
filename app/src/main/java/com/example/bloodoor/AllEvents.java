package com.example.bloodoor;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodoor.adapter.MyAdapter2;
import com.example.bloodoor.models.Events;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class AllEvents extends AppCompatActivity {
    private ListView listView;
    private EditText bloodBankName;
    RecyclerView recyclerView;
    MyAdapter2 myAdapter;
    ArrayList<Events> list;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all_events);

        Calendar cal = Calendar.getInstance();
        recyclerView = findViewById(R.id.allEventList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new MyAdapter2(this, list);
        recyclerView.setAdapter(myAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events");
        mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list.clear();
                for(DataSnapshot snapshot:datasnapshot.getChildren()) {
                    for(DataSnapshot snap:snapshot.getChildren()) {
                        String key=snap.getKey();
                        if (key.equals(userID)) {
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

    //Function to show error message when input is not in correct format
    public void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}

