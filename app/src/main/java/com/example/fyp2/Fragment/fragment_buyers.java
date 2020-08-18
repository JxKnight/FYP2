package com.example.fyp2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.fyp2.Adapter.BuyerListAdapter;
import com.example.fyp2.Class.Buyer;
import com.example.fyp2.Class.OrderCartSession;
import com.example.fyp2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class fragment_buyers extends Fragment {
    View v;
    FloatingActionButton filter;
    ArrayList<Buyer> buyerList = new ArrayList<>();
    ListView buyerListView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_buyers, container, false);
        filter = (FloatingActionButton) v.findViewById(R.id.fragment_customers_filter_btn);
        filter.setOnClickListener(e -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_fragment_customer_filter, null);
            Spinner State = (Spinner) mView.findViewById(R.id.customer_category_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.locations, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            State.setAdapter(adapter);
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        });
        for (int i = 0; i < 50; i++) {
            Buyer buyers = new Buyer("00" + i, "01" + i, "Johor", "address" + i);
            buyerList.add(buyers);
        }
        buyerListView = (ListView) v.findViewById(R.id.buyersList);
        final BuyerListAdapter adapter = new BuyerListAdapter(getActivity(), R.layout.adapter_buyer_list, buyerList);
        adapter.notifyDataSetChanged();
        buyerListView.setAdapter(adapter);

//
//        String xxx = "111";
        buyerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(getActivity(),buyerList.get(i).getBuyerName(),Toast.LENGTH_SHORT).show();
                AlertDialog.Builder mmBuilder = new AlertDialog.Builder(getActivity());
                View mmView = getLayoutInflater().inflate(R.layout.dialog_fragment_buyer_detail, null);
                TextView name = (TextView) mmView.findViewById(R.id.fragment_buyer_detail_name);
                TextView contact = (TextView) mmView.findViewById(R.id.fragment_buyer_detail_contact);
                TextView location = (TextView) mmView.findViewById(R.id.fragment_buyer_detail_location);
                TextView address = (TextView) mmView.findViewById(R.id.fragment_buyer_detail_address);
                name.setText(buyerList.get(i).getBuyerName());
                contact.setText(buyerList.get(i).getBuyerContact());
                location.setText(buyerList.get(i).getBuyerLocation());
                address.setText(buyerList.get(i).getBuyerAddress());
                mmBuilder.setView(mmView);
                AlertDialog dialog = mmBuilder.create();
                dialog.show();
            }
        });
        return v;
    }
}