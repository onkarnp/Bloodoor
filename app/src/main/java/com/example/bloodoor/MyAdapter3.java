package com.example.bloodoor;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Events event = list.get(position);
        holder.bloodBankName.setText(event.getBankName());
        holder.eventName.setText(event.getName());
        holder.eventStartDate.setText(event.getStartDate());
        holder.eventEndDate.setText(event.getEndData());
        holder.eventDescription.setText(event.getDescription());
        holder.eventDuration.setText(event.getDuration());
        holder.eventVenue.setText(event.getVenue());
        holder.eventStatus.setText(event.getStatus());
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
            eventEndDate = itemView.findViewById(R.id.showEventEndDate);
            eventDescription = itemView.findViewById(R.id.showEventDescription);
            eventDuration = itemView.findViewById(R.id.showEventDuration);
            eventVenue = itemView.findViewById(R.id.showEventVenue);
            eventStatus = itemView.findViewById(R.id.showEventStatus);
        }
    }
}
