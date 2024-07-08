package com.example.pushnotification;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class AlarmDynamicAdder extends AppCompatActivity {

    private TextView startTimeTextView;
    private TextView stopTimeTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_dynamic_adder);

        // Find TextViews for start time and stop time
        startTimeTextView = findViewById(R.id.startTime1);
        stopTimeTextView = findViewById(R.id.stopTime1);

        // Set OnClickListener for clock image in start time TextView
        ImageView startTimeClockImage = findViewById(R.id.startTimeClockImage);
        startTimeClockImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(true);
            }
        });

        // Set OnClickListener for clock image in stop time TextView
        ImageView stopTimeClockImage = findViewById(R.id.stopTimeClockImage);
        stopTimeClockImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(false);
            }
        });

        // Find ImageView elements for days
        ImageView sundayImageView = findViewById(R.id.sunday);
        ImageView mondayImageView = findViewById(R.id.monday);
        ImageView tuesdayImageView = findViewById(R.id.tuesday);
        ImageView wednesdayImageView = findViewById(R.id.wednesday);
        ImageView thursdayImageView = findViewById(R.id.thursday);
        ImageView fridayImageView = findViewById(R.id.friday);
        ImageView saturdayImageView = findViewById(R.id.saturday);

        // Set DayImageClickListener as click listener for ImageView elements
        DayImageClickListener dayClickListener = new DayImageClickListener();
        sundayImageView.setOnClickListener(dayClickListener);
        mondayImageView.setOnClickListener(dayClickListener);
        tuesdayImageView.setOnClickListener(dayClickListener);
        wednesdayImageView.setOnClickListener(dayClickListener);
        thursdayImageView.setOnClickListener(dayClickListener);
        fridayImageView.setOnClickListener(dayClickListener);
        saturdayImageView.setOnClickListener(dayClickListener);
    }

    private void showTimePickerDialog(final boolean isStartTime) {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a time picker dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        // Update the corresponding TextView based on whether it's start time or stop time
                        if (isStartTime) {
                            startTimeTextView.setText(time);
                        } else {
                            stopTimeTextView.setText(time);
                        }
                    }
                }, hour, minute, true);

        // Show the time picker dialog
        timePickerDialog.show();
    }
}
