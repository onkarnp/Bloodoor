package com.example.bloodoor.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodoor.models.Events;
import com.example.bloodoor.R;

import java.util.ArrayList;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyViewHolder>{

    Context context;
    ArrayList<Events> list;



    public MyAdapter3(Context context, ArrayList<Events> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_all_events,parent,false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Events event = list.get(position);
        holder.bloodBankName.setText(event.getBankName());
        holder.eventName.setText(event.getName());
        holder.eventStartDate.setText(event.getStartDate() + " to " + event.getEndData());
//        holder.eventEndDate.setText(event.getEndData());
        holder.eventDescription.setText(event.getDescription());

        String s = event.getDuration();
        String[] split = s.split("::");
        String firstSubString = split[0];
        String secondSubString = split[1];
        holder.eventDuration.setText(firstSubString + " to " + secondSubString + " (Daytime)");
        holder.eventVenue.setText(event.getVenue() + ", " + event.getPin());
        holder.eventStatus.setText(event.getStatus());

        if(event.getStatus().equals("Live")) {
            holder.eventStatus.setText(event.getStatus());
            holder.eventStatus.setTextColor(android.graphics.Color.GREEN);
        }
        else{
            holder.eventStatus.setText(event.getStatus());
            holder.eventStatus.setTextColor(android.graphics.Color.RED);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

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
