package com.example.fyp2.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.fyp2.Adapter.BuyerListAdapter;
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


public class fragment_orders extends Fragment {
    View v;
    Spinner State;
    FloatingActionButton floatfilterbtn;
    ArrayList<Order> orderList = new ArrayList<>();
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_orders, container, false);
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
        listView = (ListView) v.findViewById(R.id.ordersList);
        getOrderList(getContext());
//        final OrderListAdapter adapter = new OrderListAdapter(getActivity(), R.layout.adapter_order_list, orderList);
//        adapter.notifyDataSetChanged();
//        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), orderList.get(i).getBuyerId(), Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

    public void getOrderList(Context context) {
        Call<List<Order>> call = RetrofitClient.getInstance().getApi().findAllOrder();
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (!response.isSuccessful()) {
                    //Toast.makeText(context, "Get Buyers Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<Order> orders = response.body();
                    for (Order currentOrder : orders) {
                        orderList.add(currentOrder);
                        Toast.makeText(getActivity(), currentOrder.getProductsId(), Toast.LENGTH_LONG).show();
                    }

                    final OrderListAdapter adapter = new OrderListAdapter(getActivity(), R.layout.adapter_order_list, orderList);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                    //Toast.makeText(context,buyerList.get(0).getBuyerId(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }
}