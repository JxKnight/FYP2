package com.example.fyp2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fyp2.Class.Buyer;
import com.example.fyp2.Class.Task;
import com.example.fyp2.R;

import java.util.ArrayList;

public class TaskListAdapter extends ArrayAdapter<String> {

    private static final String TAG = "BuyerListAdapter";

    private Context mContext;
    int mResource;

    public TaskListAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String productID = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView id = (TextView) convertView.findViewById(R.id.task_product_list_id);

        id.setText(productID);

        return convertView;
    }
}
