package com.example.bloodoor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodoor.R;
import com.example.bloodoor.models.RequestBlood;
import com.example.bloodoor.sendEmail.userMailApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class BloodRequestAdapter extends RecyclerView.Adapter<BloodRequestAdapter.MyViewHolder>{

    Context context;
    ArrayList<RequestBlood> list;
    FirebaseAuth mAuth;

    public BloodRequestAdapter(Context context, ArrayList<RequestBlood> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BloodRequestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.request_item,parent,false);
        return new BloodRequestAdapter.MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BloodRequestAdapter.MyViewHolder holder, int position) {
        RequestBlood requestBlood = list.get(position);
        holder.requestBloodgrp.setText(requestBlood.getBloodgrp());
        if(requestBlood.getRequestStatus().equals("Pending")) {
            holder.requestStatus.setText(requestBlood.getRequestStatus());
            holder.requestStatus.setTextColor(android.graphics.Color.RED);
        }
        else{
            holder.requestStatus.setText(requestBlood.getRequestStatus());
            holder.requestStatus.setTextColor(android.graphics.Color.GREEN);
        }
        holder.patientName.setText(requestBlood.getPatientName());
//        holder.patientNumber.setText(requestBlood.getPatientNumber());
//        holder.patientEmail.setText(requestBlood.getPatientEmail());
        holder.hospitalName.setText(requestBlood.getHospitalName()+", "+requestBlood.getPinCode());
//        holder.pinCode.setText(requestBlood.getPinCode());
        holder.requestReason.setText(requestBlood.getRequestReason());

        final String nameOFReceiver = requestBlood.getPatientName();
        final String idOfReceiver = requestBlood.getPatientEmail();

        final String patientNumber = requestBlood.getPatientNumber();
        final String patientEmail = requestBlood.getPatientEmail();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Request For Blood!!");
                alertDialogBuilder.setIcon(R.drawable.ic_flag);
                alertDialogBuilder.setMessage("Change status ?");
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setPositiveButton("Fulfilled", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Function to change status in the database
                        setRequestStatus(holder, "Fulfilled");
                    }
                }).setNegativeButton("Pending", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Function to change status in the database
                        setRequestStatus(holder, "Pending");
                    }
                });
                alertDialogBuilder.show();
            }
        });


        holder.make_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+patientNumber));
                context.startActivity(intent);
            }
        });


        holder.send_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Send Email")
                        .setMessage("Send Email to " + requestBlood.getPatientName() + "?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                                        .child("ALLBloodbanks").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String nameOfBank = snapshot.child("name").getValue().toString();
                                        String nameOfSender = snapshot.child("handlerName").getValue().toString();
                                        String email = snapshot.child("email").getValue().toString();
                                        String address = snapshot.child("address").getValue().toString();
                                        String phoneNo = snapshot.child("phoneNo").getValue().toString();
                                        String pinCode = snapshot.child("bbPinCode").getValue().toString();
                                        String mEmail = idOfReceiver;
                                        String mSubject = "BLOOD REQUEST";
                                        String mMessage = "Hello " + nameOFReceiver + ",\n" + nameOfSender
                                                + " is ready to fulfill your blood request. \n\nHere's his/her details :\n"
                                                + "Name : " + nameOfSender + "\n"
                                                + "Phone Number : " + phoneNo + "\n"
                                                + "Email : " + email + "\n"
                                                + "Address : " + address + "\n"
                                                + "City Pin Code : " + pinCode + "\n"
                                                + "Kindly reach out to him/her. \nThank You!\n\n"
                                                + "With Regards,\nTeam BlooDoor \nDONATE BLOOD, SAVE LIFE :)";

                                        userMailApi userMailApi = new userMailApi(context, mEmail, mSubject, mMessage);
                                        userMailApi.execute();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(context, "Task Cancelled...", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }


    public void setRequestStatus(MyViewHolder holder, String s) {
        String name = holder.patientName.getText().toString();
//        String number = holder.patientNumber.getText().toString();
        final int[] flag = {0};
        mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("bloodRequests");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    for(DataSnapshot dataSnapshot : task.getResult().getChildren()){
                        for(DataSnapshot snap: dataSnapshot.getChildren()){
                            String n = String.valueOf(snap.child("patientName").getValue());
                            String p = String.valueOf(snap.child("pinCode").getValue());
                            if(name.equals(n) && Objects.equals(snap.getKey(), userID)) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("bloodRequests");
                                ref.child(p).child(userID).child("requestStatus").setValue(s);
                                flag[0] = 1;
                                Toast.makeText(context, "Status changed to " + s, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    if(flag[0] == 0) {
                        Toast.makeText(context, "This is not your request", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(context, "Couldn't update status", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView requestBloodgrp, requestStatus, patientName, patientNumber, patientEmail, hospitalName, pinCode, requestReason;
        CardView make_call, send_mail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            requestBloodgrp = itemView.findViewById(R.id.requestBloodgrp);
            requestStatus = itemView.findViewById(R.id.requestStatus);
            patientName = itemView.findViewById(R.id.patientName);
            patientNumber = itemView.findViewById(R.id.patientNumber);
            patientEmail = itemView.findViewById(R.id.patientEmail);
            hospitalName = itemView.findViewById(R.id.hospitalName);
            pinCode = itemView.findViewById(R.id.pinCode);
            requestReason = itemView.findViewById(R.id.requestReason);
            make_call = itemView.findViewById(R.id.make_call);
            send_mail = itemView.findViewById(R.id.send_mail);
        }
    }
}