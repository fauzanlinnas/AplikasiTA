package com.example.serius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InstitutionAppointment extends AppCompatActivity {
    private ListView listView;
    private DatabaseReference appointmentRef;
    private FirebaseAuth firebaseAuth;
    private Appointment appointment;
    private ArrayList<Appointment> appointmentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution_appointment);

        listView = findViewById(R.id.lvInstitutionAppointmentList);

        appointment = new Appointment();
        appointmentArrayList = new ArrayList<>();
        final InstitutionAppointmentAdapter adapter = new InstitutionAppointmentAdapter(this, appointmentArrayList);

        appointmentRef = FirebaseDatabase.getInstance().getReference("Appointment");
        firebaseAuth = FirebaseAuth.getInstance();

        appointmentRef.addValueEventListener(new ValueEventListener() {
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(InstitutionAppointment.this, "Berhasil di klik", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(InstitutionAppointment.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_institution, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutMenuInstitution: {
                Logout();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
