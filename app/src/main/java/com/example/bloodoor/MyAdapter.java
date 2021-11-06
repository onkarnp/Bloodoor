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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<bloodBankHelperClass> list;

    public MyAdapter(Context context, ArrayList<bloodBankHelperClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.bloodbankdata,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        bloodBankHelperClass user = list.get(position);
        holder.name.setText(user.getName());
        holder.handlerName.setText(user.getHandlerName());
        holder.mobileNo.setText(user.getMobileNo());
        holder.phoneNo.setText(user.getPhoneNo());
        holder.city.setText(user.getCity());
        holder.email.setText(user.getEmail());
        holder.address.setText(user.getAddress());
        holder.city.setText(user.getCity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, handlerName, mobileNo, phoneNo, email, address, city;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.bb_name0);;
            handlerName = itemView.findViewById(R.id.bb_holder_name0);
            mobileNo = itemView.findViewById(R.id.mobileNo0);
            phoneNo = itemView.findViewById(R.id.phoneNo0);
            email = itemView.findViewById(R.id.city0);
            address = itemView.findViewById(R.id.homeAddress0);
            city = itemView.findViewById(R.id.city0);
            button = itemView.findViewById(R.id.btn_email);
        }
    }
}


