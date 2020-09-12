package com.example.fyp2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fyp2.Class.Role;
import com.example.fyp2.R;

import java.util.ArrayList;

public class TaskRolesListAdapter extends ArrayAdapter<Role> {

    private static final String TAG = "BuyerListAdapter";

    private Context mContext;
    int mResource;

    public TaskRolesListAdapter(Context context, int resource, ArrayList<Role> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String productID = getItem(position).getRoleName();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView id = (TextView) convertView.findViewById(R.id.task_product_roles_name);

        id.setText(productID);

        return convertView;
    }
}
