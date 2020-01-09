package com.example.serius;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userName, userEmail, userPassword, userAge;
    private EditText userTelepon, userAddress, userGoldar, userPenyakit;
    private EditText userRecency, userFrequency, userTime;
    String[] dataDonorArr = new String[3];
    List<String> dataDonor;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private ImageView userProfilePic;

    String email, name, age, password, address, telepon, goldar, penyakit;

    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;

    private Classifier mClassifier = null;
    private double hasilClassify;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                userProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*"); // application/*   audio/*
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), PICK_IMAGE);
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    // Run weka
                    wekaLoadModel();

                    //Upload data to the database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //sendEmailVerification();
                                sendUserData();
                                firebaseAuth.signOut();
                                Toast.makeText(RegistrationActivity.this, "Successfully Registered, Upload complete!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });
    }

    private void setupUIViews() {
        userProfilePic = findViewById(R.id.ivProfilePic);
        userEmail = findViewById(R.id.etUserEmail);
        userName = findViewById(R.id.etUserName);
        userPassword = findViewById(R.id.etUserPassword);
        userAddress = findViewById(R.id.etAddressUpdate);
        userAge = findViewById(R.id.etUserAge);
        userGoldar = findViewById(R.id.etGoldarUpdate);
        userTelepon = findViewById(R.id.etTeleponUpdate);
        userPenyakit = findViewById(R.id.etPenyakitUpdate);
        regButton = findViewById(R.id.btnRegister);
        userLogin = findViewById(R.id.tvUserLogin);
        userRecency = findViewById(R.id.etRecency);
        userFrequency = findViewById(R.id.etFrequency);
        userTime = findViewById(R.id.etTime);
    }

    private Boolean validate() {
        Boolean result = false;

        email = userEmail.getText().toString();
        name = userName.getText().toString();
        password = userPassword.getText().toString();
        address = userAddress.getText().toString();
        telepon = userTelepon.getText().toString();
        age = userAge.getText().toString();
        goldar = userGoldar.getText().toString();
        penyakit = userPenyakit.getText().toString();
        dataDonorArr[0] = userRecency.getText().toString();
        dataDonorArr[1] = userFrequency.getText().toString();
        dataDonorArr[2] = userTime.getText().toString();
        dataDonor = Arrays.asList(dataDonorArr);

        if (name.isEmpty() || password.isEmpty() || email.isEmpty() || age.isEmpty() || imagePath == null
                || address.isEmpty() || telepon.isEmpty() || goldar.isEmpty() || penyakit.isEmpty()) {
            Toast.makeText(this, "Please enter all the details!", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;
    }

    public void wekaLoadModel() {
        AssetManager assetManager = getAssets();
        try {
            mClassifier = (Classifier) weka.core.SerializationHelper.read(assetManager.open("donordarah.model"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // Weka "catch'em all!"
            e.printStackTrace();
        }

        if(mClassifier==null){
            Toast.makeText(this, "Model not loaded!", Toast.LENGTH_SHORT).show();
            return;
        }

        // we need those for creating new instances later
        // order of attributes/classes needs to be exactly equal to those used for training
        final Attribute attributeRecency = new Attribute("recency");
        final Attribute attributeFrequency = new Attribute("frequency");
        final Attribute attributeTime = new Attribute("time");
        final List<String> classes = new ArrayList<String>() {
            {
                add("tidak donor"); // cls nr 1
                add("donor"); // cls nr 2
            }
        };

        // Instances(...) requires ArrayList<> instead of List<>...
        ArrayList<Attribute> attributeList = new ArrayList<Attribute>(2) {
            {
                add(attributeRecency);
                add(attributeFrequency);
                add(attributeTime);
                Attribute attributeClass = new Attribute("@@class@@", classes);
                add(attributeClass);
            }
        };
        // unpredicted data sets (reference to sample structure for new instances)
        Instances dataUnpredicted = new Instances("TestInstances",
                attributeList, 1);
        // last feature is target variable
        dataUnpredicted.setClassIndex(dataUnpredicted.numAttributes() - 1);

        // create new instance: this one should fall into the setosa domain
        DenseInstance newInstance = new DenseInstance(dataUnpredicted.numAttributes()) {
            {
                setValue(attributeRecency, Double.parseDouble(dataDonorArr[0]));
                setValue(attributeFrequency, Double.parseDouble(dataDonorArr[1]));
                setValue(attributeTime, Double.parseDouble(dataDonorArr[2]));
            }
        };
        // reference to dataset
        newInstance.setDataset(dataUnpredicted);

        // predict new sample
        try {
            hasilClassify = mClassifier.classifyInstance(newInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendUserData();
                        Toast.makeText(RegistrationActivity.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Verification mail hasn't been sent", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void sendUserData() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference userRef = rootRef.child(firebaseAuth.getUid());
        StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic"); // User ID/images/profile_pic.png
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrationActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(RegistrationActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
            }
        });
        UserProfile userProfile = new UserProfile(email, name, address, telepon, age, goldar, penyakit, dataDonor, hasilClassify);
        userRef.setValue(userProfile);
    }
}
