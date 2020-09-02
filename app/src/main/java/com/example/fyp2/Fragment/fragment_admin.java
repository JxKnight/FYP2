package com.example.fyp2.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.fyp2.Adapter.AdminRoleAdapter;
import com.example.fyp2.Adapter.AdminUserAdapter;
import com.example.fyp2.Adapter.BuyerListAdapter;
import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.Buyer;
import com.example.fyp2.Class.Role;
import com.example.fyp2.Class.User;
import com.example.fyp2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class fragment_admin extends Fragment {
    View v;
    ArrayList<Role> rolesList = new ArrayList<>();
    ArrayList<User> usersList = new ArrayList<>();
    ListView lvRoles, lvUsers;
    String userIc, role;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_admin, container, false);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("BOM_PREFS", MODE_PRIVATE);
        userIc = sharedPreferences.getString("USERIC", "");
        role = sharedPreferences.getString("ROLE", "");

        Toast.makeText(getActivity(), "Welcome Admin", Toast.LENGTH_LONG).show();
        Button adminRole = v.findViewById(R.id.admin_roles);
        Button adminUsers = v.findViewById(R.id.admin_users);
        Button adminSalaryLists = v.findViewById(R.id.admin_salary);
        adminRole.setOnClickListener(q -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view = getLayoutInflater().inflate(R.layout.dialog_admin_roles, null);
            getRoles(getContext());
            EditText name = view.findViewById(R.id.fragment_admin_role_name);
            EditText num = view.findViewById(R.id.fragment_admin_role_num);
            EditText desc = view.findViewById(R.id.fragment_admin_role_description);
            EditText rate = view.findViewById(R.id.fragment_admin_role_rate);

            CheckBox warehouse = view.findViewById(R.id.admin_roles_warehouse);
            CheckBox orders = view.findViewById(R.id.admin_roles_orders);
            CheckBox customers = view.findViewById(R.id.admin_roles_customers);
            CheckBox tasks = view.findViewById(R.id.admin_roles_tasks);
            CheckBox reports = view.findViewById(R.id.admin_roles_reports);

            Button submit = view.findViewById(R.id.fragment_admin_role_submit_btn);
            lvRoles = view.findViewById(R.id.fragment_admin_roles_list_view);

            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.show();
            submit.setOnClickListener(w -> {
                String wh, od, ctm, t, rp;
                if (warehouse.isChecked()) {
                    wh = "1";
                } else {
                    wh = "0";
                }

                if (orders.isChecked()) {
                    od = "1";
                } else {
                    od = "0";
                }

                if (customers.isChecked()) {
                    ctm = "1";
                } else {
                    ctm = "0";
                }

                if (tasks.isChecked()) {
                    t = "1";
                } else {
                    t = "0";
                }

                if (reports.isChecked()) {
                    rp = "1";
                } else {
                    rp = "0";
                }
                lvRoles.setAdapter(null);
                Role role = new Role(name.getText().toString(), num.getText().toString(), desc.getText().toString(), rate.getText().toString(), wh, od, ctm, rp, t);
                createRoles(role, getContext());
                getRoles(getContext());
            });
        });

        adminUsers.setOnClickListener(w -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view = getLayoutInflater().inflate(R.layout.dialog_admin_users, null);
            lvUsers = view.findViewById(R.id.fragment_admin_users_list_view);
            getUsers(getContext());
            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        adminSalaryLists.setOnClickListener(e -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view = getLayoutInflater().inflate(R.layout.dialog_admin_users, null);
            lvUsers = view.findViewById(R.id.fragment_admin_users_list_view);
            getUsers(getContext());
            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        return v;
    }

    public void getUsers(Context context) {
        lvUsers.setAdapter(null);
        usersList.clear();
        Call<List<User>> call = RetrofitClient.getInstance().getApi().findAllUser();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Get Users Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<User> users = response.body();
                    for (User currentUser : users) {
                        usersList.add(currentUser);
                    }
                    final AdminUserAdapter adapter = new AdminUserAdapter(getActivity(), R.layout.adapter_users_list, usersList);
                    adapter.notifyDataSetChanged();
                    lvUsers.setAdapter(adapter);
                    //Toast.makeText(context,rolesList.get(0).getRoleName(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getRoles(Context context) {
        rolesList.clear();
        Call<List<Role>> call = RetrofitClient.getInstance().getApi().findAllRoles();
        call.enqueue(new Callback<List<Role>>() {
            @Override
            public void onResponse(Call<List<Role>> call, Response<List<Role>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Get Roles Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<Role> roles = response.body();
                    for (Role currentRole : roles) {
                        rolesList.add(currentRole);
                    }
                    final AdminRoleAdapter adapter = new AdminRoleAdapter(getActivity(), R.layout.adapter_role_list, rolesList);
                    adapter.notifyDataSetChanged();
                    lvRoles.setAdapter(adapter);
                    //Toast.makeText(context,rolesList.get(0).getRoleName(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Role>> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createRoles(Role role, Context context) {
        Call<Void> call = RetrofitClient.getInstance().getApi().createRole(role);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Add Role Fail", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Add Role Successfully", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Fail To Connect To Server", Toast.LENGTH_LONG).show();
            }
        });
    }
}