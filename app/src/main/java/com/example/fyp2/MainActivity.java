package com.example.fyp2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.User;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText useric, password, phone, registeric, registerpassword, registercontact;
    private TextView tv;
    private Button login, register, registerbutton;

    // private CheckBox rmbMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        useric = (EditText) findViewById(R.id.loginIC);
        password = (EditText) findViewById(R.id.loginPassword);
        tv = (TextView) findViewById(R.id.textView2);
        login = (Button) findViewById(R.id.userLogIn);
        register = (Button) findViewById(R.id.Register);
        //rmbMe = (CheckBox)findViewById(R.id.checkBox);

        login.setOnClickListener(e -> {
            //User user = new User("951219015471","1234");
            //User user = new User("940528014566","1234");
//            User user = new User(useric.getText().toString(),password.getText().toString());
//            login(user);
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        });
        register.setOnClickListener(e -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_register, null);

            registeric = (EditText) mView.findViewById(R.id.register_ic);
            registerpassword = (EditText) mView.findViewById(R.id.register_password);
            registercontact = (EditText) mView.findViewById(R.id.register_contact);
            registerbutton = (Button) mView.findViewById(R.id.register_button);
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();

            registerbutton.setOnClickListener(u -> {
                User registerUser = new User(registerpassword.getText().toString(), registercontact.getText().toString(), registeric.getText().toString());
                Toast.makeText(getApplicationContext(), registercontact.getText().toString(), Toast.LENGTH_SHORT).show();
                registerProfile(registerUser);
            });
        });
    }
//    private void getAllUsers(){
//        Call<List<User>> call = RetrofitClient.getInstance().getApi()
//                .findAllUser();
//        call.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                if(!response.isSuccessful()){
//                    tv.setText("Code:"+response.code());
//                    return;
//                }
//                List<User> user = response.body();
//                for(User currentuser: user){
//                    String content="";
//                    content+="IC:"+currentuser.getIC()+"\n";
//                    tv.append(content);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                tv.setText(t.getMessage());
//            }
//        });
//
//    }

    public void registerProfile(User user) {
        Call<User> call = RetrofitClient.getInstance().getApi().createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Register UnSuccessful, \nDuplicate Name Exist", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void login(User user) {
        Call<User> call = RetrofitClient.getInstance().getApi().searchUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    tv.append("Invalid User IC and Password");
                } else {
                    //tv.append("valid");
                    User user = response.body();
//                    Intent intent = new Intent(MainActivity.this,MenuActivity.class);
//                    intent.putExtra("userIc",user.getIC());
//                    intent.putExtra("userRole", user.getRole());
//                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
