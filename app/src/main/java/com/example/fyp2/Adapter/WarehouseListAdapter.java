package com.example.fyp2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp2.Class.Warehouse;
import com.example.fyp2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, quantity;
        public ImageView warehouseImage;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.Warehouse_adapter_ID);
            warehouseImage = itemView.findViewById(R.id.Warehouse_adapter_IMG);
            quantity = itemView.findViewById(R.id.Warehouse_Adapter_Quantity);

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
        }
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
}
