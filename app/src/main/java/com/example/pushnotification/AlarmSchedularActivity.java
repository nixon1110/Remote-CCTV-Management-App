package com.example.pushnotification;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import soup.neumorphism.NeumorphButton;
import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.ShapeType;

import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class AlarmSchedularActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout cameraContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_schedular);

        cameraContainer = findViewById(R.id.cameraContainer);

        // Set OnClickListener for ImageView buttons
        findViewById(R.id.homeImageView).setOnClickListener(this);
        findViewById(R.id.timeImageView).setOnClickListener(this);
        findViewById(R.id.plusImageView).setOnClickListener(this);
        findViewById(R.id.graphImageView).setOnClickListener(this);
        findViewById(R.id.settingsImageView).setOnClickListener(this);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("users").document(userId)
                    .collection("cameras").get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<String> cameraLocations = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String cameraLocation = document.getString("location");
                                if (cameraLocation != null && !cameraLocation.isEmpty()) {
                                    cameraLocations.add(cameraLocation);
                                }
                            }
                            if (cameraLocations.isEmpty()) {
                                TextView messageTextView = findViewById(R.id.cameraLocationTextView);
                                messageTextView.setText("Nothing to show");
                            } else {
                                LayoutInflater inflater = LayoutInflater.from(this);
                                for (String location : cameraLocations) {
                                    View cameraView = inflater.inflate(R.layout.alarm_dynamic_adder, null);
                                    TextView locationTextView = cameraView.findViewById(R.id.port1_text);
                                    locationTextView.setText(location);

                                    TextView startTimeTextView = cameraView.findViewById(R.id.startTime1);
                                    TextView stopTimeTextView = cameraView.findViewById(R.id.stopTime1);

                                    Spinner amPmStartSpinner = cameraView.findViewById(R.id.amPmStartSpinner);
                                    Spinner amPmEndSpinner = cameraView.findViewById(R.id.amPmEndSpinner);

                                    // Set click listeners for startTimeTextView and stopTimeTextView
                                    startTimeTextView.setOnClickListener(v -> showTimePickerDialog(true, startTimeTextView));
                                    stopTimeTextView.setOnClickListener(v -> showTimePickerDialog(false, stopTimeTextView));

                                    // Set ArrayAdapter for spinners
                                    ArrayAdapter<CharSequence> amPmAdapter = ArrayAdapter.createFromResource(this,
                                            R.array.am_pm_array, android.R.layout.simple_spinner_item);
                                    amPmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    amPmStartSpinner.setAdapter(amPmAdapter);
                                    amPmEndSpinner.setAdapter(amPmAdapter);

                                    // Set DayImageClickListener as click listener for ImageView elements
                                    setDayClickListener(cameraView);

                                    cameraContainer.addView(cameraView);
                                }

                                // Get reference to the update button
                                NeumorphButton updateButton = findViewById(R.id.buttonUp1);

                                // Set OnClickListener for the update button
                                updateButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Display a toast message
                                        Toast.makeText(AlarmSchedularActivity.this, "Scheduler updated successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            Log.e("Firestore", "Error getting documents: ", task.getException());
                        }
                    });
        }
    }


    private void showTimePickerDialog(boolean isStartTime, TextView textViewToUpdate) {
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            String time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
            textViewToUpdate.setText(time);
        };

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, timeSetListener, hour, minute, true);
        timePickerDialog.show();
    }

    /*private void createCameraView(String cameraLocation) {
        // Inflate the dynamic layout for each camera
        View dynamicLayout = LayoutInflater.from(this).inflate(R.layout.alarm_dynamic_adder, null);
        TextView cameraLocationTextView = dynamicLayout.findViewById(R.id.port1_text);
        cameraLocationTextView.setText(cameraLocation);

        // Add the dynamic layout to the cameraContainer
        cameraContainer.addView(dynamicLayout);
    }*/


    private void setDayClickListener(View cameraView) {
        ImageView sundayImageView = cameraView.findViewById(R.id.sunday);
        ImageView mondayImageView = cameraView.findViewById(R.id.monday);
        ImageView tuesdayImageView = cameraView.findViewById(R.id.tuesday);
        ImageView wednesdayImageView = cameraView.findViewById(R.id.wednesday);
        ImageView thursdayImageView = cameraView.findViewById(R.id.thursday);
        ImageView fridayImageView = cameraView.findViewById(R.id.friday);
        ImageView saturdayImageView = cameraView.findViewById(R.id.saturday);

        DayImageClickListener dayClickListener = new DayImageClickListener();
        sundayImageView.setOnClickListener(dayClickListener);
        mondayImageView.setOnClickListener(dayClickListener);
        tuesdayImageView.setOnClickListener(dayClickListener);
        wednesdayImageView.setOnClickListener(dayClickListener);
        thursdayImageView.setOnClickListener(dayClickListener);
        fridayImageView.setOnClickListener(dayClickListener);
        saturdayImageView.setOnClickListener(dayClickListener);
    }

    private boolean isSundaySelected = false;
    private boolean isMondaySelected = false;
    private boolean isTuesdaySelected = false;
    private boolean isWednesdaySelected = false;
    private boolean isThursdaySelected = false;
    private boolean isFridaySelected = false;
    private boolean isSaturdaySelected = false;

    @Override
    public void onClick(View v) {
        Context context = v.getContext();

        if (v.getId() == R.id.homeImageView) {
            startActivity(new Intent(this, HomeActivity.class));
        } else if (v.getId() == R.id.timeImageView) {
            // Handle click event for timeImageView
        } else if (v.getId() == R.id.plusImageView) {
            startActivity(new Intent(this, CameraRegistrationActivity.class));
        } else if (v.getId() == R.id.graphImageView) {
            startActivity(new Intent(this, NotificationActivity.class));
        } else if (v.getId() == R.id.settingsImageView) {
            startActivity(new Intent(this, ProfileActivity.class));
        } /*else if (v.getId() == R.id.sunday) {
            ImageView sundayImageView = (ImageView) v;
            if (isSundaySelected) {
                sundayImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.swhite));
                isSundaySelected = false;
            } else {
                sundayImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.sred));
                isSundaySelected = true;
            }
        } else if (v.getId() == R.id.monday) {
            ImageView mondayImageView = (ImageView) v;
            if (isMondaySelected) {
                mondayImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.mwhite));
                isMondaySelected = false;
            } else {
                mondayImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.mred));
                isMondaySelected = true;
            }
        } else if (v.getId() == R.id.tuesday) {
            ImageView tuesdayImageView = (ImageView) v;
            if (isTuesdaySelected) {
                tuesdayImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.twhite));
                isTuesdaySelected = false;
            } else {
                tuesdayImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.tred));
                isTuesdaySelected = true;
            }
        } else if (v.getId() == R.id.wednesday) {
            ImageView wednesdayImageView = (ImageView) v;
            if (isWednesdaySelected) {
                wednesdayImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.wwhite));
                isWednesdaySelected = false;
            } else {
                wednesdayImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.wred));
                isWednesdaySelected = true;
            }
        } else if (v.getId() == R.id.thursday) {
            ImageView thursdayImageView = (ImageView) v;
            if (isThursdaySelected) {
                thursdayImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.thwhite));
                isThursdaySelected = false;
            } else {
                thursdayImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.thred));
                isThursdaySelected = true;
            }
        } else if (v.getId() == R.id.friday) {
            ImageView fridayImageView = (ImageView) v;
            if (isFridaySelected) {
                fridayImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.fwhite));
                isFridaySelected = false;
            } else {
                fridayImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.fred));
                isFridaySelected = true;
            }
        } else if (v.getId() == R.id.saturday) {
            ImageView saturdayImageView = (ImageView) v;
            if (isSaturdaySelected) {
                saturdayImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.sawhite));
                isSaturdaySelected = false;
            } else {
                saturdayImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.sared));
                isSaturdaySelected = true;
            }
        }*/

        if (v.getId() == R.id.sunday) {
            ImageView sundayImageView = (ImageView) v;
            if (isSundaySelected) {
                sundayImageView.setBackgroundResource(R.drawable.swhite);
                isSundaySelected = false;
            } else {
                sundayImageView.setBackgroundResource(R.drawable.sred);
                isSundaySelected = true;
            }
        } else if (v.getId() == R.id.monday) {
            ImageView mondayImageView = (ImageView) v;
            if (isMondaySelected) {
                mondayImageView.setBackgroundResource(R.drawable.mwhite);
                isMondaySelected = false;
            } else {
                mondayImageView.setBackgroundResource(R.drawable.mred);
                isMondaySelected = true;
            }
        } else if (v.getId() == R.id.tuesday) {
            ImageView tuesdayImageView = (ImageView) v;
            if (isTuesdaySelected) {
                tuesdayImageView.setBackgroundResource(R.drawable.twhite);
                isTuesdaySelected = false;
            } else {
                tuesdayImageView.setBackgroundResource( R.drawable.tred);
                isTuesdaySelected = true;
            }
        } else if (v.getId() == R.id.wednesday) {
            ImageView wednesdayImageView = (ImageView) v;
            if (isWednesdaySelected) {
                wednesdayImageView.setBackgroundResource(R.drawable.wwhite);
                isWednesdaySelected = false;
            } else {
                wednesdayImageView.setBackgroundResource(R.drawable.wred);
                isWednesdaySelected = true;
            }
        } else if (v.getId() == R.id.thursday) {
            ImageView thursdayImageView = (ImageView) v;
            if (isThursdaySelected) {
                thursdayImageView.setBackgroundResource(R.drawable.thwhite);
                isThursdaySelected = false;
            } else {
                thursdayImageView.setBackgroundResource(R.drawable.thred);
                isThursdaySelected = true;
            }
        } else if (v.getId() == R.id.friday) {
            ImageView fridayImageView = (ImageView) v;
            if (isFridaySelected) {
                fridayImageView.setBackgroundResource(R.drawable.fwhite);
                isFridaySelected = false;
            } else {
                fridayImageView.setBackgroundResource(R.drawable.fred);
                isFridaySelected = true;
            }
        } else if (v.getId() == R.id.saturday) {
            ImageView saturdayImageView = (ImageView) v;
            if (isSaturdaySelected) {
                saturdayImageView.setBackgroundResource(R.drawable.sawhite);
                isSaturdaySelected = false;
            } else {
                saturdayImageView.setBackgroundResource(R.drawable.sared);
                isSaturdaySelected = true;
            }
        }
    }
}
