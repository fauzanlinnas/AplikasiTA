package com.example.serius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ListView listView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference donorRef;
    private ArrayList<UserProfile> userProfileArrayList;
    private UserProfile userProfile;
    private FireMissilesDialogFragment exampleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        listView = findViewById(R.id.lvDonorList);

        userProfile = new UserProfile();
        exampleDialog = new FireMissilesDialogFragment();

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        donorRef = firebaseDatabase.getReference("Users");
        userProfileArrayList = new ArrayList<>();
        final UsersAdapter adapter = new UsersAdapter(this, userProfileArrayList);

        donorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    userProfile = ds.getValue(UserProfile.class);
                    if (userProfile.userWillDonor == 0 && !(ds.getKey().equals(firebaseAuth.getUid()))) {
                        adapter.add(userProfile);
                    }
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
                DatabaseReference userRef = donorRef.child(String.valueOf(userProfileArrayList.get(i).userToken)).child("dataRequested");
                userRef.setValue(firebaseAuth.getUid());
                // Toast.makeText(SecondActivity.this, String.valueOf(userProfileArrayList.get(i).userName), Toast.LENGTH_SHORT).show();
                // sendNotification("You have been requested");
            }
        });

        DatabaseReference notifRef = donorRef.child(firebaseAuth.getUid()).child("dataRequested");
        notifRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    sendNotification("You have been requested to be a donor");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static class FireMissilesDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Request blood?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                            Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    private void sendNotification(String messageBody) {
        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(this, YourRequested.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);

        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.notify_icon)
                        .setContentTitle("Donor Request")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(resultPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutMenu: {
                Logout();
                break;
            }
            case R.id.requestList: {
                startActivity(new Intent(SecondActivity.this ,RequestList.class));
                break;
            }
            case R.id.profileMenu: {
                startActivity(new Intent(SecondActivity.this, ProfileActivity.class));
                break;
            }
            case R.id.requested: {
                startActivity(new Intent(SecondActivity.this, YourRequested.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
