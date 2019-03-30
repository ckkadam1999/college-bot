package com.webhostapp.collegebot;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfAdapter extends ArrayAdapter<String> {

    private Context context;

    ProfAdapter(@NonNull Context context, String[] devices) {
        super(context, 0, devices);
        this.context  = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try {
            JSONObject device = new JSONObject(getItem(position));
            Log.i("JSON",device.toString());
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
            }
            TextView name = convertView.findViewById(R.id.name);
            TextView subject = convertView.findViewById(R.id.subject);
            name.setText(device.getString("name"));
            subject.setText(device.getString("subject"));
            return convertView;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

