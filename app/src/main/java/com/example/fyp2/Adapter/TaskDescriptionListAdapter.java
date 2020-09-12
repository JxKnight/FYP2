package com.example.fyp2.Adapter;

import android.content.Context;
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
import com.example.fyp2.Class.Task;
import com.example.fyp2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskDescriptionListAdapter extends ArrayAdapter<Task> {

    private static final String TAG = "BuyerListAdapter";

    private Context mContext;
    int mResource;
    ArrayList<Role> RolesList = new ArrayList<>();

    public TaskDescriptionListAdapter(Context context, int resource, ArrayList<Task> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        getRoles(getContext());
        String desc = getItem(position).getTaskDescription();
        String roleNum = getItem(position).getUserRole();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView descTV = (TextView) convertView.findViewById(R.id.task_product_list_detail_description);
        TextView roleTV = (TextView) convertView.findViewById(R.id.task_product_list_detail_roles);

        descTV.setText(desc);
        roleTV.setText(roleNum);
//        for(Role var:RolesList){
//            if(roleNum.equals(var.getRoleNum())){
//                roleTV.setText(var.getRoleName());
//            }
//        }

        return convertView;
    }

    public void getRoles(Context context) {
        RolesList.clear();
        Call<List<Role>> call = RetrofitClient.getInstance().getApi().findAllRoles();
        call.enqueue(new Callback<List<Role>>() {
            @Override
            public void onResponse(Call<List<Role>> call, Response<List<Role>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Get Roles Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<Role> roles = response.body();
                    for (Role currentRole : roles) {
                        if (currentRole.getRoleName().equals("NoRole") || currentRole.getRoleName().equals("Admin")) {

                        } else {
                            RolesList.add(currentRole);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Role>> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }
}
