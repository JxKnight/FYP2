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
import com.example.fyp2.R;

import java.util.ArrayList;

public class AttendancesListAdapter extends ArrayAdapter<Attendance> {

    private static final String TAG = "AdminRoleAdapter";
    private Context mContext;
    int mResource;

    public AttendancesListAdapter(Context context, int resource, ArrayList<Attendance> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // String productID = getItem(position).getProductId();
        String userName = getItem(position).getUserName();
        String userTime = getItem(position).getTime();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView name = convertView.findViewById(R.id.fragment_attendance_admin_check_name);
        TextView time = convertView.findViewById(R.id.fragment_attendance_admin_check_time);

        //productsID.append(productID);
        name.setText(userName);
        time.setText(userTime);


        return convertView;
    }
}
