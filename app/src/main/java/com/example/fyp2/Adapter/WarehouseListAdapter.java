package com.example.fyp2.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.Warehouse;
import com.example.fyp2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WarehouseListAdapter extends RecyclerView.Adapter<WarehouseListAdapter.ViewHolder> {
    private ArrayList<Warehouse> productArrayList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Warehouse WarehouseProduct = productArrayList.get(position);
        holder.title.setText(WarehouseProduct.getProductsId());
        String imageURL = "http://192.168.0.146:9999/image/Products?imgPath=" + WarehouseProduct.getProductsImage();
        Picasso.get().load(imageURL).into(holder.warehouseImage);
        holder.quantity.setText(WarehouseProduct.getProductsQuantity() + " Quantity");
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setTitle("Warning");
                alertbox.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteWarehouse(productArrayList.get(position).getProductsId(), v.getContext());
                    }
                });
                alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertbox.show();
                //deleteWarehouse(productArrayList.get(position).getProductsId(),v.getContext());
                return false;
            }
        });
    }

    public WarehouseListAdapter(ArrayList<Warehouse> productArrayListt) {
        productArrayList = productArrayListt;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_warehouse, parent, false);
        ViewHolder vh = new ViewHolder(view, mListener);
        return vh;
    }

    public void deleteWarehouse(String x, Context context) {
        Call<Warehouse> call = RetrofitClient.getInstance().getApi().deleteWarehouse(x);
        call.enqueue(new Callback<Warehouse>() {
            @Override
            public void onResponse(Call<Warehouse> call, Response<Warehouse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Delete Fail", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Warehouse> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, quantity;
        public ImageView warehouseImage;
        public View mView;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.Warehouse_adapter_ID);
            warehouseImage = itemView.findViewById(R.id.Warehouse_adapter_IMG);
            quantity = itemView.findViewById(R.id.Warehouse_Adapter_Quantity);
            mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                    return false;
                }
            });
        }
    }
}
