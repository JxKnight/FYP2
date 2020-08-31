package com.example.fyp2.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp2.Adapter.ProductListAdapter;
import com.example.fyp2.Adapter.ProductOrderListAdapter;
import com.example.fyp2.Adapter.WarehouseListAdapter;
import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.OrderCartSession;
import com.example.fyp2.Class.Product;
import com.example.fyp2.Class.Warehouse;
import com.example.fyp2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class fragment_warehouse extends Fragment {
    View v;
    Spinner State;
    ImageView filter;
    ArrayList<Warehouse> warehouseproductList;
    GridLayoutManager gridLayoutManager;
    RecyclerView WarehouseRecyclerView;
    WarehouseListAdapter warehouseListAdapter;
    String userIc, roles, warehousePermission;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_warehouse, container, false);

        gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        warehouseproductList = new ArrayList<>();
        getWarehouseProductList();

        WarehouseRecyclerView = v.findViewById(R.id.WarehouseList);

        warehouseListAdapter = new WarehouseListAdapter(warehouseproductList);
        WarehouseRecyclerView.setLayoutManager(gridLayoutManager);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("BOM_PREFS", MODE_PRIVATE);
        userIc = sharedPreferences.getString("USERIC", "");
        roles = sharedPreferences.getString("ROLE", "");
        warehousePermission = sharedPreferences.getString("role-warehouse", "");

        filter = (ImageView) v.findViewById(R.id.filter_warehouse);
        filter.setOnClickListener(e -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_warehouse_filter, null);
            State = (Spinner) mView.findViewById(R.id.warehouse_category_spinner);
            Button WarehouseSpinner = (Button) mView.findViewById(R.id.Warehouses_Spinner);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.category, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            State.setAdapter(adapter);
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();

            WarehouseSpinner.setOnClickListener(p -> {
                if (State.getSelectedItem().toString().equals("Select Category")) {
                    getWarehouseProductList();
                    dialog.dismiss();
                } else {
                    Warehouse warehouse = new Warehouse(State.getSelectedItem().toString());
                    findWarehouseByFilter(warehouse, getContext());
                    dialog.dismiss();
                }

            });
        });
        warehouseListAdapter.setOnItemClickListener(new WarehouseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog_warehouse_edit, null);
                TextView WarehouseProductID = mView.findViewById(R.id.warehouse_productID_editor);
                TextView WarehouseProductChangeQuantity = mView.findViewById(R.id.warehouse_quantity_editor);
                TextView WarehouseProductCurrentQuantity = mView.findViewById(R.id.warehouse_current_quantity);
                Button submitBtn = mView.findViewById(R.id.WarehouseEditProductBtn);
                WarehouseProductID.setText(warehouseproductList.get(position).getProductsId());
                WarehouseProductCurrentQuantity.setText(warehouseproductList.get(position).getProductsQuantity());
                mBuilder.setView(mView);
                AlertDialog ddialog = mBuilder.create();
                ddialog.show();
                submitBtn.setOnClickListener(e -> {
                    Warehouse update = new Warehouse(warehouseproductList.get(position).getProductsId(), WarehouseProductChangeQuantity.getText().toString(), userIc);
                    updateWarehouseQuantity(update, getContext());
                    ddialog.dismiss();
                });
            }
        });
        return v;
    }

    public void getWarehouseProductList() {
        Call<List<Warehouse>> call = RetrofitClient.getInstance().getApi().findAllWareHouseProduct();
        call.enqueue(new Callback<List<Warehouse>>() {
            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                if (!response.isSuccessful()) {
                    //Toast.makeText(context, "Get Buyers Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<Warehouse> products = response.body();
                    warehouseproductList.clear();
                    for (Warehouse currentProduct : products) {
                        //Toast.makeText(getActivity(),currentProduct.getProductsId(), Toast.LENGTH_SHORT).show();
                        warehouseproductList.add(currentProduct);
                    }

                    WarehouseRecyclerView.setAdapter(warehouseListAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Warehouse>> call, Throwable t) {
                //Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateWarehouseQuantity(Warehouse warehouse, Context context) {
        Call<Warehouse> call = RetrofitClient.getInstance().getApi().updateWarehouse(warehouse);
        call.enqueue(new Callback<Warehouse>() {
            @Override
            public void onResponse(Call<Warehouse> call, Response<Warehouse> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Update Fail", Toast.LENGTH_SHORT).show();
                }
                warehouseproductList.clear();
                getWarehouseProductList();
            }

            @Override
            public void onFailure(Call<Warehouse> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void findWarehouseByFilter(Warehouse warehouse, Context context) {
        Call<List<Warehouse>> call = RetrofitClient.getInstance().getApi().findAllWareHouseByFilter(warehouse);
        call.enqueue(new Callback<List<Warehouse>>() {
            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                List<Warehouse> products = response.body();
                warehouseproductList.clear();
                for (Warehouse currentProduct : products) {
                    //Toast.makeText(getActivity(),currentProduct.getProductsId(), Toast.LENGTH_SHORT).show();
                    warehouseproductList.add(currentProduct);
                }

                WarehouseRecyclerView.setAdapter(warehouseListAdapter);
            }

            @Override
            public void onFailure(Call<List<Warehouse>> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }
}