package com.example.fyp2.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.example.fyp2.Adapter.OrderListAdapter;
import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.Buyer;
import com.example.fyp2.Class.Order;
import com.example.fyp2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class fragment_orders extends Fragment {
    View v;
    Spinner State;
    FloatingActionButton floatfilterbtn;
    ArrayList<Order> orderNewList = new ArrayList<>();
    ArrayList<Order> orderCompletedList = new ArrayList<>();
    ArrayList<Order> orderList = new ArrayList<>();
    ListView listView;
    String orderDetailsBuyerName, userIc, orderPermission, role;
    TextView buyerName;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_orders, container, false);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("BOM_PREFS", MODE_PRIVATE);
        userIc = sharedPreferences.getString("USERIC", "");
        orderPermission = sharedPreferences.getString("role-orders", "");
        role = sharedPreferences.getString("role", "");

        getOrderList("true", getContext());
        listView = (ListView) v.findViewById(R.id.ordersList);
        SegmentedButtonGroup sbg = v.findViewById(R.id.segmentedBtnGroup);
        sbg.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(int position) {
                if (position == 0) {
                    listView.setAdapter(null);
                    getOrderList("true", getContext());
                } else if (position == 1) {
                    listView.setAdapter(null);
                    getOrderList("false", getContext());
                }
            }
        });

        floatfilterbtn = (FloatingActionButton) v.findViewById(R.id.filter_orders);
        floatfilterbtn.setOnClickListener(e -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_orders_filter, null);
            State = (Spinner) mView.findViewById(R.id.location_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.locations, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            State.setAdapter(adapter);
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog_order_detail, null);
                TextView orderID = (TextView) mView.findViewById(R.id.order_detail_id);
                buyerName = (TextView) mView.findViewById(R.id.order_detail_buyer_name);
                TextView orderDescription = (TextView) mView.findViewById(R.id.order_detail_description);
                TextView orderProductList = (TextView) mView.findViewById(R.id.order_list_productId);
                TextView orderProductQuantity = (TextView) mView.findViewById(R.id.order_list_productQuantity);

                //Toast.makeText(getActivity(),orderList.get(i).getOrdersId(),Toast.LENGTH_LONG).show();
                orderID.setText(orderNewList.get(i).getOrdersId());
                orderDescription.setText(orderNewList.get(i).getOrdersDescription());
                getBuyerName(orderNewList.get(i).getBuyerId(), getContext());

                String[] productList = orderNewList.get(i).getProductsId().split("/");
                String[] quantityList = orderNewList.get(i).getProductsQuantity().split("/");
                for (String a : productList) {
                    orderProductList.append(a + "\n");
                }
                for (String b : quantityList) {
                    orderProductQuantity.append(b + "\n");
                }
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
        return v;
    }

    public void getOrderList(String Order, Context context) {
        Call<List<Order>> call = RetrofitClient.getInstance().getApi().findAllOrder();
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                orderNewList.clear();
                orderCompletedList.clear();
                orderList.clear();
                if (!response.isSuccessful()) {
                    //Toast.makeText(context, "Get Buyers Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<Order> orders = response.body();
                    for (Order currentOrder : orders) {
                        if (currentOrder.getOrdersStatus().equals("true")) {
                            orderNewList.add(currentOrder);
                        } else {
                            orderCompletedList.add(currentOrder);
                        }
                    }
                    if (Order.equals("true")) {
                        orderList = orderNewList;
                    } else {
                        orderList = orderCompletedList;
                    }
                    final OrderListAdapter adapter = new OrderListAdapter(getActivity(), R.layout.adapter_order_list, orderList);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getBuyerName(String buyerId, Context context) {
        Call<Buyer> call = RetrofitClient.getInstance().getApi().getBuyerDetails(buyerId);
        call.enqueue(new Callback<Buyer>() {
            @Override
            public void onResponse(Call<Buyer> call, Response<Buyer> response) {
                if (!response.isSuccessful()) {
                    //Toast.makeText(context, "Get Buyers Fail", Toast.LENGTH_LONG).show();
                } else {
                    orderDetailsBuyerName = response.body().getBuyerName();
                    buyerName.setText(orderDetailsBuyerName);
                }
            }

            @Override
            public void onFailure(Call<Buyer> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }
}