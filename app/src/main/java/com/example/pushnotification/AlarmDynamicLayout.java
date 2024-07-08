package com.example.pushnotification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class AlarmDynamicLayout {

    private Context context;

    public AlarmDynamicLayout(Context context) {
        this.context = context;
    }

    public void addDynamicViews(LinearLayout container) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dynamicLayout = inflater.inflate(R.layout.alarm_dynamic_adder, null);
        container.addView(dynamicLayout);
    }
}