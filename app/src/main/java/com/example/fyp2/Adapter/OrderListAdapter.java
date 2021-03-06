package com.example.fyp2.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.CheckOrder;
import com.example.fyp2.Class.Order;
import com.example.fyp2.Class.Warehouse;
import com.example.fyp2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListAdapter extends ArrayAdapter<CheckOrder> {

    private static final String TAG = "OrderListAdapter";

    ArrayList<Warehouse> warehouseProductList = new ArrayList<>();
    private Context mContext;
    int mResource;

    public OrderListAdapter(Context context, int resource, ArrayList<CheckOrder> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String orderID = getItem(position).getOrdersDate();
        String orderDescription = getItem(position).getOrdersDescription();
        String customerID = getItem(position).getBuyerId();
        String status = getItem(position).getCheckOrder();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView ordersID = (TextView) convertView.findViewById(R.id.OrderIDAdapter);
        TextView ordersDescription = (TextView) convertView.findViewById(R.id.OrderDescriptionAdapter);
        TextView customersID = (TextView) convertView.findViewById(R.id.CustomerIDAdapter);
        LinearLayout lv = (LinearLayout) convertView.findViewById(R.id.order_check_status);

        ordersID.append(orderID);
        ordersDescription.append(orderDescription);
        customersID.append(customerID);
        if (status.equals("true")) {
            lv.setBackgroundColor(Color.parseColor("#31d655"));
        } else {
            lv.setBackgroundColor(Color.parseColor("#e32929"));
        }

        return convertView;
    }
}
