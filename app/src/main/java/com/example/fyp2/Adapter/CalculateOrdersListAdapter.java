package com.example.fyp2.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fyp2.Class.Attendance;
import com.example.fyp2.Class.CalculateOrders;
import com.example.fyp2.R;

import java.util.ArrayList;

public class CalculateOrdersListAdapter extends ArrayAdapter<CalculateOrders> {

    private static final String TAG = "AdminRoleAdapter";
    private Context mContext;
    int mResource;

    public CalculateOrdersListAdapter(Context context, int resource, ArrayList<CalculateOrders> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // String productID = getItem(position).getProductId();
        String productID = getItem(position).getProductID();
        String productQuantity = getItem(position).getQuantity();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView productIDTV = convertView.findViewById(R.id.calculate_orders_product_ID);
        TextView productQuantityTV = convertView.findViewById(R.id.calculate_orders_quantity_ID);

        if (Integer.parseInt(productQuantity) < 0) {
            productQuantityTV.setTextColor(Color.parseColor("#fc0303"));
        } else {
            productQuantityTV.setTextColor(Color.parseColor("#00ff33"));
        }
        //productsID.append(productID);
        productIDTV.setText(productID);
        productQuantityTV.setText(productQuantity);
        return convertView;
    }
}
