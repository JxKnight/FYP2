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
import android.widget.Toast;

import com.example.fyp2.Fragment.fragment_attendance;
import com.example.fyp2.Fragment.fragment_buyers;
import com.example.fyp2.Fragment.fragment_orders;
import com.example.fyp2.Fragment.fragment_products;
import com.example.fyp2.Fragment.fragment_profile;
import com.example.fyp2.Fragment.fragment_warehouse;
import com.google.android.material.navigation.NavigationView;

import static com.example.fyp2.MainActivity.FIRSTENTRY;
import static com.example.fyp2.MainActivity.USERIC;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Bundle bundle;
    private FragmentTransaction t;
    private String userIc, firstEntry;

    public static final String SHARED_PREFS = "BOM_PREFS";
    public static final String USERIC = "";
    public static final String FIRSTENTRY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SharedPreferences sharedPreferences = getSharedPreferences("BOM_PREFS", MODE_PRIVATE);
        userIc = sharedPreferences.getString("USERIC", "");
        firstEntry = sharedPreferences.getString("FIRSTENTRY", "");

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

        if (firstEntry.equals("true")) {
            drawerLayout.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_profile()).commit();
        }

        View v = navigationView.getHeaderView(0);
        ImageView profile = v.findViewById(R.id.HeaderProfilePic);
        profile.setOnClickListener(u -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_profile()).commit();
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