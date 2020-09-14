package com.example.fyp2.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.fyp2.Adapter.AttendancesListAdapter;
import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.Attendance;
import com.example.fyp2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_home extends Fragment {
    View v;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String[] currentDate;
    private TextView morning1, morning2, evening1, evening2, ot1, ot2;
    private ImageView morning, evening, ot;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String[] day = {"morning", "evening", "ot"};
        v = inflater.inflate(R.layout.fragment_home, container, false);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd:MM:yyyy");
        currentDate = dateFormat.format(calendar.getTime()).split(":");
        TextView date = v.findViewById(R.id.home_current_date);
        morning1 = v.findViewById(R.id.home_attendance_morning_1);
        morning2 = v.findViewById(R.id.home_attendance_morning_2);
        evening1 = v.findViewById(R.id.home_attendance_evening_1);
        evening2 = v.findViewById(R.id.home_attendance_evening_2);
        ot1 = v.findViewById(R.id.home_attendance_OT_1);
        ot2 = v.findViewById(R.id.home_attendance_OT_2);
        morning = v.findViewById(R.id.home_attendance_morning_check);
        evening = v.findViewById(R.id.home_attendance_evening_check);
        ot = v.findViewById(R.id.home_attendance_OT_check);
        date.setText((Integer.parseInt(currentDate[0]) + 1) + ":" + currentDate[1] + ":" + currentDate[2]);
        for (String var : day) {
            getAttendance(var, getContext());
        }
        return v;
    }

    public void getAttendance(String text, Context context) {
        Call<List<Attendance>> call = RetrofitClient.getInstance().getApi().currentDayAttendance(text);
        call.enqueue(new Callback<List<Attendance>>() {
            @Override
            public void onResponse(Call<List<Attendance>> call, Response<List<Attendance>> response) {
                if (!response.isSuccessful()) {
                    // Toast.makeText(context, "Get Order History Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<Attendance> attendances = response.body();
                    for (int i = 0; i < attendances.size(); i++) {
                        if (i == 0 && text.equals("morning")) {
                            morning1.setText(attendances.get(i).getTime());
                        } else if (i == 1 && text.equals("morning")) {
                            morning2.setText(attendances.get(i).getTime());
                        }
                        if (attendances.size() == 2 && text.equals("morning")) {
                            morning1.setTextColor(Color.parseColor("#00ff26"));
                            morning2.setTextColor(Color.parseColor("#00ff26"));
                            morning.setColorFilter(Color.parseColor("#00ff26"));
                        }
                        if (i == 0 && text.equals("evening")) {
                            evening1.setText(attendances.get(i).getTime());
                        } else if (i == 1 && text.equals("evening")) {
                            evening2.setText(attendances.get(i).getTime());
                        }
                        if (attendances.size() == 2 && text.equals("evening")) {
                            evening1.setTextColor(Color.parseColor("#00ff26"));
                            evening2.setTextColor(Color.parseColor("#00ff26"));
                            evening.setColorFilter(Color.parseColor("#00ff26"));
                        }
                        if (i == 0 && text.equals("ot")) {
                            ot1.setText(attendances.get(i).getTime());
                        } else if (i == 1 && text.equals("ot")) {
                            ot2.setText(attendances.get(i).getTime());
                        }
                        if (attendances.size() == 2 && text.equals("ot")) {
                            ot1.setTextColor(Color.parseColor("#00ff26"));
                            ot2.setTextColor(Color.parseColor("#00ff26"));
                            ot.setColorFilter(Color.parseColor("#00ff26"));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Attendance>> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }
}