package com.example.bloodoor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class enternumber extends AppCompatActivity {

    EditText entermobilenumber;
    Button getotpbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enternumber);

        entermobilenumber = findViewById(R.id.input_mobile_number);
        getotpbutton = findViewById(R.id.buttongetotp);

        getotpbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!entermobilenumber.getText().toString().trim().isEmpty()){
                    if ((entermobilenumber.getText().toString().trim()).length() == 10){
                        Intent intent = new Intent(getApplicationContext(),verifyotp.class);
                        intent.putExtra("mobile",entermobilenumber.getText().toString());
                        startActivity(intent);
                    }else {
                        Toast.makeText(enternumber.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(enternumber.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}