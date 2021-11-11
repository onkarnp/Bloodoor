package com.example.bloodoor;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Events order = list.get(position);
        holder.bloodBankName.setText(order.getBankName());
        holder.eventName.setText(order.getName());
        holder.eventStartDate.setText(order.getStartDate());
        holder.eventEndDate.setText(order.getEndData());
        holder.eventDescription.setText(order.getDescription());
        holder.eventDuration.setText(order.getDuration());
        holder.eventVenue.setText(order.getVenue());
        holder.eventStatus.setText(order.getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Event Details..!!");
                alertDialogBuilder.setIcon(R.drawable.ic_flag);
                alertDialogBuilder.setMessage("Change Event status ?");
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ///Function to change status in the database
                        setOrderStatus(holder, "Done");
                        holder.eventStatus.setText("Done");

                        Toast.makeText(context, "Status changed to Done.", Toast.LENGTH_LONG).show();
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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        String n = String.valueOf(snap.child("bankName").getValue());
                        String d = String.valueOf(snap.child("startDate").getValue());
                        if (bbn.equals(n) && sdt.equals(d)) {
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Events");
                            if (s.equals("Done")) {
                                ref.child(d).child(snap.getKey()).child("status").setValue("Done");
                            } else if(s.equals("Live")) {
                                ref.child(d).child(snap.getKey()).child("status").setValue("Live");
                            } else{
                                ref.child(d).child(snap.getKey()).child("status").setValue("Upcoming");
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
            eventEndDate = itemView.findViewById(R.id.showEventEndDate);
            eventDescription = itemView.findViewById(R.id.showEventDescription);
            eventDuration = itemView.findViewById(R.id.showEventDuration);
            eventVenue = itemView.findViewById(R.id.showEventVenue);
            eventStatus = itemView.findViewById(R.id.showEventStatus);
        }
    }

}

