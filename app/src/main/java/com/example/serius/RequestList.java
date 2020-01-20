package com.example.serius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

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

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date dateObj = null;
                    try {
                        dateObj = simpleDateFormat.parse(requestBlood.date);
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
    }
}
