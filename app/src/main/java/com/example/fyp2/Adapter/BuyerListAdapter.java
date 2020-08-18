package com.example.fyp2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fyp2.Class.Buyer;
import com.example.fyp2.R;

import java.util.ArrayList;

public class BuyerListAdapter extends ArrayAdapter<Buyer> {

    private static final String TAG = "BuyerListAdapter";

    private Context mContext;
    int mResource;

    public BuyerListAdapter(Context context, int resource, ArrayList<Buyer> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // String productID = getItem(position).getProductId();
        String buyerName = getItem(position).getBuyerName();
        String buyerContact = getItem(position).getBuyerContact();
        String buyerLocation = getItem(position).getBuyerLocation();
        String buyerAddress = getItem(position).getBuyerAddress();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView name = (TextView) convertView.findViewById(R.id.Buyer_list_View_Name);
        TextView contact = (TextView) convertView.findViewById(R.id.Buyer_List_View_Contact);
        TextView location = (TextView) convertView.findViewById(R.id.Buyer_List_View_Location);
        TextView address = (TextView) convertView.findViewById(R.id.Buyer_List_View_Address);

        //productsID.append(productID);
        name.append(buyerName);
        contact.append(buyerContact);
        location.append(buyerLocation);
        address.append(buyerAddress);

        return convertView;
    }
}
