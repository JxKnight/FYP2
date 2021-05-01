package com.example.fyp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.Role;
import com.example.fyp2.Class.User;
import com.example.fyp2.Fragment.fragment_admin;
import com.example.fyp2.Fragment.fragment_attendance;
import com.example.fyp2.Fragment.fragment_buyers;
import com.example.fyp2.Fragment.fragment_home;
import com.example.fyp2.Fragment.fragment_orders;
import com.example.fyp2.Fragment.fragment_products;
import com.example.fyp2.Fragment.fragment_profile;
import com.example.fyp2.Fragment.fragment_tasks;
import com.example.fyp2.Fragment.fragment_warehouse;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FragmentTransaction t;
    private String userIc, firstEntry, role, warehouse = "false", orders = "false", customers = "false", reports = "false", tasks = "false";
    private TextView name, roleName;
    private ImageView profile;
    private Menu menu;
    private MenuItem navWarehouse, navTasks, navOrders, navCustomer, navAdmin;

    public static final String SHARED_PREFS = "BOM_PREFS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences("BOM_PREFS", MODE_PRIVATE);
        userIc = sharedPreferences.getString("USERIC", "");
        firstEntry = sharedPreferences.getString("FIRSTENTRY", "");
        role = sharedPreferences.getString("ROLE", "");

        if (firstEntry.equals("true")) {
            drawerLayout.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_profile()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_home()).commit();
        }

        User user = new User(userIc);
        ViewProfile(user);
        saveData(role);
        t = getSupportFragmentManager().beginTransaction();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        navigationView.bringToFront(); // light up effect
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        View v = navigationView.getHeaderView(0);
        name = v.findViewById(R.id.username_header);
        roleName = v.findViewById(R.id.role_header);
        profile = v.findViewById(R.id.HeaderProfilePic);
        profile.setOnClickListener(u -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_profile()).commit();
        });

        menu = navigationView.getMenu();
        navWarehouse = menu.findItem(R.id.nav_warehouse);
        navOrders = menu.findItem(R.id.nav_orders);
        navCustomer = menu.findItem(R.id.nav_customer);
        navAdmin = menu.findItem(R.id.nav_admin);
        navTasks = menu.findItem(R.id.nav_tasks);

        navTasks.setVisible(false);
        navWarehouse.setVisible(false);
        navCustomer.setVisible(false);
        navAdmin.setVisible(false);
        navOrders.setVisible(false);

        if (role.equals("1")) {
            navAdmin.setVisible(true);
        }
        saveData(role);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_warehouse:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_warehouse()).commit();
                break;
            case R.id.nav_products:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_products()).commit();
                break;
            case R.id.nav_orders:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_orders()).commit();
                break;
            case R.id.nav_attendance:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_attendance()).commit();
                break;
            case R.id.nav_customer:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_buyers()).commit();
                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_home()).commit();
                break;
            case R.id.nav_tasks:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_tasks()).commit();
                break;
            case R.id.nav_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_admin()).commit();
                break;
            case R.id.nav_logout:
               finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    public void saveData(String roles) {
        Role role = new Role(roles);
        getRole(role);
    }

    public void getRole(Role role) {
        Call<Role> call = RetrofitClient.getInstance().getApi().currentRole(role);
        call.enqueue(new Callback<Role>() {
            @Override
            public void onResponse(Call<Role> call, Response<Role> response) {
                Role postResponse = response.body();
                roleName.setText(postResponse.getRoleName());
                if (postResponse.getTasks().equals("1")) {
                    tasks = "true";
                    navTasks.setVisible(true);
                }
                if (postResponse.getWarehouse().equals("1")) {
                    warehouse = "true";
                    navWarehouse.setVisible(true);
                }
                if (postResponse.getReports().equals("1")) {
                    reports = "true";
                }
                if (postResponse.getOrders().equals("1")) {
                    orders = "true";
                    navOrders.setVisible(true);
                }
                if (postResponse.getCustomers().equals("1")) {
                    customers = "true";
                    navCustomer.setVisible(true);
                }
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (tasks.equals("true")) {
                    editor.putString("role-tasks", "true");
                } else {
                    editor.putString("role-tasks", "false");
                }
                if (warehouse.equals("true")) {
                    editor.putString("role-warehouse", "true");
                } else {
                    editor.putString("role-warehouse", "false");
                }
                if (reports.equals("true")) {
                    editor.putString("role-reports", "true");
                } else {
                    editor.putString("role-reports", "false");
                }
                if (orders.equals("true")) {
                    editor.putString("role-orders", "true");
                } else {
                    editor.putString("role-orders", "false");
                }
                if (customers.equals("true")) {
                    editor.putString("role-customers", "true");
                } else {
                    editor.putString("role-customers", "false");
                }
                editor.commit();
            }

            @Override
            public void onFailure(Call<Role> call, Throwable t) {

            }
        });
    }

    public void ViewProfile(User user) {
        Call<User> call = RetrofitClient.getInstance().getApi().currentUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User postResponse = response.body();

                String FN = postResponse.getFirstName();
                String LN = postResponse.getLastName();
                name.setText(FN + " " + LN);
                if (null == postResponse.getPicture()) {

                } else {
                    String imageURL = "http://192.168.0.146:9999/image/Users?imgPath=" + postResponse.getPicture();
                    Picasso.get().load(imageURL).into(profile);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}