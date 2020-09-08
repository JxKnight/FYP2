package com.example.fyp2.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.User;
import com.example.fyp2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class fragment_profile extends Fragment {
    Button submit;
    View v;
    EditText FName, LName, Ic, Contact, Address, PostCode, Password, CPassword;
    Spinner State;
    User currentUser;
    CircleImageView ProfilePic;
    ImageView resetPassword, selectPic;
    String userIc, firstEntry;
    TextView ResetPasswordTxt;
    private FloatingActionButton btnEditProfileEnable, getBtnEditProfileCancel;
    static final int IMAGE_PICK_CODE = 1000;
    static final int PERMISSION_CODE = 1001;

    public fragment_profile() {

    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("BOM_PREFS", MODE_PRIVATE);
        userIc = sharedPreferences.getString("USERIC", "");
        firstEntry = sharedPreferences.getString("FIRSTENTRY", "");

        submit = (Button) v.findViewById(R.id.profile_save);
        FName = (EditText) v.findViewById(R.id.FName);
        LName = (EditText) v.findViewById(R.id.LName);
        Ic = (EditText) v.findViewById(R.id.Ic);
        Contact = (EditText) v.findViewById(R.id.Contact);
        Address = (EditText) v.findViewById(R.id.Address);
        PostCode = (EditText) v.findViewById(R.id.PostCode);
        State = (Spinner) v.findViewById(R.id.State);
        Password = (EditText) v.findViewById(R.id.Password);
        CPassword = (EditText) v.findViewById(R.id.CPassword);
        ProfilePic = (CircleImageView) v.findViewById(R.id.ProfilePic);
        btnEditProfileEnable = (FloatingActionButton) v.findViewById(R.id.BtnEditProfileEnable);
        getBtnEditProfileCancel = (FloatingActionButton) v.findViewById(R.id.BtnEditProfileCancel);
        resetPassword = (ImageView) v.findViewById(R.id.ResetPassword);
        selectPic = (ImageView) v.findViewById(R.id.SelectProfilePic);
        ResetPasswordTxt = (TextView) v.findViewById(R.id.ResetPasswordTxt);
        FName.setEnabled(false);
        LName.setEnabled(false);
        Ic.setEnabled(false);
        Contact.setEnabled(false);
        Address.setEnabled(false);
        PostCode.setEnabled(false);
        Password.setEnabled(false);
        State.setEnabled(false);
        ProfilePic.setEnabled(false);
        btnEditProfileEnable.setVisibility(View.VISIBLE);
        getBtnEditProfileCancel.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
        currentUser = new User(userIc);
        ViewProfile(currentUser);

        if (firstEntry.equals("true")) {
            FName.setEnabled(true);
            LName.setEnabled(true);
            Contact.setEnabled(true);
            Address.setEnabled(true);
            PostCode.setEnabled(true);
            State.setEnabled(true);
            ProfilePic.setEnabled(true);
            btnEditProfileEnable.setVisibility(View.GONE);
            getBtnEditProfileCancel.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
        } else {
            btnEditProfileEnable.setOnClickListener(e -> {
                Contact.setEnabled(true);
                Address.setEnabled(true);
                PostCode.setEnabled(true);
                State.setEnabled(true);
                ProfilePic.setEnabled(true);
                btnEditProfileEnable.setVisibility(View.GONE);
                getBtnEditProfileCancel.setVisibility(View.VISIBLE);
                resetPassword.setVisibility(View.GONE);
                ResetPasswordTxt.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
            });
            getBtnEditProfileCancel.setOnClickListener(f -> {
                Contact.setEnabled(false);
                Address.setEnabled(false);
                PostCode.setEnabled(false);
                State.setEnabled(false);
                ProfilePic.setEnabled(false);
                btnEditProfileEnable.setVisibility(View.VISIBLE);
                getBtnEditProfileCancel.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                resetPassword.setVisibility(View.VISIBLE);
                ResetPasswordTxt.setVisibility(View.VISIBLE);
            });
        }
        submit.setOnClickListener(e -> {

            ProfilePic.buildDrawingCache();
            Bitmap bmap = ProfilePic.getDrawingCache();
            String x = getEncodeImage(bmap);
            //Toast.makeText(getActivity(), x, Toast.LENGTH_LONG).show();
            String Roles = null;
            if (State.getSelectedItem().toString().equals("Select Location")) {
                User newUser = new User(Password.getText().toString(), Contact.getText().toString(), Ic.getText().toString(), FName.getText().toString(), LName.getText().toString(), Address.getText().toString(), PostCode.getText().toString(), null, Roles, x, "false");
                updateProfile(newUser, getContext());
                //Toast.makeText(getContext(),State.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
            } else {
                User newUser = new User(Password.getText().toString(), Contact.getText().toString(), Ic.getText().toString(), FName.getText().toString(), LName.getText().toString(), Address.getText().toString(), PostCode.getText().toString(), State.getSelectedItem().toString(), Roles, x, "false");
                updateProfile(newUser, getContext());
            }
            ViewProfile(currentUser);
            Contact.setEnabled(false);
            Address.setEnabled(false);
            PostCode.setEnabled(false);
            State.setEnabled(false);
            resetPassword.setVisibility(View.VISIBLE);
            ResetPasswordTxt.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
            btnEditProfileEnable.setVisibility(View.VISIBLE);
            getBtnEditProfileCancel.setVisibility(View.GONE);
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        State.setAdapter(adapter);

        selectPic.setOnClickListener(e -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    //permission not granted,request it.
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    //show popup for runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission already granted
                    pickImageFromGallery();
                }
            } else {
                //system os is less than marshmallow
                pickImageFromGallery();
            }
        });

        resetPassword.setOnClickListener(k -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_profile_change_password, null);
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        });
        return v;
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //set image to image view
            ProfilePic.setImageURI(data.getData());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(getActivity(), "Permission denied...", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void ViewProfile(User user) {
        Call<User> call = RetrofitClient.getInstance().getApi().searchCurrentUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User postResponse = response.body();

                FName.setText(postResponse.getFirstName());
                LName.setText(postResponse.getLastName());
                Ic.setText(postResponse.getUserIc());
                Contact.setText(postResponse.getContact());
                Address.setText(postResponse.getAddress());
                PostCode.setText(postResponse.getPostCode());
                Password.setText(postResponse.getPassword());
//                String[] state =getResources().getStringArray(R.array.locations);
//                for (int i = 0; i < state.length; i++) {
//                    if (state[i].equals(postResponse.getState())) {
//                        State.setSelection(i);
//                    } else if (null == postResponse.getState()) {
//
//                    }
//                }
                if (null == postResponse.getPicture()) {

                } else {
                    byte[] decodeString = Base64.decode(postResponse.getPicture().getBytes(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
                    ProfilePic.setImageBitmap(decodedByte);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public void updateProfile(User user, Context context) {
        Call<User> call = RetrofitClient.getInstance().getApi().updateUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Update Fail", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getEncodeImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byeformat = stream.toByteArray();
        String imgString = Base64.encodeToString(byeformat, Base64.NO_WRAP);
        return imgString;
    }
}