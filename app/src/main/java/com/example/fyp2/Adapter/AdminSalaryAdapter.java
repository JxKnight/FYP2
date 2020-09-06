package com.example.fyp2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fyp2.Class.Attendance;
import com.example.fyp2.Class.Salary;
import com.example.fyp2.Class.User;
import com.example.fyp2.R;

import java.util.ArrayList;

public class AdminSalaryAdapter extends ArrayAdapter<Attendance> {

    private static final String TAG = "AdminSalaryAdapter";
    private Context mContext;
    int mResource;

    public AdminSalaryAdapter(Context context, int resource, ArrayList<Attendance> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // String productID = getItem(position).getProductId();
        String name = getItem(position).getUserName();
        String date = getItem(position).getDate();
        String time = getItem(position).getTime();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView displayName = convertView.findViewById(R.id.admin_users_attendance_list_name);
        TextView displayDate = convertView.findViewById(R.id.admin_users_attendance_list_date);
        TextView displayTime = convertView.findViewById(R.id.admin_users_attendance_list_time);


        //productsID.append(productID);
        displayName.setText(name);
        displayDate.setText(date);
        displayTime.setText(time);

        return convertView;
    }
}
