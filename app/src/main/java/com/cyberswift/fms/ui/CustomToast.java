package com.cyberswift.fms.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cyberswift.fms.R;

public class CustomToast extends Toast {
 
    public CustomToast(Context activityContext, String message, int duration) {
        super(activityContext);
        this.setDuration(duration);
        LayoutInflater inflater = (LayoutInflater) activityContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_toast_layout, (ViewGroup) ((Activity) activityContext).findViewById(R.id.toast));
        this.setView(view);
        TextView tv = (TextView) view.findViewById(R.id.tv_message);
        tv.setText(message);
    }
    
    public CustomToast(Context activityContext, int resId, int duration) {
        super(activityContext);
        this.setDuration(duration);
        LayoutInflater inflater = (LayoutInflater) activityContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_toast_layout, (ViewGroup) ((Activity) activityContext).findViewById(R.id.toast));
        this.setView(view);
        TextView tv = (TextView) view.findViewById(R.id.tv_message);
        tv.setText(activityContext.getResources().getText(resId));
    }
}
