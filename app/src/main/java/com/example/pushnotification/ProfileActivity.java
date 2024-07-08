package com.example.pushnotification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    Button button;
    TextView textView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViewById(R.id.homeImageView).setOnClickListener(this);
        findViewById(R.id.timeImageView).setOnClickListener(this);
        findViewById(R.id.plusImageView).setOnClickListener(this);
        findViewById(R.id.graphImageView).setOnClickListener(this);
        findViewById(R.id.settingsImageView).setOnClickListener(this);


        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimaryDark));
        textView = findViewById(R.id.user_details);
        user = auth.getCurrentUser();
        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            textView.setText(user.getEmail());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
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
            startActivity(new Intent(this, CameraRegistrationActivity.class));
        } else if (v.getId() == R.id.graphImageView) {
            startActivity(new Intent(this, NotificationActivity.class));
        } else if (v.getId() == R.id.settingsImageView) {

        }
    }
}