package com.example.serius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YourRequested extends AppCompatActivity {

    LinearLayout llAtas, llBawah;
    private TextView requestedName, requestedGoldar, requestedJumlah, requestedHape;
    private TextView yourRequestName, yourRequestGoldar, yourRequestJumlah, yourRequestHape;
    private TextView noRequestedBy, noMyRequest;
    private String requesterUId;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private RequestBlood requestedBy, userRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_requested);

        setupUIViews();

        requestedBy = new RequestBlood();
        userRequest = new RequestBlood();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference userRequestedBy = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid()).child("dataRequested");
        userRequestedBy.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requesterUId = String.valueOf(dataSnapshot.getValue());

                DatabaseReference reqDetail = firebaseDatabase.getReference("Request").child(requesterUId);
                reqDetail.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("RequestDetail", String.valueOf(dataSnapshot));

                        // IF YOU ARE REQUESTED
                        if (dataSnapshot.getValue() != null) {
                            requestedBy = dataSnapshot.getValue(RequestBlood.class);

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            Date dateObj = null;
                            try {
                                dateObj = simpleDateFormat.parse(requestedBy.date);
                                Log.d("TryBerhasil", String.valueOf(dateObj));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if (new Date().before(dateObj)) {
                                setTextRequestedBy();
                            }
                        } else {
                            // IF YOU ARE NOT REQUESTED
                            llAtas.setVisibility(View.GONE);
                            noRequestedBy.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference myRequest = firebaseDatabase.getReference("Request").child(firebaseAuth.getUid());
        myRequest.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    userRequest = dataSnapshot.getValue(RequestBlood.class);
                    setTextMyRequest();
                } else {
                    llBawah.setVisibility(View.GONE);
                    noMyRequest.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupUIViews() {
        // Requested text view
        llAtas = findViewById(R.id.llRequestedBy);
        requestedName = findViewById(R.id.tvRequestedName2);
        requestedGoldar = findViewById(R.id.tvRequestedGoldar2);
        requestedJumlah = findViewById(R.id.tvRequestedJumlah2);
        requestedHape = findViewById(R.id.tvRequestedHape2);

        // Your request text view
        llBawah = findViewById(R.id.llRequest);
        yourRequestName = findViewById(R.id.tvYourRequestName2);
        yourRequestGoldar = findViewById(R.id.tvYourRequestGoldar2);
        yourRequestJumlah = findViewById(R.id.tvYourRequestJumlah2);
        yourRequestHape = findViewById(R.id.tvYourRequestHape2);

        // IF NO
        noRequestedBy = findViewById(R.id.tvNoRequestedBy);
        noMyRequest = findViewById(R.id.tvNoMyRequest);
    }

    private void setTextRequestedBy() {
        requestedName.setText(requestedBy.getName());
        requestedGoldar.setText(requestedBy.getGoldar());
        requestedJumlah.setText(requestedBy.getJumlah());
        requestedHape.setText(requestedBy.getHape());
    }

    private void setTextMyRequest() {
        yourRequestName.setText(userRequest.getName());
        yourRequestGoldar.setText(userRequest.getGoldar());
        yourRequestJumlah.setText(userRequest.getJumlah());
        yourRequestHape.setText(userRequest.getHape());
    }
}
