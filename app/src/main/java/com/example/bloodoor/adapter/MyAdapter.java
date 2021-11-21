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
import com.example.bloodoor.models.BloodAvailable;
import com.example.bloodoor.models.bloodBankHelperClass;
import com.example.bloodoor.sendEmail.JavaMailApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<bloodBankHelperClass> list;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String userID;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        bloodBankHelperClass user = list.get(position);
        holder.name.setText(user.getName());
//        holder.handlerName.setText(user.getHandlerName());
        holder.mobileNo.setText(user.getMobileNo()+" / "+user.getPhoneNo());
//        holder.phoneNo.setText(user.getPhoneNo());
        holder.email.setText(user.getEmail());
        holder.address.setText(user.getAddress()+", "+user.getbbPinCode());
//        holder.bbPinCode.setText(user.getbbPinCode());

        final String pinCode = user.getbbPinCode();
        final String nameOFReceiver = user.getHandlerName();
        final String nameOfBank = user.getName();
        final String idOfReceiver = user.getEmail();
        final String bankNumber = user.getPhoneNo();
        final String bankEmail = user.getEmail();

        holder.direct_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("geo:0, 0?q=" + nameOfBank + " blood bank, " + pinCode);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                context.startActivity(intent);
            }
        });

        holder.call_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+bankNumber));
                context.startActivity(intent);
            }
        });

        holder.mail_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Send Email")
                        .setMessage("Send Email to " + nameOfBank + "?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                                        .child("allusers").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String fullname = snapshot.child("fullName").getValue().toString();
                                        String email = snapshot.child("email").getValue().toString();
                                        String address = snapshot.child("homeAddress").getValue().toString();
                                        String phoneNo = snapshot.child("mobileNo").getValue().toString();
                                        String pinCode = snapshot.child("pinCode").getValue().toString();

                                        String mEmail = idOfReceiver;
                                        String mSubject = "BLOOD REQUEST";
                                        String mMessage = "Hello " + nameOFReceiver + ",\n" + fullname
                                                + " is in the need of blood.\n\nHere's his/her details :\n"
                                                + "Name : " + fullname + "\n"
                                                + "Mobile Number : " + phoneNo + "\n"
                                                + "Email : " + email + "\n"
                                                + "Address : " + address + "\n"
                                                + "City Pin Code : " + pinCode + "\n"
                                                + "Kindly reach out to him/her. \nThank You!\n\n"
                                                + "With Regards,\nTeam BlooDoor\nDONATE BLOOD, SAVE LIFE (:";

                                        JavaMailApi javaMailApi = new JavaMailApi(context, mEmail, mSubject, mMessage);
                                        javaMailApi.execute();
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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.custom_dialog,null);
                TextView o_pos, o_neg, a_pos, a_neg, b_pos, b_neg, ab_pos, ab_neg;
//                ImageView close, call;
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("BloodAvailability");
                o_pos = dialogView.findViewById(R.id.o_pos_count);
                o_neg = dialogView.findViewById(R.id.o_neg_count);
                a_pos = dialogView.findViewById(R.id.a_pos_count);
                a_neg = dialogView.findViewById(R.id.a_neg_count);
                b_pos = dialogView.findViewById(R.id.b_pos_count);
                b_neg = dialogView.findViewById(R.id.b_neg_count);
                ab_pos = dialogView.findViewById(R.id.ab_pos_count);
                ab_neg = dialogView.findViewById(R.id.ab_neg_count);
//                close = dialogView.findViewById(R.id.close);
//                call = dialogView.findViewById(R.id.call);
//                close.setImageResource(R.drawable.ic_close);
//                call.setImageResource(R.drawable.ic_phone);
                ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            for(DataSnapshot dataSnapshot : task.getResult().getChildren()){
                                BloodAvailable profileUser = dataSnapshot.getValue(BloodAvailable.class);
                                if(nameOfBank.equals(profileUser.getName())){
                                    o_pos.setText(profileUser.getO_pos() + " units");
                                    o_neg.setText(profileUser.getO_neg() + " units");
                                    a_pos.setText(profileUser.getA_pos() + " units");
                                    a_neg.setText(profileUser.getA_neg() + " units");
                                    b_pos.setText(profileUser.getB_pos() + " units");
                                    b_neg.setText(profileUser.getB_neg() + " units");
                                    ab_pos.setText(profileUser.getAB_pos() + " units");
                                    ab_neg.setText(profileUser.getAB_neg() + " units");
                                }
                            }
                        }
                        else{
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();


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
        CardView call_bank, mail_bank, direct_bank;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.bb_name0);
//            handlerName = itemView.findViewById(R.id.bb_holder_name0);
            mobileNo = itemView.findViewById(R.id.mobileNo0);
//            phoneNo = itemView.findViewById(R.id.phoneNo0);
            email = itemView.findViewById(R.id.email0);
            address = itemView.findViewById(R.id.homeAddress0);
//            bbPinCode = itemView.findViewById(R.id.bb_pin_code0);
//            button = itemView.findViewById(R.id.btn_email);
            call_bank = itemView.findViewById(R.id.call_bank);
            mail_bank = itemView.findViewById(R.id.mail_bank);
            direct_bank = itemView.findViewById(R.id.direct_bank);

        }
    }
}


