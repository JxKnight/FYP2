package com.example.fyp2.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.fyp2.Adapter.AdminRoleAdapter;
import com.example.fyp2.Adapter.BuyerListAdapter;
import com.example.fyp2.Adapter.BuyerOrderHistoryListAdapter;
import com.example.fyp2.Adapter.TaskDescriptionListAdapter;
import com.example.fyp2.Adapter.TaskListAdapter;
import com.example.fyp2.Adapter.TaskRolesListAdapter;
import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.Buyer;
import com.example.fyp2.Class.Order;
import com.example.fyp2.Class.Role;
import com.example.fyp2.Class.Task;
import com.example.fyp2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class fragment_tasks extends Fragment {
    View v;
    ArrayList<Task> TaskList = new ArrayList<>();
    ArrayList<String> TaskProductList = new ArrayList<>();
    ArrayList<Task> tasks = new ArrayList<>();
    ArrayList<Role> RolesList = new ArrayList<>();
    ListView LV;
    String userIc, roles;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_tasks, container, false);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("BOM_PREFS", MODE_PRIVATE);
        userIc = sharedPreferences.getString("USERIC", "");
        roles = sharedPreferences.getString("ROLE", "");
        getRoles(getContext());
        LV = v.findViewById(R.id.task_product_list);
        getTaskList(getContext());
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tasks.clear();
                AlertDialog.Builder Builder = new AlertDialog.Builder(getActivity());
                View View = getLayoutInflater().inflate(R.layout.dialog_task_details_list, null);
                TextView name = (TextView) View.findViewById(R.id.fragment_task_details_product_id);
                ListView lv = View.findViewById(R.id.fragment_task_details_description_list);
                name.setText(TaskProductList.get(position));
                for (Task var : TaskList) {
                    if (var.getProductsId().equals(TaskProductList.get(position))) {
                        tasks.add(var);
                    }
                }
                final TaskDescriptionListAdapter adapter = new TaskDescriptionListAdapter(getActivity(), R.layout.adapter_task_detail_description, tasks);
                adapter.notifyDataSetChanged();
                lv.setAdapter(adapter);
                Builder.setView(View);
                AlertDialog dialogDetail = Builder.create();
                dialogDetail.show();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, android.view.View view, int k, long id) {
                        if (roles.equals("1")) {

                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                            View mView = getLayoutInflater().inflate(R.layout.dialog_task_details_assign_roles_list, null);
                            ListView lv = mView.findViewById(R.id.fragment_task_details_roles_list);
                            final TaskRolesListAdapter adapter = new TaskRolesListAdapter(getActivity(), R.layout.adapter_task_roles_list, RolesList);
                            adapter.notifyDataSetChanged();
                            lv.setAdapter(adapter);
                            mBuilder.setView(mView);
                            AlertDialog dialogRoles = mBuilder.create();
                            dialogRoles.show();
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, android.view.View view, int i, long id) {
                                    dialogRoles.dismiss();
                                    dialogDetail.dismiss();
                                    Task task = new Task(tasks.get(k).getTaskId(), RolesList.get(i).getRoleNum());
                                    Call<Task> call = RetrofitClient.getInstance().getApi().updateRole(task);
                                    call.enqueue(new Callback<Task>() {
                                        @Override
                                        public void onResponse(Call<Task> call, Response<Task> response) {
                                            if (!response.isSuccessful()) {
                                                Toast.makeText(getContext(), "Update Task Roles Fail", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getContext(), "Update Task Roles Success", Toast.LENGTH_LONG).show();
                                                LV.setAdapter(null);
                                                getTaskList(getContext());
                                                tasks.clear();
                                                TaskList.clear();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Task> call, Throwable t) {
                                            Toast.makeText(getContext(), "Fail to connect to server", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "You don't have permission to access", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        return v;
    }

    public void getTaskList(Context context) {
        Call<List<Task>> call = RetrofitClient.getInstance().getApi().findAllTask();
        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Get Tasks Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<String> unlist = new ArrayList<>();
                    List<Task> tasks = response.body();
                    for (Task currentTask : tasks) {
                        TaskList.add(currentTask);
                        unlist.add(currentTask.getProductsId());
                    }
                    LinkedHashSet<String> hashSet = new LinkedHashSet<>(unlist);
                    TaskProductList = new ArrayList<>(hashSet);
                    final TaskListAdapter adapter = new TaskListAdapter(getActivity(), R.layout.adapter_task_list, TaskProductList);
                    adapter.notifyDataSetChanged();
                    LV.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
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