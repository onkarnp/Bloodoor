package com.example.bloodoor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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


