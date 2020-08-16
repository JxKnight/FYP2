package com.example.fyp2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fyp2.Class.OrderCartSession;
import com.example.fyp2.Class.Product;
import com.example.fyp2.R;

import java.util.ArrayList;

public class ProductOrderListAdapter extends ArrayAdapter<OrderCartSession> {

    private static final String TAG = "ProductListAdapter";

    private Context mContext;
    int mResource;

    public ProductOrderListAdapter(Context context, int resource, ArrayList<OrderCartSession> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String productID = getItem(position).getProductId();
        String productQuantity = getItem(position).getOrderQuantity();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView productsID = (TextView) convertView.findViewById(R.id.Product_Order_Cart_Product_ID);
        TextView productsQuantity = (TextView) convertView.findViewById(R.id.Product_Order_Cart_Product_Quantity);

        productsID.append(productID);
        productsQuantity.append(productQuantity);

        return convertView;
    }
}
