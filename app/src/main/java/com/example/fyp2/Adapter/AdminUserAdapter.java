package com.example.fyp2.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.Role;
import com.example.fyp2.Class.User;
import com.example.fyp2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUserAdapter extends ArrayAdapter<User> {

    private static final String TAG = "AdminUserAdapter";
    private Context mContext;
    int mResource;

    public AdminUserAdapter(Context context, int resource, ArrayList<User> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // String productID = getItem(position).getProductId();
        String FName = getItem(position).getFirstName();
        String LName = getItem(position).getLastName();
        String userIC = getItem(position).getUserIc();
        String userRole = getItem(position).getRole();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView name = convertView.findViewById(R.id.admin_roles_name);
        TextView IC = convertView.findViewById(R.id.admin_users_ic);
        TextView Role = convertView.findViewById(R.id.admin_users_roles);
        //productsID.append(productID);
        name.setText(LName + " " + FName);
        IC.setText(userIC);
        Role.setText(userRole);

        return convertView;
    }
}
