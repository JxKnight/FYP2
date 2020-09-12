package com.example.fyp2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.fyp2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class fragment_home extends Fragment {
    View v;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String currentDate;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd:MM:yyyy");
        currentDate = dateFormat.format(calendar.getTime());
        TextView date = v.findViewById(R.id.home_current_date);
        date.setText(currentDate);
        return v;
    }
}