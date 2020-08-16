package com.example.fyp2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.fyp2.Adapter.OrderListAdapter;
import com.example.fyp2.Adapter.ProductListAdapter;
import com.example.fyp2.Class.Order;
import com.example.fyp2.Class.Product;
import com.example.fyp2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class fragment_products extends Fragment {
    View v;
    Spinner State;
    FloatingActionButton floatfilterbtn;
    ArrayList<Product> productList = new ArrayList<>();
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_products, container, false);
        floatfilterbtn = (FloatingActionButton) v.findViewById(R.id.filter_products);
        floatfilterbtn.setOnClickListener(e -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_fragment_product_filter, null);
            State = (Spinner) mView.findViewById(R.id.product_category_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.category, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            State.setAdapter(adapter);
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        });

        listView = (ListView) v.findViewById(R.id.productsList);
        final ProductListAdapter adapter = new ProductListAdapter(getActivity(), R.layout.adapter_view_products, productList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(), orderList.get(i).getBuyerId(), Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }
}