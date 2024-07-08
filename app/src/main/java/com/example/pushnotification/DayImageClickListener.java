package com.example.pushnotification;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

public class DayImageClickListener implements View.OnClickListener {

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