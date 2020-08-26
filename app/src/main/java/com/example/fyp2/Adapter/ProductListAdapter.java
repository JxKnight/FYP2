package com.example.fyp2.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp2.Class.Product;
import com.example.fyp2.R;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private ArrayList<Product> productArrayList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, price;
        public ImageView gridImage;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.Product_adapter_ID);
            gridImage = itemView.findViewById(R.id.Product_adapter_IMG);
            price = itemView.findViewById(R.id.Product_Adapter_Price);

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

    public ProductListAdapter(ArrayList<Product> productArrayListt) {
        productArrayList = productArrayListt;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_products, parent, false);
        ViewHolder vh = new ViewHolder(view, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productArrayList.get(position);
//        byte[] decodeString = Base64.decode(images.get(position).getBytes(), Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        holder.title.setText(product.getProductsId());
        // holder.gridImage.setImageBitmap(decodedByte);
        holder.price.setText("RM " + product.getProductsPrice());
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
}
