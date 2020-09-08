package com.example.fyp2.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fyp2.Class.Buyer;
import com.example.fyp2.Class.Role;
import com.example.fyp2.R;

import java.util.ArrayList;

public class AdminRoleAdapter extends ArrayAdapter<Role> {

    private static final String TAG = "AdminRoleAdapter";

    private Context mContext;
    int mResource;

    public AdminRoleAdapter(Context context, int resource, ArrayList<Role> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // String productID = getItem(position).getProductId();
        String name = getItem(position).getRoleName();
        String desc = getItem(position).getRoleDescription();
        String rate = getItem(position).getRoleRate();
        String warehouse = getItem(position).getWarehouse();
        String order = getItem(position).getOrders();
        String customer = getItem(position).getCustomers();
        String reports = getItem(position).getReports();
        String tasks = getItem(position).getTasks();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView roleName = (TextView) convertView.findViewById(R.id.admin_roles_name);
        TextView roleDesc = (TextView) convertView.findViewById(R.id.admin_roles_desc);
        TextView roleRate = (TextView) convertView.findViewById(R.id.admin_roles_rate);
        TextView roleWarehouse = (TextView) convertView.findViewById(R.id.admin_roles_adapter_warehouse);
        TextView roleOrder = (TextView) convertView.findViewById(R.id.admin_roles_adapter_order);
        TextView roleCustomer = (TextView) convertView.findViewById(R.id.admin_roles_adapter_customer);
        TextView roleReports = (TextView) convertView.findViewById(R.id.admin_roles_adapter_reports);
        TextView roleTasks = (TextView) convertView.findViewById(R.id.admin_roles_adapter_Tasks);

        //productsID.append(productID);
        roleName.append(name);
        roleDesc.setText(desc);
        roleRate.append("RM" + rate + "/hour");
        if (warehouse.equals("1")) {
            roleWarehouse.setTextColor(Color.parseColor("#00e329"));
        }
        if (order.equals("1")) {
            roleOrder.setTextColor(Color.parseColor("#00e329"));
        }
        if (customer.equals("1")) {
            roleCustomer.setTextColor(Color.parseColor("#00e329"));
        }
        if (reports.equals("1")) {
            roleReports.setTextColor(Color.parseColor("#00e329"));
        }
        if (tasks.equals("1")) {
            roleTasks.setTextColor(Color.parseColor("#00e329"));
        }

        return convertView;
    }
}
