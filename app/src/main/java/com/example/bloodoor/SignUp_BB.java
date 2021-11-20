package com.example.bloodoor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.bloodoor.models.bloodBankHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.alterac.blurkit.BlurLayout;

public class SignUp_BB extends AppCompatActivity {

    BlurLayout blurLayout;
    CardView signupcard, signincard;
    private EditText name, handlerName, mobileNo, phoneNo, email, address, bbPinCode;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference ref;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_sign_up__b_b);

        //Hooks
        name = findViewById(R.id.enterBloodBankName);
        handlerName = findViewById(R.id.enterFullName);
        mobileNo = findViewById(R.id.mobileNumber);
        phoneNo = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.emailID);
        address = findViewById(R.id.bankAddress);
        bbPinCode = findViewById(R.id.bb_pin_code);
        signupcard = (CardView) findViewById(R.id.signupcard);
        signincard = (CardView) findViewById(R.id.signincard);
        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            loadingBar.setMessage("Fetching Your Data");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            String userID = mAuth.getCurrentUser().getUid();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ALLBloodbanks");
            ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        for(DataSnapshot dataSnapshot : task.getResult().getChildren()){
                            if(userID.equals(dataSnapshot.getKey())){
                                loadingBar.dismiss();
                                Toast.makeText(SignUp_BB.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp_BB.this,Homepage_BB.class));
                                finish();
                            }
                        }
                    }
                    else{
                        loadingBar.dismiss();
                        Toast.makeText(SignUp_BB.this, "Something went wrong :(\n Please try again", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp_BB.this,options.class));
                    }
                }
            });
        }

        signupcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regName = name.getText().toString();
                String regHandlerName = handlerName.getText().toString();
                String regMobileNo = mobileNo.getText().toString();
                String regPhoneNo = phoneNo.getText().toString();
                String regEmail = email.getText().toString();
                String regAddress = address.getText().toString();
                String regPinCode = bbPinCode.getText().toString();

                if(regName.isEmpty() || regName.length()<5)
                {
                    showError(name, "Please enter correct name");
                }
                else if(regHandlerName.isEmpty() || regHandlerName.length()<5)
                {
                    showError(handlerName, "Please enter correct name");
                }
                else if (regMobileNo.length()!=10)
                {
                    showError(mobileNo, "Please enter valid mobile number");
                }
                else if (regPhoneNo.length()!=10)
                {
                    showError(phoneNo, "Please enter valid 10 digit number");
                }
                else if(regEmail.isEmpty() && !isEmailValid(regEmail))
                {
                    showError(email, "Please enter valid email");
                }
                else if (regAddress.isEmpty() || regAddress.length()<5)
                {
                    showError(address, "Please enter valid address");
                }
                else if (regPinCode.length()!=6)
                {
                    showError(bbPinCode, "Please enter a valid pin code");
                }
                else{
                    createBloodBank();
                }
            }

            public void createBloodBank(){
                String regName = name.getText().toString();
                String regHandlerName = handlerName.getText().toString();
                String regMobileNo = mobileNo.getText().toString();
                String regPhoneNo = phoneNo.getText().toString();
                String regEmail = email.getText().toString();
                String regAddress = address.getText().toString();
                String regPinCode = bbPinCode.getText().toString();
                bloodBankHelperClass helper = new bloodBankHelperClass(regName, regHandlerName, regMobileNo,
                        regPhoneNo, regEmail, regAddress, regPinCode);
                if (!mobileNo.getText().toString().trim().isEmpty()) {
                    if ((mobileNo.getText().toString().trim()).length() == 10) {

                        signupcard.setVisibility(View.INVISIBLE);
//                      loadingBar.setTitle("Create Account");
                        loadingBar.setMessage("Signing up");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + mobileNo.getText().toString(),
                                90,
                                TimeUnit.SECONDS,
                                SignUp_BB.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                        signupcard.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        signupcard.setVisibility(View.VISIBLE);
                                        loadingBar.dismiss();
                                        Toast.makeText(SignUp_BB.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                        signupcard.setVisibility(View.INVISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), verifyotp_BB.class);
                                        bloodBankHelperClass helper = new bloodBankHelperClass(regName, regHandlerName, regMobileNo,
                                                regPhoneNo, regEmail, regAddress, regPinCode);
                                        intent.putExtra("mobile", mobileNo.getText().toString());
                                        intent.putExtra("backendotp", backendotp);
                                        intent.putExtra("Helper", helper);
                                        intent.putExtra("from","SignUp_BB");
                                        loadingBar.dismiss();
                                        startActivity(intent);

                                    }
                                }
                        );

//                        Intent intent = new Intent(getApplicationContext(),verifyotp.class);
//                        intent.putExtra("mobile",entermobilenumber.getText().toString());
//                        startActivity(intent);

                    } else {
                        signupcard.setVisibility(View.VISIBLE);
                        loadingBar.dismiss();
                        Toast.makeText(SignUp_BB.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    signupcard.setVisibility(View.VISIBLE);
                    loadingBar.dismiss();
                    Toast.makeText(SignUp_BB.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signincard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), enternumberBB.class);
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

    //Functions for making background blur
    @Override
    protected void onStop() {
        blurLayout.pauseBlur();
        super.onStop();
    }

    //Function to show error message when input is not in correct format
    public void showError(EditText input, String s)
    {
        input.setError(s);
        input.requestFocus();
    }

    //Function to check whether an email enters is valid or not
    public boolean isEmailValid(String email)
    {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9]+(\\.[_A-Za-z0-9]+)*@[_A-Za-z0-9]+(\\.[_A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

