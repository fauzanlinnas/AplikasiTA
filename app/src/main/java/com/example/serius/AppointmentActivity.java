package com.example.serius;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AppointmentActivity extends AppCompatActivity {

    private TextView appointmentRequester, appointmentDonor, appointmentDate, appointmentTime;
    private DatabaseReference appointmentRef;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        appointmentRequester = findViewById(R.id.tvAppointmentRequester);
        appointmentDonor = findViewById(R.id.tvAppointmentDonor);
        appointmentDate = findViewById(R.id.tvAppointmentDate);
        appointmentTime = findViewById(R.id.tvAppointmentTime);

        appointmentRef = FirebaseDatabase.getInstance().getReference("Appointment");
        firebaseAuth = FirebaseAuth.getInstance();


    }
}
