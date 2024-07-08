package com.example.pushnotification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CameraRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextMacAddress, editTextUsername, editTextPassword, editTextLocation;
    private Spinner spinnerType;
    private Button buttonRegisterCamera;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_registration);

        // Set click listeners for image views
        findViewById(R.id.homeImageView).setOnClickListener(this);
        findViewById(R.id.timeImageView).setOnClickListener(this);
        findViewById(R.id.plusImageView).setOnClickListener(this);
        findViewById(R.id.graphImageView).setOnClickListener(this);
        findViewById(R.id.settingsImageView).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        editTextMacAddress = findViewById(R.id.editTextMacAddress);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextLocation = findViewById(R.id.editTextLocation);
        spinnerType = findViewById(R.id.spinnerType);
        buttonRegisterCamera = findViewById(R.id.buttonRegisterCamera);

        buttonRegisterCamera.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimaryDark));

        // Populate spinner with camera types
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.camera_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        buttonRegisterCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String macAddress = editTextMacAddress.getText().toString();
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String location = editTextLocation.getText().toString();
                String type = spinnerType.getSelectedItem().toString();

                // Validate input fields
                if (macAddress.isEmpty() || username.isEmpty() || password.isEmpty() || location.isEmpty()) {
                    Toast.makeText(CameraRegistrationActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save camera details to Firestore
                saveCameraToFirestore(macAddress, username, password, location, type);
            }
        });
    }

    private void saveCameraToFirestore(String macAddress, String username, String password, String location, String type) {
        // Create a new camera document in Firestore
        db.collection("users").document(mAuth.getCurrentUser().getUid())
                .collection("cameras").document()
                .set(new Camera(macAddress, username, password, location, type))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Camera registration successful
                            Toast.makeText(CameraRegistrationActivity.this, "Camera registered successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // Camera registration failed
                            Toast.makeText(CameraRegistrationActivity.this, "Failed to register camera. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.homeImageView) {
            startActivity(new Intent(this, HomeActivity.class));
        } else if (v.getId() == R.id.timeImageView) {
            startActivity(new Intent(this, AlarmSchedularActivity.class));
        } else if (v.getId() == R.id.plusImageView) {
        } else if (v.getId() == R.id.graphImageView) {
            startActivity(new Intent(this, NotificationActivity.class));
        } else if (v.getId() == R.id.settingsImageView) {
            startActivity(new Intent(this, ProfileActivity.class));
        }
    }
}
