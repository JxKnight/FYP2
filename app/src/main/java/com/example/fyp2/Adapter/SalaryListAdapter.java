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
import com.example.fyp2.Class.Salary;
import com.example.fyp2.R;

import java.util.ArrayList;

public class SalaryListAdapter extends ArrayAdapter<Salary> {

    private static final String TAG = "BuyerListAdapter";

    private Context mContext;
    int mResource;

    public SalaryListAdapter(Context context, int resource, ArrayList<Salary> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // String productID = getItem(position).getProductId();
        String userIc = getItem(position).getUserIc();
        String salaryAmount = getItem(position).getSalaryAmount();
        String salaryMonth = getItem(position).getSalaryMonth();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView ic = convertView.findViewById(R.id.Salary_list_View_Ic);
        TextView month = convertView.findViewById(R.id.Salary_list_View_Month);
        TextView amount = convertView.findViewById(R.id.Salary_list_View_Amount);


        //productsID.append(productID);
        ic.setText(userIc);
        month.setText(salaryMonth);
        amount.setText(salaryAmount);

        return convertView;
    }
}
