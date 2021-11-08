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

import com.example.bloodoor.sendEmail.JavaMailApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<bloodBankHelperClass> list;

    public MyAdapter(Context context, ArrayList<bloodBankHelperClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.bloodbankdata, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        bloodBankHelperClass user = list.get(position);
        holder.name.setText(user.getName());
        holder.handlerName.setText(user.getHandlerName());
        holder.mobileNo.setText(user.getMobileNo());
        holder.phoneNo.setText(user.getPhoneNo());
        holder.email.setText(user.getEmail());
        holder.address.setText(user.getAddress());
        holder.bbPinCode.setText(user.getbbPinCode());

        final String nameOFReceiver = user.getHandlerName();
        final String nameOfBank = user.getName();
        final String idOfReceiver = user.getEmail();

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Send Email")
                        .setMessage("Send Email to " + user.getName() + "?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                                        .child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String fullname = snapshot.child("fullName").getValue().toString();
                                        String email = snapshot.child("email").getValue().toString();
                                        String address = snapshot.child("homeAddress").getValue().toString();
                                        String phoneNo = snapshot.child("mobileNo").getValue().toString();
                                        String pinCode = snapshot.child("pinCode").getValue().toString();

                                        String mEmail = user.getEmail();
                                        String mSubject = "BLOOD REQUEST";
                                        String mMessage = "Hello " + nameOFReceiver + "," + fullname
                                                + "would like to Blood Donation from you.\nHere's his/her details :\n"
                                                + "Name : " + fullname + "\n"
                                                + "Mobile Number : " + phoneNo + "\n"
                                                + "Email : " + email + "\n"
                                                + "Address : " + address + "\n"
                                                + "City Pin Code : " + pinCode + "\n"
                                                + "Kindly reach out to him/her. Thank You...\n"
                                                + "BlooDoor... DONATE BLOOD, SAVE LIFE (:";


                                        JavaMailApi javaMailApi = new JavaMailApi(context, mEmail, mSubject, mMessage);
                                        javaMailApi.execute();

                                        DatabaseReference senderRef = FirebaseDatabase.getInstance().getReference("email")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        senderRef.child(idOfReceiver).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, handlerName, mobileNo, phoneNo, email, address, bbPinCode;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.bb_name0);
            handlerName = itemView.findViewById(R.id.bb_holder_name0);
            mobileNo = itemView.findViewById(R.id.mobileNo0);
            phoneNo = itemView.findViewById(R.id.phoneNo0);
            email = itemView.findViewById(R.id.email0);
            address = itemView.findViewById(R.id.homeAddress0);
            bbPinCode = itemView.findViewById(R.id.bb_pin_code0);
            button = itemView.findViewById(R.id.btn_email);
        }
    }
}


