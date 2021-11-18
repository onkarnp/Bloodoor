package com.example.bloodoor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodoor.R;
import com.example.bloodoor.models.Events;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {

    Context context;
    ArrayList<Events> list;


    public MyAdapter2(Context context, ArrayList<Events> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_all_events, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Events order = list.get(position);
        holder.bloodBankName.setText(order.getBankName());
        holder.eventName.setText(order.getName());
        holder.eventStartDate.setText(order.getStartDate() + " to " + order.getEndData());
//        holder.eventEndDate.setText(order.getEndData());
        holder.eventDescription.setText(order.getDescription());

        String s = order.getDuration();
        String[] split = s.split("::");
        String firstSubString = split[0];
        String secondSubString = split[1];
        holder.eventDuration.setText(firstSubString + " to " + secondSubString + " (Daytime)");
        holder.eventVenue.setText(order.getVenue() + ", " + order.getPin());
        holder.eventStatus.setText(order.getStatus());

        if (order.getStatus().equals("Live")) {
            holder.eventStatus.setText(order.getStatus());
            holder.eventStatus.setTextColor(android.graphics.Color.GREEN);
        } else {
            holder.eventStatus.setText(order.getStatus());
            holder.eventStatus.setTextColor(android.graphics.Color.RED);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Event Details..!!");
                alertDialogBuilder.setIcon(R.drawable.ic_flag);
                alertDialogBuilder.setMessage("Change Event status ?");
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setPositiveButton("Over", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ///Function to change status in the database
                        setOrderStatus(holder, "Over");
                        holder.eventStatus.setText("Over");
                        Toast.makeText(context, "Status changed to Over.", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("Upcoming/Live", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("Event Details..!!");
                        alertDialogBuilder.setIcon(R.drawable.ic_flag);
                        alertDialogBuilder.setMessage("Change Event status ?");
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setPositiveButton("Live", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Function  to change status in the database
                                setOrderStatus(holder, "Live");
                                holder.eventStatus.setText("Live");
                                Toast.makeText(context, "Status changed to Live.", Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("Upcoming", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Function  to change status in the database
                                setOrderStatus(holder, "Upcoming");
                                holder.eventStatus.setText("Upcoming");
                                Toast.makeText(context, "Status changed to Upcoming.", Toast.LENGTH_LONG).show();
                            }
                        });
                        alertDialogBuilder.show();
                    }
                });
                alertDialogBuilder.show();
            }
        });
    }

    public void setOrderStatus(MyViewHolder holder, String s) {
        String bbn = holder.bloodBankName.getText().toString();
        String sdt = holder.eventStartDate.getText().toString();
        String[] split = sdt.split(" to ");
        String firstSubString = split[0];
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events");

        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            String n = String.valueOf(snap.child("bankName").getValue());
                            String sd = String.valueOf(snap.child("startDate").getValue());
                            if (bbn.equals(n) && firstSubString.equals(sd)) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Events");
                                ref.child(sd).child(snap.getKey()).child("status").setValue(s);
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(context, "Couldn't update status", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView bloodBankName, eventName, eventStartDate, eventEndDate, eventDescription, eventDuration, eventVenue, eventStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bloodBankName = itemView.findViewById(R.id.showBloodBankName);
            eventName = itemView.findViewById(R.id.showEventName);
            eventStartDate = itemView.findViewById(R.id.showEventStartDate);
//            eventEndDate = itemView.findViewById(R.id.showEventEndDate);
            eventDescription = itemView.findViewById(R.id.showEventDescription);
            eventDuration = itemView.findViewById(R.id.showEventDuration);
            eventVenue = itemView.findViewById(R.id.showEventVenue);
            eventStatus = itemView.findViewById(R.id.showEventStatus);
        }
    }

}

