package com.example.serius;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AppointmentAdapter extends ArrayAdapter<Appointment> {
    private DatabaseReference donorRef, requesterRef;
    private String donorUid, requesterUid;
    private String donorName, requesterName;

    public AppointmentAdapter(Context context, ArrayList<Appointment> appointments) {
        super(context, 0, appointments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView appointmentDate, appointmentTime;
        final TextView[] appointmentRequester = new TextView[1];
        final TextView[] appointmentDonor = new TextView[1];

        Appointment appointment = getItem(position);

        donorUid = appointment.getUidDonor();
        requesterUid = appointment.getUidRequester();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.appointment_list, parent, false);
        }

        donorRef = FirebaseDatabase.getInstance().getReference("Users").child(donorUid).child("userName");
        final View finalConvertView1 = convertView;
        donorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donorName = String.valueOf(dataSnapshot.getValue());
                appointmentDonor[0] = finalConvertView1.findViewById(R.id.tvAppointmentDonor);
                appointmentDonor[0].setText(donorName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        requesterRef = FirebaseDatabase.getInstance().getReference("Users").child(requesterUid).child("userName");
        final View finalConvertView = convertView;
        requesterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requesterName = String.valueOf(dataSnapshot.getValue());
                appointmentRequester[0] = finalConvertView.findViewById(R.id.tvAppointmentRequester);
                appointmentRequester[0].setText(requesterName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        appointmentDate = convertView.findViewById(R.id.tvAppointmentDate);
        appointmentTime = convertView.findViewById(R.id.tvAppointmentTime);

        appointmentDate.setText(appointment.getTanggal());
        appointmentTime.setText(appointment.getJam());

        return convertView;
    }
}
