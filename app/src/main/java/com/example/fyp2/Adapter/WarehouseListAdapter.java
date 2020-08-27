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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, quantity;
        public ImageView gridImage;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.Warehouse_adapter_ID);
            gridImage = itemView.findViewById(R.id.Warehouse_adapter_IMG);
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Warehouse WarehouseProduct = productArrayList.get(position);
//        byte[] decodeString = Base64.decode(images.get(position).getBytes(), Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        holder.title.setText(WarehouseProduct.getProductsId());
        // holder.gridImage.setImageBitmap(decodedByte);
        holder.quantity.setText(WarehouseProduct.getProductsQuantity() + " Quantity");
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
}
