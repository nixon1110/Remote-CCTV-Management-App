package com.example.pushnotification;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateCameraDetailsActivity extends AppCompatActivity {

    private EditText editTextLocation, editTextMacAddress, editTextPassword, editTextType, editTextUsername;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_camera_details);

        db = FirebaseFirestore.getInstance();

        // Initialize views
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextMacAddress = findViewById(R.id.editTextMacAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextType = findViewById(R.id.editTextType);
        editTextUsername = findViewById(R.id.editTextUsername);

        // Get the intent extras
        String location = getIntent().getStringExtra("location");
        String macAddress = getIntent().getStringExtra("macAddress");
        String password = getIntent().getStringExtra("password");
        String type = getIntent().getStringExtra("type");
        String username = getIntent().getStringExtra("username");

        // Set the EditText fields with the received data
        editTextLocation.setText(location);
        editTextMacAddress.setText(macAddress);
        editTextPassword.setText(password);
        editTextType.setText(type);
        editTextUsername.setText(username);

        // Set the click listener for the save button
        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCameraDetails(location);
            }
        });
    }

    private void updateCameraDetails(String location) {
        String newLocation = editTextLocation.getText().toString().trim();
        String newMacAddress = editTextMacAddress.getText().toString().trim();
        String newPassword = editTextPassword.getText().toString().trim();
        String newType = editTextType.getText().toString().trim();
        String newUsername = editTextUsername.getText().toString().trim();

        if (TextUtils.isEmpty(newLocation)) {
            editTextLocation.setError("Location is required");
            return;
        }

        // Update the camera details in Firestore
        DocumentReference cameraRef = db.collection("cameras").document(location);
        cameraRef.update(
                "location", newLocation,
                "macAddress", newMacAddress,
                "password", newPassword,
                "type", newType,
                "username", newUsername
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UpdateCameraDetailsActivity.this, "Camera details updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateCameraDetailsActivity.this, "Failed to update camera details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
