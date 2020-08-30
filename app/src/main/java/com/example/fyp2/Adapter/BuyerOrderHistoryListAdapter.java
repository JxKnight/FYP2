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
import com.example.fyp2.Class.Order;
import com.example.fyp2.R;

import java.util.ArrayList;

public class BuyerOrderHistoryListAdapter extends ArrayAdapter<Order> {

    private static final String TAG = "BuyerOrderHistoryListAdapter";

    private Context mContext;
    int mResource;

    public BuyerOrderHistoryListAdapter(Context context, int resource, ArrayList<Order> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // String productID = getItem(position).getProductId();
        String Date = getItem(position).getOrdersDate();
        String Desc = getItem(position).getOrdersDescription();
        String[] productList = getItem(position).getProductsId().split("/");
        String[] quantityList = getItem(position).getProductsQuantity().split("/");
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView date = (TextView) convertView.findViewById(R.id.Buyer_Order_List_History_Date);
        TextView product = (TextView) convertView.findViewById(R.id.Buyer_Order_List_History_Products);
        TextView desc = (TextView) convertView.findViewById(R.id.Buyer_Order_List_History_Desc);
        //TextView name = (TextView) convertView.findViewById(R.id.Buyer_list_View_Name);
        String x = "";
        for (int i = 0; i < productList.length; i++) {
            x = x + productList[i] + " x " + quantityList[i] + "\n";
        }
        date.append(Date);
        product.setText(x);
        desc.setText(Desc);
        //productsID.append(productID);


        return convertView;
    }
}
