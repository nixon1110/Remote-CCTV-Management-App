package com.example.pushnotification;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout alertsContainer;
    private Handler handler;
    private RequestQueue requestQueue;
    private static final String ALERTS_URL = "http://your-raspberry-pi-ip:8030/alerts"; // Replace with your Raspberry Pi IP

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        alertsContainer = findViewById(R.id.alertsContainer);

        findViewById(R.id.homeImageView).setOnClickListener(this);
        findViewById(R.id.timeImageView).setOnClickListener(this);
        findViewById(R.id.plusImageView).setOnClickListener(this);
        findViewById(R.id.graphImageView).setOnClickListener(this);
        findViewById(R.id.settingsImageView).setOnClickListener(this);

        handler = new Handler();
        requestQueue = Volley.newRequestQueue(this);

        // Start displaying alert messages every 25 seconds
        displayAlertMessages();
    }

    private void displayAlertMessages() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchAlertsFromServer();

                // Schedule the next fetch after 25 seconds
                handler.postDelayed(this, 25000); // 25 seconds
            }
        }, 0); // Initial delay of 0 milliseconds
    }

    private void fetchAlertsFromServer() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                ALERTS_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        alertsContainer.removeAllViews(); // Clear existing alerts
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject alert = response.getJSONObject(i);
                                String alertMessage = alert.getString("Alarm") + " @ " + alert.getString("MacAddress") + " Time: " + alert.getString("AlarmTime");
                                addAlertCard(alertMessage);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                error -> error.printStackTrace()
        );

        requestQueue.add(jsonArrayRequest);
    }

    private void addAlertCard(String alertMessage) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View alertCard = inflater.inflate(R.layout.alert_card, alertsContainer, false);

        TextView alertTextView = alertCard.findViewById(R.id.alertsTextView);
        ImageView alertImageView = alertCard.findViewById(R.id.alertImageView);

        alertTextView.setText(alertMessage);
        alertTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertImageView.getVisibility() == View.GONE) {
                    alertImageView.setVisibility(View.VISIBLE);
                } else {
                    alertImageView.setVisibility(View.GONE);
                }
            }
        });

        alertsContainer.addView(alertCard);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null); // Remove any pending callbacks to avoid memory leaks
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
            // Placeholder for graph activity
        } else if (v.getId() == R.id.settingsImageView) {
            startActivity(new Intent(this, ProfileActivity.class));
        }
    }
}
