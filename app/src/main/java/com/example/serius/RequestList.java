package com.example.serius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RequestList extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FloatingActionButton fabAddRequest;
    private ListView listView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reqRef;
    private ArrayList<RequestBlood> requestBloodArrayList;
    private RequestBlood requestBlood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        listView = findViewById(R.id.lvRequestList);
        fabAddRequest = findViewById(R.id.fabAddRequest);

        requestBlood = new RequestBlood();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reqRef = firebaseDatabase.getReference("Request");
        requestBloodArrayList = new ArrayList<>();
        final RequestAdapter adapter = new RequestAdapter(this, requestBloodArrayList);

        reqRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    requestBlood = ds.getValue(RequestBlood.class);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date dateObj = null;
                    try {
                        dateObj = simpleDateFormat.parse(requestBlood.date);
                        Log.d("TryBerhasil", String.valueOf(dateObj));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (new Date().before(dateObj)) {
                        adapter.add(requestBlood);
                    }
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fabAddRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reqRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("RequestListGetUid", String.valueOf(dataSnapshot.getValue()));
                        Log.d("RequestList", String.valueOf(reqRef.child(firebaseAuth.getUid())));
                        if (dataSnapshot.getValue() == null) {
                            startActivity(new Intent(RequestList.this, RequestActivity.class));
                        } else {
                            Toast.makeText(RequestList.this, "You have already requested blood", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
