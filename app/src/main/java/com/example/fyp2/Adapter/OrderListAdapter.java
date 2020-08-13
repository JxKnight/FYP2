package com.example.fyp2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fyp2.Class.Order;
import com.example.fyp2.R;

import java.util.ArrayList;

public class OrderListAdapter extends ArrayAdapter<Order> {

    private static final String TAG = "OrderListAdapter";

    private Context mContext;
    int mResource;

    public OrderListAdapter(Context context, int resource, ArrayList<Order> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String orderID = getItem(position).getOrdersId();
        String orderDescription = getItem(position).getOrdersDescription();
        String customerID = getItem(position).getBuyerId();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView ordersID = (TextView) convertView.findViewById(R.id.OrderIDAdapter);
        TextView ordersDescription = (TextView) convertView.findViewById(R.id.OrderDescriptionAdapter);
        TextView customersID = (TextView) convertView.findViewById(R.id.CustomerIDAdapter);

        ordersID.append(orderID);
        ordersDescription.append(orderDescription);
        customersID.append(customerID);

        return convertView;
    }
}
