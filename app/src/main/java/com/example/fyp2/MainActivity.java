package com.example.fyp2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText useric, password, registeric, registerpassword, registercontact;
    private TextView tv;
    private Button login, register, registerButton;
    public static final String SHARED_PREFS = "BOM_PREFS";
    public static final String USERIC = "text";
    public static final String FIRSTENTRY = "text";
    public static final String SWITCH = "switch1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        useric = (EditText) findViewById(R.id.loginIC);
        password = (EditText) findViewById(R.id.loginPassword);
        tv = (TextView) findViewById(R.id.textView2);
        login = (Button) findViewById(R.id.userLogIn);
        register = (Button) findViewById(R.id.Register);
        login.setOnClickListener(e -> {
            //User user = new User("123456", "951219015471");
            User user = new User(password.getText().toString(), useric.getText().toString());
            login(user, this);
        });
        register.setOnClickListener(e -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_register, null);
            registeric = (EditText) mView.findViewById(R.id.register_ic);
            registerpassword = (EditText) mView.findViewById(R.id.register_password);
            registercontact = (EditText) mView.findViewById(R.id.register_contact);
            registerButton = (Button) mView.findViewById(R.id.register_button);
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            registerButton.setOnClickListener(u -> {
                User registerUser = new User(registerpassword.getText().toString(), registercontact.getText().toString(), registeric.getText().toString());
                registerProfile(registerUser, this);
                dialog.dismiss();
            });
        });
    }
    public void registerProfile(User user, Context context) {
        Call<User> call = RetrofitClient.getInstance().getApi().createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Register Successful", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Register Fail", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Unable to connect server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void login(User user, Context context) {
        Call<User> call = RetrofitClient.getInstance().getApi().searchUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Login Fail\nInvalid Ic or Password", Toast.LENGTH_LONG).show();
                } else {
                    User user = response.body();
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    saveData(user.getUserIc(), user.getFirstEntry(), user.getRole());
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Unable to connect server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saveData(String userIc, String firstEntry, String roles) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("USERIC", userIc);
        editor.putString("FIRSTENTRY", firstEntry);
        editor.putString("ROLE", roles);
        editor.commit();
    }
}
