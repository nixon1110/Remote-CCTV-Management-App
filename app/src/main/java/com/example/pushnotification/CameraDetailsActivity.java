package com.example.pushnotification;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class CameraDetailsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView textViewCameraDetails;
    private EditText editTextLocation, editTextMacAddress, editTextPassword, editTextType, editTextUsername;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_details);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize views
        textViewCameraDetails = findViewById(R.id.textViewCameraDetails);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextMacAddress = findViewById(R.id.editTextMacAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextType = findViewById(R.id.editTextType);
        editTextUsername = findViewById(R.id.editTextUsername);

        // Get the list of cameras from the intent
        List<Camera> cameras = getIntent().getParcelableArrayListExtra("cameras");

        // Display camera details
        displayCameraDetails(cameras);
    }

    private void displayCameraDetails(List<Camera> cameras) {
        // Display camera details in TextView
        StringBuilder stringBuilder = new StringBuilder();
        for (Camera camera : cameras) {
            stringBuilder.append("Location: ").append(camera.getLocation()).append("\n")
                    .append("MAC Address: ").append(camera.getMacAddress()).append("\n")
                    .append("Password: ").append(camera.getPassword()).append("\n")
                    .append("Type: ").append(camera.getType()).append("\n")
                    .append("Username: ").append(camera.getUsername()).append("\n\n");
        }
        textViewCameraDetails.setText(stringBuilder.toString());

        // Populate EditText fields with camera details for editing
        Camera firstCamera = cameras.get(0);
        editTextLocation.setText(firstCamera.getLocation());
        editTextMacAddress.setText(firstCamera.getMacAddress());
        editTextPassword.setText(firstCamera.getPassword());
        editTextType.setText(firstCamera.getType());
        editTextUsername.setText(firstCamera.getUsername());
    }

    // Method to handle edit button click
    public void onEditClicked(View view) {
        if (!isEditMode) {
            // Show EditText fields for editing
            editTextLocation.setVisibility(View.VISIBLE);
            editTextMacAddress.setVisibility(View.VISIBLE);
            editTextPassword.setVisibility(View.VISIBLE);
            editTextType.setVisibility(View.VISIBLE);
            editTextUsername.setVisibility(View.VISIBLE);
            // Show the save button
            findViewById(R.id.buttonSave).setVisibility(View.VISIBLE);
            isEditMode = true;
        } else {
            // Hide EditText fields if already in edit mode
            editTextLocation.setVisibility(View.GONE);
            editTextMacAddress.setVisibility(View.GONE);
            editTextPassword.setVisibility(View.GONE);
            editTextType.setVisibility(View.GONE);
            editTextUsername.setVisibility(View.GONE);
            // Hide the save button
            findViewById(R.id.buttonSave).setVisibility(View.GONE);
            isEditMode = false;
        }
    }

    // Method to handle save button click
    public void onSaveClicked(View view) {
        Log.d("CameraDetailsActivity", "Save button clicked");
        // Update camera details in Firestore
        String userId = mAuth.getCurrentUser().getUid();
        String location = editTextLocation.getText().toString();
        String macAddress = editTextMacAddress.getText().toString();
        String password = editTextPassword.getText().toString();
        String type = editTextType.getText().toString();
        String username = editTextUsername.getText().toString();

        // Query to find the document based on the location
        db.collection("users").document(userId).collection("cameras")
                .whereEqualTo("location", location)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Update the document fields
                                document.getReference().update(
                                        "macAddress", macAddress,
                                        "password", password,
                                        "type", type,
                                        "username", username
                                ).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Show success message
                                            Toast.makeText(CameraDetailsActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                                            // Refresh the UI
                                            refreshUI();
                                        } else {
                                            // Show error message
                                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                                            Toast.makeText(CameraDetailsActivity.this, "Error updating details: " + errorMessage, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        } else {
                            // Show error message
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                            Toast.makeText(CameraDetailsActivity.this, "Error updating details: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    // Method to handle back button click
    public void onBackClicked(View view) {
        finish(); // Finish the current activity
    }

    // Method to refresh UI after updating camera details
    private void refreshUI() {
        // Hide EditText fields and save button
        editTextLocation.setVisibility(View.GONE);
        editTextMacAddress.setVisibility(View.GONE);
        editTextPassword.setVisibility(View.GONE);
        editTextType.setVisibility(View.GONE);
        editTextUsername.setVisibility(View.GONE);
        findViewById(R.id.buttonSave).setVisibility(View.GONE);
        isEditMode = false;
    }
}
