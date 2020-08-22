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
import com.example.fyp2.Class.Product;
import com.example.fyp2.R;

import java.util.ArrayList;

public class ProductListAdapter extends ArrayAdapter<Product> {

    private static final String TAG = "ProductListAdapter";

    private Context mContext;
    int mResource;

    public ProductListAdapter(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String productID = getItem(position).getProductsId();
        String productDescription = getItem(position).getProductsDescription();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView productsID = (TextView) convertView.findViewById(R.id.ProductIdAdapter);
        TextView productsDescription = (TextView) convertView.findViewById(R.id.ProductDescriptionAdapter);

        productsID.append(productID);
        productsDescription.setText(productDescription);

        return convertView;
    }
}
