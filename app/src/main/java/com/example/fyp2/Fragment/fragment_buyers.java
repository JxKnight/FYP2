package com.example.fyp2.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.fyp2.Adapter.BuyerListAdapter;
import com.example.fyp2.Adapter.BuyerOrderHistoryListAdapter;
import com.example.fyp2.Adapter.ProductOrderListAdapter;
import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.Buyer;
import com.example.fyp2.Class.Order;
import com.example.fyp2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class fragment_buyers extends Fragment {
    View v;
    FloatingActionButton filter, add;
    ArrayList<Buyer> buyerList;
    ArrayList<Order> currentBuyerOrderHistoryList;
    ListView buyerListView;
    String userIc, roles, buyerPermission;
    static final int IMAGE_PICK_CODE = 1000;
    ListView buyerOrderHistoryLV;
    static final int PERMISSION_CODE = 1001;
    ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_buyers, container, false);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("BOM_PREFS", MODE_PRIVATE);
        userIc = sharedPreferences.getString("USERIC", "");
        roles = sharedPreferences.getString("ROLE", "");
        buyerPermission = sharedPreferences.getString("role-customers", "");
        currentBuyerOrderHistoryList = new ArrayList<>();

        buyerList = new ArrayList<>();
        buyerListView = (ListView) v.findViewById(R.id.buyersList);
        getBuyerList(getContext());

        filter = (FloatingActionButton) v.findViewById(R.id.fragment_buyers_filter_btn);
        filter.setOnClickListener(e -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_buyer_filter, null);
            Spinner State = (Spinner) mView.findViewById(R.id.customer_category_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.locations, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            State.setAdapter(adapter);
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        });

        add = (FloatingActionButton) v.findViewById(R.id.fragment_buyers_add_buyer);
        add.setOnClickListener(k -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_buyer_add_buyer, null);
            TextView name = (TextView) mView.findViewById(R.id.fragment_buyer_add_buyer_name);
            TextView contact = (TextView) mView.findViewById(R.id.fragment_buyer_add_buyer_contact);
            Spinner location = (Spinner) mView.findViewById(R.id.fragment_buyer_add_buyer_location_spinner);
            TextView address = (TextView) mView.findViewById(R.id.fragment_buyer_add_buyer_address);
            image = (ImageView) mView.findViewById(R.id.fragment_buyer_List_Buyer_Pic);
            ImageView selectPic = (ImageView) mView.findViewById(R.id.buyer_add_buyer_camera);
            Button submit = (Button) mView.findViewById(R.id.Buyer_Add_Buyer_Btn);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.locations, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            location.setAdapter(adapter);
            mBuilder.setView(mView);
            AlertDialog dialogAdd = mBuilder.create();
            dialogAdd.show();
            //b3.getString("userIc")
            submit.setOnClickListener(e -> {
                image.buildDrawingCache();
                Bitmap bmap = image.getDrawingCache();
                String x = getEncodeImage(bmap);
                Buyer addBuyer = new Buyer(name.getText().toString(), contact.getText().toString(), location.getSelectedItem().toString(), address.getText().toString(), userIc, x);
                addBuyer(addBuyer, getContext());
                dialogAdd.dismiss();
            });
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
        });
        buyerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(getActivity(),buyerList.get(i).getBuyerName(),Toast.LENGTH_SHORT).show();
                AlertDialog.Builder mmBuilder = new AlertDialog.Builder(getActivity());
                View mmView = getLayoutInflater().inflate(R.layout.dialog_buyer_detail, null);
                TextView name = (TextView) mmView.findViewById(R.id.fragment_buyer_detail_name);
                TextView contact = (TextView) mmView.findViewById(R.id.fragment_buyer_detail_contact);
                TextView location = (TextView) mmView.findViewById(R.id.fragment_buyer_detail_location);
                TextView address = (TextView) mmView.findViewById(R.id.fragment_buyer_detail_address);
                ImageView check = (ImageView) mmView.findViewById(R.id.buyer_detail_check_n_uncheck);
                Button confirmCheck = (Button) mmView.findViewById(R.id.buyer_detail_confirm_check);
                Button buyerHistory = (Button) mmView.findViewById(R.id.Buyer_Order_List_History);
                ImageView picture = (ImageView) mmView.findViewById(R.id.buyer_Detail_Picture);
                name.setText(buyerList.get(i).getBuyerName());
                contact.setText(buyerList.get(i).getBuyerContact());
                location.setText(buyerList.get(i).getBuyerLocation());
                address.setText(buyerList.get(i).getBuyerAddress());
                String imageURL = "http://192.168.0.146:9999/image/Customers?imgPath=" + buyerList.get(i).getBuyerImage();
                Picasso.get().load(imageURL).into(picture);
                if (buyerList.get(i).getAdminCheck().equals("true")) {
                    check.setImageResource(R.drawable.icon_check);
                    confirmCheck.setVisibility(View.GONE);
                } else {
                    if (roles.equals("1")) {
                        confirmCheck.setVisibility(View.VISIBLE);
                    } else {
                        confirmCheck.setVisibility(View.GONE);
                    }
                }
                mmBuilder.setView(mmView);
                AlertDialog dialogDetail = mmBuilder.create();
                dialogDetail.show();
                confirmCheck.setOnClickListener(e -> {
                    AlertDialog.Builder mmBuilderr = new AlertDialog.Builder(getActivity());
                    View mmVieww = getLayoutInflater().inflate(R.layout.dialog_buyer_confirm_check, null);
                    Button checkYes = (Button) mmVieww.findViewById(R.id.buyer_Detail_check_yes);
                    Button checkNo = (Button) mmVieww.findViewById(R.id.buyer_Detail_check_no);
                    mmBuilderr.setView(mmVieww);
                    AlertDialog dialogCheck = mmBuilderr.create();
                    dialogCheck.show();
                    checkYes.setOnClickListener(k -> {
                        Buyer buyer = new Buyer(buyerList.get(i).getBuyerId(), userIc, "true");
                        adminCheck(buyer, getContext());
                        dialogCheck.dismiss();
                        dialogDetail.dismiss();
                        buyerList.clear();
                        buyerListView.setAdapter(null);
                        getBuyerList(getContext());
                    });
                    checkNo.setOnClickListener(p -> {
                        dialogCheck.dismiss();
                    });
                });
                buyerHistory.setOnClickListener(k -> {
                    AlertDialog.Builder mBBuilder = new AlertDialog.Builder(getActivity());
                    View mVView = getLayoutInflater().inflate(R.layout.dialog_buyer_detail_order_history, null);
                    buyerOrderHistoryLV = mVView.findViewById(R.id.buyer_order_history_list);

                    Order order = new Order(buyerList.get(i).getBuyerId());
                    getCurrentBuyerOrderHistoryList(order, getContext());

                    mBBuilder.setView(mVView);
                    AlertDialog dialogCheck = mBBuilder.create();
                    dialogCheck.show();
                });

            }
        });
        return v;
    }

    public void getBuyerList(Context context) {
        Call<List<Buyer>> call = RetrofitClient.getInstance().getApi().findAllBuyer();
        call.enqueue(new Callback<List<Buyer>>() {
            @Override
            public void onResponse(Call<List<Buyer>> call, Response<List<Buyer>> response) {
                if (!response.isSuccessful()) {
                    //Toast.makeText(context, "Get Buyers Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<Buyer> buyers = response.body();
                    for (Buyer currentBuyer : buyers) {
                        buyerList.add(currentBuyer);
                    }
                    final BuyerListAdapter adapter = new BuyerListAdapter(getActivity(), R.layout.adapter_buyer_list, buyerList);
                    adapter.notifyDataSetChanged();
                    buyerListView.setAdapter(adapter);
                    //Toast.makeText(context,buyerList.get(0).getBuyerId(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Buyer>> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addBuyer(Buyer buyer, Context context) {
        Call<Void> call = RetrofitClient.getInstance().getApi().addBuyer(buyer);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Add Buyer Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Add Buyer Fail", Toast.LENGTH_LONG).show();
                }
                buyerList.clear();
                buyerListView.setAdapter(null);
                getBuyerList(getContext());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void adminCheck(Buyer buyer, Context context) {
        Call<Buyer> call = RetrofitClient.getInstance().getApi().checkBuyer(buyer);
        call.enqueue(new Callback<Buyer>() {
            @Override
            public void onResponse(Call<Buyer> call, Response<Buyer> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Checked Buyer", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Check Buyer Fail", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Buyer> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getCurrentBuyerOrderHistoryList(Order order, Context context) {
        Call<List<Order>> call = RetrofitClient.getInstance().getApi().findAllBuyerOrderHistoryList(order);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                currentBuyerOrderHistoryList.clear();
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Get Order History Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<Order> orders = response.body();
                    for (Order currentOrder : orders) {
                        currentBuyerOrderHistoryList.add(currentOrder);
                    }
                    final BuyerOrderHistoryListAdapter orderHistoryList = new BuyerOrderHistoryListAdapter(getActivity(), R.layout.adapter_buyer_order_history_list, currentBuyerOrderHistoryList);
                    orderHistoryList.notifyDataSetChanged();
                    buyerOrderHistoryLV.setAdapter(orderHistoryList);
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
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

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //set image to image view
            image.setImageURI(data.getData());
        }
    }
}