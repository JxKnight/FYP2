package com.example.fyp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.fyp2.Fragment.fragment_attendance;
import com.example.fyp2.Fragment.fragment_buyers;
import com.example.fyp2.Fragment.fragment_orders;
import com.example.fyp2.Fragment.fragment_products;
import com.example.fyp2.Fragment.fragment_profile;
import com.example.fyp2.Fragment.fragment_warehouse;
import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Bundle bundle;
    private FragmentTransaction t;

    public static final String SHARED_PREFS = "111";
    public static final String TEXT = "text";
    public static final String SWITCH1 = "switch1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bundle = new Bundle();
        bundle.putString("userIc", getIntent().getStringExtra("userIc"));
        t = getSupportFragmentManager().beginTransaction();
        fragment_profile fragmentProfile = new fragment_profile();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        navigationView.bringToFront(); // light up effect
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (null == getIntent().getStringExtra("userFirstName")) {
            drawerLayout.closeDrawer(GravityCompat.START);
            bundle.putBoolean("firstEntry", true);
            fragmentProfile.setArguments(bundle);
            t.replace(R.id.fragment_container, fragmentProfile);
            t.commit();
        }
        View v = navigationView.getHeaderView(0);
        ImageView profile = v.findViewById(R.id.HeaderProfilePic);
        profile.setOnClickListener(u -> {
            bundle.putBoolean("firstEntry", false);
            fragmentProfile.setArguments(bundle);
            t.replace(R.id.fragment_container, fragmentProfile);
            t.commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        });
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
                //bundle.putString("userIc", getIntent().getStringExtra("userIc"));
                fragment_buyers fragmentBuyers = new fragment_buyers();
                fragmentBuyers.setArguments(bundle);
                t.replace(R.id.fragment_container, fragmentBuyers);
                t.commit();
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_buyers()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

}