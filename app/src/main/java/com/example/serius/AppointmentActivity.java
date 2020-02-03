package com.example.serius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AppointmentActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseReference appointmentRef;
    private FirebaseAuth firebaseAuth;
    private Appointment appointment;
    private ArrayList<Appointment> appointmentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        listView = findViewById(R.id.lvAppointmentList);

        appointment = new Appointment();
        appointmentArrayList = new ArrayList<>();
        final AppointmentAdapter adapter = new AppointmentAdapter(this, appointmentArrayList);

        appointmentRef = FirebaseDatabase.getInstance().getReference("Appointment");
        firebaseAuth = FirebaseAuth.getInstance();

        Query query = appointmentRef.orderByChild("uidDonor").equalTo(firebaseAuth.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    appointment = ds.getValue(Appointment.class);
                    adapter.add(appointment);
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query query1 = appointmentRef.orderByChild("uidRequester").equalTo(firebaseAuth.getUid());
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    appointment = ds.getValue(Appointment.class);
                    adapter.add(appointment);
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
