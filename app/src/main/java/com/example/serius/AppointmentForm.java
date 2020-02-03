package com.example.serius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AppointmentForm extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button btnDatePicker, btnTimePicker, btnMake;
    private String hour, minute, date, waktuAppointment;
    private String uidRequester, uidDonor;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference pushRef, appointmentRef;
    private Appointment appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_form);

        btnDatePicker = findViewById(R.id.btnAppointmentDate);
        btnTimePicker = findViewById(R.id.btnAppointmentTime);
        btnMake = findViewById(R.id.btnMakeAppointment);

        firebaseAuth = FirebaseAuth.getInstance();
        appointmentRef = FirebaseDatabase.getInstance().getReference("Appointment");

        // MENDAPATKAN UID REQUESTER
        DatabaseReference getRequesterUid = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid()).child("dataRequested");
        getRequesterUid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uidRequester = String.valueOf(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate(hour, minute, date)) {
                    pushRef = appointmentRef.push();
                    String key = pushRef.getKey();

                    makeAppointmentClass(key);

                    pushRef.setValue(appointment);

                    FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid()).child("dataRequested").setValue(null);

                    Toast.makeText(AppointmentForm.this, "Appointment created", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    public Boolean validate(String _hour, String _minute, String _date) {
        Boolean result = false;

        if (_hour.isEmpty() || _minute.isEmpty() || _date.isEmpty()) {
            Toast.makeText(this, "Tanggal dan jam harus dipilih!", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;
    }

    public void passData(int _hour, int _minute) {
        hour = String.valueOf(_hour);
        minute = String.valueOf(_minute);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat fmtOut = new SimpleDateFormat("EEE, dd-MM-yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        // fmtOut.format(c.getTime());

        // String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        date = dateFormat.format(c.getTime());
        String displayDate = fmtOut.format(c.getTime());
        btnDatePicker.setText(displayDate);
    }

    public void makeAppointmentClass(String key) {
        uidDonor = firebaseAuth.getUid();
        // date di definisikan pada fungsi onDateSet
        waktuAppointment = hour + ":" + minute;
        appointment = new Appointment(key, uidRequester, uidDonor, date, waktuAppointment);
    }
}


