package com.example.bloodoor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodoor.models.BloodAvailable;
import com.example.bloodoor.models.User;
import com.example.bloodoor.models.bloodBankHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.alterac.blurkit.BlurLayout;

public class available_blood extends AppCompatActivity {

    BlurLayout blurLayout;
    TextView bankName;
    EditText o_pos, o_neg, a_pos, a_neg, b_pos, b_neg, AB_pos, AB_neg;
    Button updateButton;
    private String text_o_pos, text_o_neg, text_a_pos, text_a_neg, text_b_neg, text_b_pos, text_AB_pos, text_AB_neg;
    private ProgressDialog loadingBar;

    FirebaseDatabase rootNode;
    FirebaseAuth mAuth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_available_blood);
        BloodAvailable BloodAvailable = (BloodAvailable) getIntent().getSerializableExtra("BloodAvailable");
        bankName = findViewById(R.id.bankName);
        o_pos = findViewById(R.id.o_pos);
        o_neg = findViewById(R.id.o_neg);
        a_pos = findViewById(R.id.a_pos);
        a_neg = findViewById(R.id.a_neg);
        b_pos = findViewById(R.id.b_pos);
        b_neg = findViewById(R.id.b_neg);
        AB_pos = findViewById(R.id.AB_pos);
        AB_neg = findViewById(R.id.AB_neg);
        updateButton = findViewById(R.id.updateButton);
        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            getBloodBankName();
            getPreviousData();
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                mAuth = FirebaseAuth.getInstance();
                String O_pos = o_pos.getText().toString();
                String O_neg = o_neg.getText().toString();
                String A_pos = a_pos.getText().toString();
                String A_neg = a_neg.getText().toString();
                String B_pos = b_pos.getText().toString();
                String B_neg = b_neg.getText().toString();
                String ab_pos = AB_pos.getText().toString();
                String ab_neg = AB_neg.getText().toString();

                if(O_pos.isEmpty() )
                {
                    showError(o_pos, "This field can't be empty");
                }
                else if(O_neg.isEmpty() )
                {
                    showError(o_neg, "This field can't be empty");
                }
                else if(A_pos.isEmpty() )
                {
                    showError(a_pos, "This field can't be empty");
                }
                else if(A_neg.isEmpty() )
                {
                    showError(a_neg, "This field can't be empty");
                }
                else if(B_pos.isEmpty() )
                {
                    showError(b_pos, "This field can't be empty");
                }
                else if(B_neg.isEmpty() )
                {
                    showError(b_neg, "This field can't be empty");
                }
                else if(ab_pos.isEmpty() )
                {
                    showError(AB_pos, "This field can't be empty");
                }
                else if(ab_neg.isEmpty() )
                {
                    showError(AB_neg, "This field can't be empty");
                }
                else{
                    updateBloodAvailability();
                }
            }

            private void updateBloodAvailability() {
                rootNode = FirebaseDatabase.getInstance();
                mAuth = FirebaseAuth.getInstance();
                String BankName = bankName.getText().toString();
                String userID = mAuth.getCurrentUser().getUid();
                String O_pos = o_pos.getText().toString();
                String O_neg = o_neg.getText().toString();
                String A_pos = a_pos.getText().toString();
                String A_neg = a_neg.getText().toString();
                String B_pos = b_pos.getText().toString();
                String B_neg = b_neg.getText().toString();
                String ab_pos = AB_pos.getText().toString();
                String ab_neg = AB_neg.getText().toString();
                ref = rootNode.getReference("BloodAvailability");
                BloodAvailable bloodAvailable = new BloodAvailable(BankName, O_pos, O_neg, A_pos, A_neg, B_pos, B_neg, ab_pos, ab_neg);
                ref.child(userID).setValue(bloodAvailable);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(available_blood.this);
                alertDialogBuilder.setIcon(R.drawable.ic_exit);
                alertDialogBuilder.setMessage("Data Updated...!!");
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(),Homepage_BB.class);
                        startActivity(intent);
                        Toast.makeText(available_blood.this,"Info updated successfully",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
                alertDialogBuilder.show();
            }
        });
    }


    //Function to get blood bank name
    private void getBloodBankName() {
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("ALLBloodbanks");
        mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bloodBankHelperClass profileUser = snapshot.getValue(bloodBankHelperClass.class);
                if (profileUser != null){
                    String s = profileUser.getName();
                    bankName.setText(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(available_blood.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Function to get previous data
    private void getPreviousData() {
        o_pos = findViewById(R.id.o_pos);
        o_neg = findViewById(R.id.o_neg);
        a_pos = findViewById(R.id.a_pos);
        a_neg = findViewById(R.id.a_neg);
        b_pos = findViewById(R.id.b_pos);
        b_neg = findViewById(R.id.b_neg);
        AB_pos = findViewById(R.id.AB_pos);
        AB_neg = findViewById(R.id.AB_neg);
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("BloodAvailability");
        mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();

        referenceProfile.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    BloodAvailable profileUser = snapshot.getValue(BloodAvailable.class);
                    if(profileUser != null){
                        text_o_pos = profileUser.getO_pos();
                        String s = profileUser.getO_pos();
                        text_o_neg = profileUser.getO_neg();
                        text_a_pos = profileUser.getA_pos();
                        text_a_neg = profileUser.getA_neg();
                        text_b_pos = profileUser.getB_pos();
                        text_b_neg = profileUser.getB_neg();
                        text_AB_pos = profileUser.getAB_pos();
                        text_AB_neg = profileUser.getAB_neg();

                        o_pos.setText(text_o_pos);
                        o_neg.setText(text_o_neg);
                        a_pos.setText(text_a_pos);
                        a_neg.setText(text_a_neg);
                        b_pos.setText(text_b_pos);
                        b_neg.setText(text_b_neg);
                        AB_pos.setText(text_AB_pos);
                        AB_neg.setText(text_AB_neg);
                    }
                else{
                    o_pos.setText("0");
                    o_neg.setText("0");
                    a_pos.setText("0");
                    a_neg.setText("0");
                    b_pos.setText("0");
                    b_neg.setText("0");
                    AB_pos.setText("0");
                    AB_neg.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(available_blood.this, "Unable to fetch previous data", Toast.LENGTH_SHORT).show();
            }
        });


//        referenceProfile.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.hasChild(userID)){
//                    BloodAvailable profileUser = snapshot.getValue(BloodAvailable.class);
//                    if(profileUser != null){
//                        text_o_pos = profileUser.getO_pos();
//                        text_o_neg = profileUser.getO_neg();
//                        text_a_pos = profileUser.getA_pos();
//                        text_a_neg = profileUser.getA_neg();
//                        text_b_pos = profileUser.getB_pos();
//                        text_b_neg = profileUser.getB_neg();
//                        text_AB_pos = profileUser.getAB_pos();
//                        text_AB_neg = profileUser.getAB_neg();
//
//                        o_pos.setText(text_o_pos);
//                        o_neg.setText(text_o_neg);
//                        a_pos.setText(text_a_pos);
//                        a_neg.setText(text_a_neg);
//                        b_pos.setText(text_b_pos);
//                        b_neg.setText(text_b_neg);
//                        AB_pos.setText(text_AB_pos);
//                        AB_neg.setText(text_AB_neg);
//                    }
//                }
//                else{
//                    o_pos.setText("0");
//                    o_neg.setText("0");
//                    a_pos.setText("0");
//                    a_neg.setText("0");
//                    b_pos.setText("0");
//                    b_neg.setText("0");
//                    AB_pos.setText("0");
//                    AB_neg.setText("0");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(available_blood.this, "Unable to fetch previous data", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    //Functions for making background blur
    @Override
    protected void onStart() {
        super.onStart();
        blurLayout = findViewById(R.id.blurLayout);
        blurLayout.startBlur();
    }

    //Functions for making background blur
    @Override
    protected void onStop() {
        blurLayout.pauseBlur();
        blurLayout = findViewById(R.id.blurLayout);
        super.onStop();
    }

    //Function to show error message when input is not in correct format
    public void showError(EditText input, String s)
    {
        input.setError(s);
        input.requestFocus();
    }
}