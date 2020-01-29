package com.example.serius;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestedResponse extends AppCompatActivity {

    private Button btnYes, btnNo;
    private DatabaseReference usersRef;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_response);

        btnYes = findViewById(R.id.btnRequestedYes);
        btnNo = findViewById(R.id.btnRequestedNo);

        usersRef = FirebaseDatabase.getInstance().getReference("Users");

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RequestedResponse.this, AppointmentForm.class));
                finish();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dataRequestedRef = usersRef.child(firebaseAuth.getUid()).child("dataRequested");
                dataRequestedRef.setValue(null);
                Toast.makeText(RequestedResponse.this, "You have decline to be a donor", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
