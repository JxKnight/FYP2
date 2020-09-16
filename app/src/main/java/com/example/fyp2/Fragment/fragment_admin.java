package com.example.fyp2.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.fyp2.Adapter.AdminRoleAdapter;
import com.example.fyp2.Adapter.AdminSalaryAdapter;
import com.example.fyp2.Adapter.AdminUserAdapter;
import com.example.fyp2.Adapter.AdminUserRolesAdapter;
import com.example.fyp2.Adapter.BuyerListAdapter;
import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.Attendance;
import com.example.fyp2.Class.Buyer;
import com.example.fyp2.Class.Role;
import com.example.fyp2.Class.Salary;
import com.example.fyp2.Class.User;
import com.example.fyp2.Class.Warehouse;
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
    ArrayList<Attendance> MonthlyAttendancesList = new ArrayList<>();
    ArrayList<User> usersList = new ArrayList<>();
    SwipeMenuListView lvRoles;
    ListView lvUsers, lvUsersRolesList;
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
        Button adminSalaryList = v.findViewById(R.id.admin_salary_list);
        adminRole.setOnClickListener(q -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view = getLayoutInflater().inflate(R.layout.dialog_admin_roles, null);
            getRoles(getContext(), "roles");
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
            Button update = view.findViewById(R.id.fragment_admin_role_update_btn);
            update.setVisibility(View.GONE);
            lvRoles = view.findViewById(R.id.fragment_admin_roles_list_view);

            builder.setView(view);
            AlertDialog rolesDialog = builder.create();
            rolesDialog.show();
            rolesDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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
                if (name.getText().toString().isEmpty() || num.getText().toString().isEmpty() || desc.getText().toString().isEmpty() || rate.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please complete all roles details", Toast.LENGTH_SHORT).show();
                } else {
                    Role role = new Role(name.getText().toString(), num.getText().toString(), desc.getText().toString(), rate.getText().toString(), wh, od, ctm, rp, t);
                    createRoles(role, getContext());
                    name.setText("");
                    num.setText("");
                    desc.setText("");
                    rate.setText("");
                    warehouse.setChecked(false);
                    orders.setChecked(false);
                    customers.setChecked(false);
                    tasks.setChecked(false);
                    reports.setChecked(false);
                }
            });
            SwipeMenuCreator creator = new SwipeMenuCreator() {

                @Override
                public void create(SwipeMenu menu) {
                    // create "open" item
                    SwipeMenuItem openItem = new SwipeMenuItem(
                            getActivity());
                    // create "delete" item
                    SwipeMenuItem deleteItem = new SwipeMenuItem(
                            getActivity());
                    // set item background
                    deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                            0x3F, 0x25)));
                    // set item width
                    deleteItem.setWidth(150);
                    // set a icon
                    deleteItem.setIcon(R.drawable.icon_delete);
                    // add to menu
                    menu.addMenuItem(deleteItem);
                }
            };
            lvRoles.setMenuCreator(creator);

            lvRoles.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    switch (index) {
                        case 0:
                            AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                            alertbox.setTitle("Warning");
                            alertbox.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteRoles(rolesList.get(position).getRoleNum(), v.getContext());
                                }
                            });
                            alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    rolesDialog.show();
                                }
                            });
                            alertbox.show();
                            break;
                    }
                    // false : close the menu; true : not close the menu
                    return false;
                }
            });

            lvRoles.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    name.setText(rolesList.get(position).getRoleName());
                    num.setEnabled(false);
                    num.setText(rolesList.get(position).getRoleNum());
                    desc.setText(rolesList.get(position).getRoleDescription());
                    rate.setText(rolesList.get(position).getRoleRate());
                    if (rolesList.get(position).getWarehouse().equals("1")) {
                        warehouse.setChecked(true);
                    } else {
                        warehouse.setChecked(false);
                    }

                    if (rolesList.get(position).getOrders().equals("1")) {
                        orders.setChecked(true);
                    } else {
                        orders.setChecked(false);
                    }

                    if (rolesList.get(position).getCustomers().equals("1")) {
                        customers.setChecked(true);
                    } else {
                        customers.setChecked(false);
                    }

                    if (rolesList.get(position).getTasks().equals("1")) {
                        tasks.setChecked(true);
                    } else {
                        tasks.setChecked(false);
                    }

                    if (rolesList.get(position).getReports().equals("1")) {
                        reports.setChecked(true);
                    } else {
                        reports.setChecked(false);
                    }
                    submit.setVisibility(View.GONE);
                    update.setVisibility(View.VISIBLE);
                    return false;
                }
            });

            update.setOnClickListener(w -> {
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
                if (name.getText().toString().isEmpty() || num.getText().toString().isEmpty() || desc.getText().toString().isEmpty() || rate.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please complete all roles details", Toast.LENGTH_SHORT).show();
                } else {
                    Role role = new Role(name.getText().toString(), num.getText().toString(), desc.getText().toString(), rate.getText().toString(), wh, od, ctm, rp, t);
                    updateRoles(role, getContext());
                    name.setText("");
                    num.setText("");
                    desc.setText("");
                    rate.setText("");
                    warehouse.setChecked(false);
                    orders.setChecked(false);
                    customers.setChecked(false);
                    tasks.setChecked(false);
                    reports.setChecked(false);
                }
            });
        });

        adminSalaryList.setOnClickListener(w -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view = getLayoutInflater().inflate(R.layout.dialog_admin_users, null);
            lvUsers = view.findViewById(R.id.fragment_admin_users_list_view);
            getUsers(getContext());
            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.show();
            lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder builderr = new AlertDialog.Builder(getActivity());
                    View vieww = getLayoutInflater().inflate(R.layout.dialog_admin_salary, null);

                    Spinner selectMonthSpinner = vieww.findViewById(R.id.admin_salary_month_spinner);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.month, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    selectMonthSpinner.setAdapter(adapter);


                    ImageView searchMonth = vieww.findViewById(R.id.admin_salary_month_search);
                    TextView totalHours = vieww.findViewById(R.id.admin_salary_month_total_hours);
                    TextView totalAmount = vieww.findViewById(R.id.admin_salary_month_total_salary);
                    ListView currentUserSalaryList = vieww.findViewById(R.id.admin_salary_all_salary_list_view);
                    final AdminSalaryAdapter adapterr = new AdminSalaryAdapter(getActivity(), R.layout.adapter_admin_user_attendance_list, MonthlyAttendancesList);
                    adapterr.notifyDataSetChanged();
                    getUsers(getContext());
                    builderr.setView(vieww);
                    AlertDialog dialog = builderr.create();
                    dialog.show();

                    searchMonth.setOnClickListener(e -> {
                        MonthlyAttendancesList.clear();
                        currentUserSalaryList.setAdapter(null);
                        Call<Salary> call = RetrofitClient.getInstance().getApi().monthlySalary(selectMonthSpinner.getSelectedItem().toString(), usersList.get(position).getUserIc());
                        call.enqueue(new Callback<Salary>() {
                            @Override
                            public void onResponse(Call<Salary> call, Response<Salary> response) {
                                if (!response.isSuccessful()) {
                                    Toast.makeText(getContext(), "No Record Found", Toast.LENGTH_LONG).show();
                                    totalHours.setText("0");
                                    totalAmount.setText("0");
                                } else {
                                    totalHours.setText(response.body().getTotalHours());
                                    totalAmount.setText(response.body().getAmount());
                                }
                            }

                            @Override
                            public void onFailure(Call<Salary> call, Throwable t) {
                                Toast.makeText(getContext(), "Fail to connect to server", Toast.LENGTH_LONG).show();
                            }
                        });
                        Call<List<Attendance>> call1 = RetrofitClient.getInstance().getApi().MonthlyAttendance(selectMonthSpinner.getSelectedItem().toString(), usersList.get(position).getUserIc());
                        call1.enqueue(new Callback<List<Attendance>>() {
                            @Override
                            public void onResponse(Call<List<Attendance>> call, Response<List<Attendance>> response) {
                                if (!response.isSuccessful()) {
                                    Toast.makeText(getContext(), "No Record Found", Toast.LENGTH_LONG).show();
                                } else {
                                    List<Attendance> attendances = response.body();
                                    for (Attendance current : attendances) {
                                        MonthlyAttendancesList.add(current);
                                    }
                                    currentUserSalaryList.setAdapter(adapterr);
                                    //Toast.makeText(context,buyerList.get(0).getBuyerId(),Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Attendance>> call, Throwable t) {
                                Toast.makeText(getContext(), "Fail to connect to server", Toast.LENGTH_LONG).show();
                            }
                        });
                    });
                }
            });

            lvUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                    View mView = getLayoutInflater().inflate(R.layout.dialog_admin_users_assign_role, null);
                    lvUsersRolesList = mView.findViewById(R.id.fragment_admin_users_assign_roles_list_view);
                    getRoles(getContext(), "users");
                    mBuilder.setView(mView);
                    AlertDialog assignUserDialog = mBuilder.create();
                    assignUserDialog.show();
                    lvUsersRolesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            User user = new User(usersList.get(position).getUserIc(), usersList.get(position).getFirstName(), usersList.get(position).getLastName(), rolesList.get(position).getRoleNum());
                            updateUserRoles(user, getContext());
                            assignUserDialog.dismiss();
                            dialog.dismiss();
                        }
                    });
                    return false;
                }
            });
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
                        if (currentUser.getRole().equals("1")) {

                        } else {
                            usersList.add(currentUser);
                        }
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

    public void getRoles(Context context, String x) {
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
                        if (currentRole.getRoleNum().equals("0") || currentRole.getRoleNum().equals("1")) {

                        } else {
                            rolesList.add(currentRole);
                        }
                    }
                    if (x.equals("roles")) {
                        final AdminRoleAdapter adapter = new AdminRoleAdapter(getActivity(), R.layout.adapter_role_list, rolesList);
                        adapter.notifyDataSetChanged();
                        lvRoles.setAdapter(adapter);
                    } else {
                        final AdminUserRolesAdapter adapter = new AdminUserRolesAdapter(getActivity(), R.layout.adapter_admin_users_role_list, rolesList);
                        adapter.notifyDataSetChanged();
                        lvUsersRolesList.setAdapter(adapter);
                    }
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
                getRoles(getContext(), "roles");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Fail To Connect To Server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateRoles(Role role, Context context) {
        Call<Void> call = RetrofitClient.getInstance().getApi().createRole(role);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Add Role Fail", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Add Role Successfully", Toast.LENGTH_LONG).show();
                }
                getRoles(getContext(), "roles");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Fail To Connect To Server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteRoles(String x, Context context) {
        Call<Role> call = RetrofitClient.getInstance().getApi().deleteRoles(x);
        call.enqueue(new Callback<Role>() {
            @Override
            public void onResponse(Call<Role> call, Response<Role> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Delete Fail", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Role> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateUserRoles(User user, Context context) {
        Call<User> call = RetrofitClient.getInstance().getApi().updateUserRole(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Update User Role Fail", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Update User Role Successfully", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Fail To Connect To Server", Toast.LENGTH_LONG).show();
            }
        });
    }
}