package com.example.fyp2.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.fyp2.Adapter.OrderListAdapter;
import com.example.fyp2.Class.Order;
import com.example.fyp2.R;

import java.util.ArrayList;
import java.util.Calendar;


public class fragment_orders extends Fragment {
    View v;
    Spinner State;
    ImageView filter;
    ;
    ArrayList<Order> orderList = new ArrayList<>();
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_orders, container, false);
        filter = (ImageView) v.findViewById(R.id.filter_orders);
        filter.setOnClickListener(e -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_fragment_orders_filter, null);
            State = (Spinner) mView.findViewById(R.id.location_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.locations, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            State.setAdapter(adapter);
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        });
        listView = (ListView) v.findViewById(R.id.ordersList);
        for (int i = 0; i < 100; i++) {
            String s = "00" + i;
            Order order = new Order("Order0001", "XXX", s);
            orderList.add(order);
        }
        final OrderListAdapter adapter = new OrderListAdapter(getActivity(), R.layout.adapter_view_orders, orderList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), orderList.get(i).getBuyerId(), Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

}