package com.example.bloodoor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodoor.models.bloodBankHelperClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.alterac.blurkit.BlurLayout;

public class profileBB extends AppCompatActivity {

    private TextView bloodBankname, holdername, mobileNo, phoneNo, email, bandLocationAdd, city;
    private TextView fullnameLabel, usernameLabel;
    private DatabaseReference reference;
    private FirebaseUser bloodBank;
    private String bloodBankID;
    private Button updateProfileButton;
    private ProgressDialog loadingBar;
    BlurLayout blurLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_profile_b_b);

        reference = FirebaseDatabase.getInstance().getReference("ALLBloodbanks");
        bloodBank = FirebaseAuth.getInstance().getCurrentUser();
        bloodBankID = bloodBank.getUid();

        bloodBankname = findViewById(R.id.bb_name);
        holdername = findViewById(R.id.bb_holder_name);
        mobileNo = findViewById(R.id.bb_mobileNo);
        phoneNo = findViewById(R.id.bb_phoneNo);
        email = findViewById(R.id.bb_emailId);
        bandLocationAdd = findViewById(R.id.bb_address);
        city = findViewById(R.id.bb_city);
        fullnameLabel = findViewById(R.id.blood_bank_name);
        usernameLabel = findViewById(R.id.holder_name);
        updateProfileButton = (Button) findViewById(R.id.btn_update);
        loadingBar = new ProgressDialog(this);
        loadingBar.setTitle("Blood Bank Profile");
        loadingBar.setMessage("Fetching blood bank profile details, "+ "Please wait for a moment...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        reference.child(bloodBankID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bloodBankHelperClass profileBB = snapshot.getValue(bloodBankHelperClass.class);
                if(profileBB != null){
                    String bb_bloodBankname = profileBB.getName();
                    String bb_holdername = profileBB.getHandlerName();
                    String bb_mobileNo = profileBB.getMobileNo();
                    String bb_phoneNo = profileBB.getPhoneNo();
                    String bb_email = profileBB.getEmail();
                    String bb_bandLocationAdd = profileBB.getAddress();
                    String bb_city = profileBB.getbbPinCode();
                    loadingBar.dismiss();

                    fullnameLabel.setText(bb_bloodBankname);
                    usernameLabel.setText(bb_holdername);
                    bloodBankname.setText(bb_bloodBankname);
                    holdername.setText(bb_holdername);
                    mobileNo.setText(bb_mobileNo);
                    phoneNo.setText(bb_phoneNo);
                    email.setText(bb_email);
                    bandLocationAdd.setText(bb_bandLocationAdd);
                    city.setText(bb_city);
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingBar.dismiss();
                Toast.makeText(profileBB.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
            }
        });

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),profileUpdate_BB.class);
                startActivity(intent);
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