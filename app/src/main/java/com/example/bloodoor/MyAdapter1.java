package com.example.bloodoor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder>{

    Context context;
    ArrayList<User> list;

    public MyAdapter1(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.users_data,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = list.get(position);
        holder.fullName.setText(user.getFullName());
        holder.mobileNo.setText(user.getMobileNo());
        holder.email.setText(user.getEmail());
        holder.homeAddress.setText(user.getHomeAddress());
        holder.pin_code.setText(user.getPinCode());
        holder.dob.setText(user.getDate());
        holder.gender.setText(user.getGender());
        holder.bloodgrp.setText(user.getBloodgrp());

        final String nameOFReceiver = user.getFullName();
        final String idOfReceiver = user.getEmail();

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Send Email")
                        .setMessage("Send Email to " + user.getFullName() + "?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                                        .child("BloodBanks").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String nameOfBank = snapshot.child("name").getValue().toString();
                                        String nameOfSender = snapshot.child("handlerName").getValue().toString();
                                        String email = snapshot.child("email").getValue().toString();
                                        String address = snapshot.child("address").getValue().toString();
                                        String phoneNo = snapshot.child("phoneNo").getValue().toString();
                                        String pinCode = snapshot.child("bbPinCode").getValue().toString();
                                        String mEmail = user.getEmail();
                                        String mSubject = "BLOOD REQUEST";
                                        String mMessage = "Hello " + nameOFReceiver + "," + nameOfSender + "from" + nameOfBank + ","
                                                + "Would like to Blood donation from you. Here's his/her details :\n"
                                                + "Name : " + nameOfSender + "\n"
                                                + "Phone Number : " + phoneNo + "\n"
                                                + "Email : " + email + "\n"
                                                + "Address : " + address + "\n"
                                                + "City Pin Code : " + pinCode + "\n"
                                                + "Kindly reach out to him/her. Thank You...\n"
                                                + "BlooDoor... DONATE BLOOD, SAVE LIFE :)";

                                        userMailApi userMailApi = new userMailApi(context, mEmail, mSubject, mMessage);
                                        userMailApi.execute();

                                        DatabaseReference senderRef = FirebaseDatabase.getInstance().getReference("email")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        senderRef.child(idOfReceiver).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    DatabaseReference receiverRef = FirebaseDatabase.getInstance().getReference("email")
                                                            .child(idOfReceiver);
                                                    receiverRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(true);
                                                }
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fullName, homeAddress, email, mobileNo, pin_code, dob, gender, bloodgrp;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.fullName1);
            mobileNo = itemView.findViewById(R.id.mobileNo1);
            email = itemView.findViewById(R.id.email1);
            homeAddress = itemView.findViewById(R.id.homeAddress1);
            pin_code = itemView.findViewById(R.id.pinCode1);
            dob = itemView.findViewById(R.id.dob1);
            gender = itemView.findViewById(R.id.gender1);
            bloodgrp = itemView.findViewById(R.id.bloodgrp1);
            button = itemView.findViewById(R.id.btn_email1);
        }
    }
}


