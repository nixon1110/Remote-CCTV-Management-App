package com.example.pushnotification;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements CameraLocationAdapter.OnLocationClickListener, View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private CameraLocationAdapter adapter;
    private List<String> locations;
    private static final int CAMERA_DETAILS_REQUEST_CODE = 1001;
    private TextView greetingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        locations = new ArrayList<>();
        greetingTextView = findViewById(R.id.greetingTextView);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CameraLocationAdapter(locations, this);
        recyclerView.setAdapter(adapter);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            greetingTextView.setText("Hi, " + userEmail);
        }

        fetchCameraLocations();

        findViewById(R.id.homeImageView).setOnClickListener(this);
        findViewById(R.id.timeImageView).setOnClickListener(this);
        findViewById(R.id.plusImageView).setOnClickListener(this);
        findViewById(R.id.graphImageView).setOnClickListener(this);
        findViewById(R.id.settingsImageView).setOnClickListener(this);
    }

    private void fetchCameraLocations() {
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("users").document(userId).collection("cameras")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        locations.clear(); // Clear previous data
                        for (DocumentSnapshot document : task.getResult()) {
                            String location = document.getString("location");
                            locations.add(location);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("FetchData", "Error fetching data: ", task.getException());
                        Toast.makeText(HomeActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onLocationClick(String location) {
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("users").document(userId).collection("cameras")
                .whereEqualTo("location", location)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Camera> cameras = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            Camera camera = document.toObject(Camera.class);
                            cameras.add(camera);
                        }
                        displayCameraDetails(cameras);
                    } else {
                        Log.e("FetchData", "Error fetching camera details: ", task.getException());
                        Toast.makeText(HomeActivity.this, "Error fetching camera details", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displayCameraDetails(List<Camera> cameras) {
        Intent intent = new Intent(this, CameraDetailsActivity.class);
        intent.putParcelableArrayListExtra("cameras", new ArrayList<>(cameras));
        startActivityForResult(intent, CAMERA_DETAILS_REQUEST_CODE);
    }

        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_DETAILS_REQUEST_CODE && resultCode == RESULT_OK) {
            fetchCameraLocations();
        }
    }

    private void startAlarmSchedularActivity(String location) {
        Intent intent = new Intent(this, AlarmSchedularActivity.class);
        intent.putExtra("cameraLocation", location);
        startActivity(intent);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.homeImageView) {
            // Handle home image view click
        } else if (v.getId() == R.id.timeImageView) {
            startActivity(new Intent(this, AlarmSchedularActivity.class));
        } else if (v.getId() == R.id.plusImageView) {
            startActivity(new Intent(this, CameraRegistrationActivity.class));
        } else if (v.getId() == R.id.graphImageView) {
            startActivity(new Intent(this, NotificationActivity.class));
        } else if (v.getId() == R.id.settingsImageView) {
            startActivity(new Intent(this, ProfileActivity.class));
        }
    }
}
