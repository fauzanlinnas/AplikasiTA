package com.example.serius;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestActivity extends AppCompatActivity {

    private EditText reqName, reqGoldar, reqJumlah, reqTelepon;
    private Button btnReq;
    String name, goldar, jumlah, telepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        reqName = findViewById(R.id.etRequestNama);
        reqGoldar = findViewById(R.id.etRequestGoldar);
        reqJumlah = findViewById(R.id.etRequestJumlah);
        reqTelepon = findViewById(R.id.etRequestTelepon);
        btnReq = findViewById(R.id. btnRequestAdd);

        btnReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    // Upload data to database
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Request");
                    DatabaseReference reqRef = rootRef.push();
                    RequestBlood requestBlood = new RequestBlood(name, goldar, jumlah, telepon);
                    reqRef.setValue(requestBlood);
                    Toast.makeText(RequestActivity.this, "Request Darah Berhasil", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(RequestActivity.this, SecondActivity.class));
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

        if (name.isEmpty() || goldar.isEmpty() || jumlah.isEmpty() || telepon.isEmpty()) {
            Toast.makeText(this, "Tolong isi semua data!", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }
}
