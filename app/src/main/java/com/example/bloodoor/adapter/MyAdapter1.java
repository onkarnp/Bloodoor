package com.example.bloodoor.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodoor.R;
import com.example.bloodoor.models.User;
import com.example.bloodoor.sendEmail.userMailApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder>{

    Context context;
    ArrayList<User> list;
    static Calendar calendar = Calendar.getInstance();

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = list.get(position);
        int len = user.getDate().length();
        String age = "" + (calendar.get(Calendar.YEAR) - Integer.parseInt(user.getDate().substring(len - 4)));
        holder.fullName.setText(user.getFullName());
//        holder.mobileNo.setText(user.getMobileNo());
//        holder.email.setText(user.getEmail());
        holder.homeAddress.setText(user.getHomeAddress() + ", " + user.getPinCode());
//        holder.pin_code.setText(user.getPinCode());
        holder.dob.setText(user.getGender() + ", " + age + " years");
//        holder.gender.setText(user.getGender() + ", " + age + " years");
        holder.bloodgrp.setText(user.getBloodgrp());

        final String nameOFReceiver = user.getFullName();
        final String idOfReceiver = user.getEmail();

        holder.make_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+user.getMobileNo()));
                context.startActivity(intent);
            }
        });

        holder.send_mail.setOnClickListener(new View.OnClickListener() {
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
                                                + " is in need of blood and would like a donation from you. \n\nHere's his/her details :\n"
                                                + "Name : " + nameOfSender + "\n"
                                                + "Phone Number : " + phoneNo + "\n"
                                                + "Email : " + email + "\n"
                                                + "Address : " + address + "\n"
                                                + "City Pin Code : " + pinCode + "\n"
                                                + "Kindly reach out to him/her. \nThank You!\n\n"
                                                + "With Regards,\nTeam BlooDoor\nDONATE BLOOD, SAVE LIFE :)";

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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fullName, homeAddress, email, mobileNo, pin_code, dob, gender, bloodgrp;
        Button button;
        CardView make_call, send_mail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.fullName1);
//            mobileNo = itemView.findViewById(R.id.mobileNo1);
//            email = itemView.findViewById(R.id.email1);
            homeAddress = itemView.findViewById(R.id.homeAddress1);
//            pin_code = itemView.findViewById(R.id.pinCode1);
            dob = itemView.findViewById(R.id.dob1);
//            gender = itemView.findViewById(R.id.gender1);
            bloodgrp = itemView.findViewById(R.id.bloodgrp1);
//            button = itemView.findViewById(R.id.btn_email1);
            make_call = itemView.findViewById(R.id.make_call);
            send_mail = itemView.findViewById(R.id.send_mail);
        }
    }
}


