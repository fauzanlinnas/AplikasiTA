package com.example.serius;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RequestActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText reqName, reqGoldar, reqJumlah, reqTelepon, namaDokter, telpRumahSakit;
    private Button btnReq, reqDate;
    String name, goldar, jumlah, telepon, dokter, teleponRumahSakit, date;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        firebaseAuth = FirebaseAuth.getInstance();

        reqName = findViewById(R.id.etRequestNama);
        reqGoldar = findViewById(R.id.etRequestGoldar);
        reqJumlah = findViewById(R.id.etRequestJumlah);
        reqTelepon = findViewById(R.id.etRequestTelepon);
        namaDokter = findViewById(R.id.etRequestNamaDokter);
        telpRumahSakit = findViewById(R.id.etRequestTelpRumahSakit);
        reqDate = findViewById(R.id.datePicker);
        btnReq = findViewById(R.id. btnRequestAdd);

        reqDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        btnReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    // Upload data to database
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Request");
                    DatabaseReference reqRef = rootRef.child(firebaseAuth.getUid());
                    RequestBlood requestBlood = new RequestBlood(name, goldar, jumlah, telepon, dokter, teleponRumahSakit, date, firebaseAuth.getUid(), false);
                    reqRef.setValue(requestBlood);
                    Toast.makeText(RequestActivity.this, "Request Darah Berhasil", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RequestActivity.this, SecondActivity.class));
                    finish();
                }
            }
        });
    }

    private Boolean validate() {
        Boolean result = false;

        name = reqName.getText().toString();
        goldar = reqGoldar.getText().toString();
        jumlah = reqJumlah.getText().toString();
        telepon = reqTelepon.getText().toString();
        dokter = namaDokter.getText().toString();
        teleponRumahSakit = telpRumahSakit.getText().toString();
        // deklarasi "date" di fungsi "onDateSetonDateSet"

        if (name.isEmpty() || goldar.isEmpty() || jumlah.isEmpty() || telepon.isEmpty() || date.isEmpty() || dokter.isEmpty() || teleponRumahSakit.isEmpty()) {
            Toast.makeText(this, "Semua data wajib diisi", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
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
        reqDate.setText(displayDate);
    }
}
