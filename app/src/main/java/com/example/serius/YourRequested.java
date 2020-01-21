package com.example.serius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class YourRequested extends AppCompatActivity {

    private TextView requestedName, requestedGoldar, requestedJumlah, requestedHape;
    private TextView yourRequestName, yourRequestGoldar, yourRequestJumlah, yourRequestHape;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_requested);

        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference reqRef = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid()).child("dataRequested");
        reqRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("YourRequested", String.valueOf(dataSnapshot));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupUIViews() {
        // Requested text view
        requestedName = findViewById(R.id.tvRequestedName2);
        requestedGoldar = findViewById(R.id.tvRequestedGoldar2);
        requestedJumlah = findViewById(R.id.tvRequestedJumlah2);
        requestedHape = findViewById(R.id.tvRequestedHape2);

        // Your request text view
        yourRequestName = findViewById(R.id.tvYourRequestName2);
        yourRequestGoldar = findViewById(R.id.tvYourRequestGoldar2);
        yourRequestJumlah = findViewById(R.id.tvYourRequestJumlah2);
        yourRequestHape = findViewById(R.id.tvYourRequestHape2);
    }
}
