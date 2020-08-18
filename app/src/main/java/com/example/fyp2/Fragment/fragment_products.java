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

import com.example.fyp2.Adapter.ProductListAdapter;
import com.example.fyp2.Adapter.ProductOrderBuyerListAdapter;
import com.example.fyp2.Adapter.ProductOrderListAdapter;
import com.example.fyp2.Class.Buyer;
import com.example.fyp2.Class.OrderCartSession;
import com.example.fyp2.Class.Product;
import com.example.fyp2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class fragment_products extends Fragment {
    View v;
    Spinner CategoryFilter;
    FloatingActionButton floatFilterBtn, floatCartBtn;
    ArrayList<Product> productList = new ArrayList<>();
    ArrayList<Buyer> buyerList = new ArrayList<>();
    ArrayList<OrderCartSession> cartList = new ArrayList<>();
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_products, container, false);
        floatFilterBtn = (FloatingActionButton) v.findViewById(R.id.filter_products);
        floatFilterBtn.setOnClickListener(e -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_fragment_product_filter, null);
            CategoryFilter = (Spinner) mView.findViewById(R.id.product_category_spinner);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.category, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            CategoryFilter.setAdapter(adapter);

            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        });

        for (int i = 0; i < 50; i++) {
            Product products = new Product("00" + i, "00" + i + "A" + "\n" + "colorA");
            productList.add(products);
        }
        listView = (ListView) v.findViewById(R.id.productsList);
        final ProductListAdapter adapter = new ProductListAdapter(getActivity(), R.layout.adapter_view_products, productList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(), productList.get(i).getProductsId(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog_product_detail, null);
                Spinner productDetailCategorySpinner = mView.findViewById(R.id.Product_Detail_Category_Spinner);
                TextView productDetailID = (TextView) mView.findViewById(R.id.Product_Detail_ID);
                TextView productDescription = (TextView) mView.findViewById(R.id.Product_Detail_Description);
                Button order2Cart = (Button) mView.findViewById(R.id.Product_Detail_Add_2_Cart);
                ArrayAdapter<CharSequence> addapter = ArrayAdapter.createFromResource(getActivity(), R.array.category, android.R.layout.simple_spinner_item);
                addapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                productDetailCategorySpinner.setAdapter(addapter);
                productDetailID.setText(productList.get(i).getProductsId());
                productDescription.setText(productList.get(i).getProductsDescription());
                mBuilder.setView(mView);

                AlertDialog diialog = mBuilder.create();
                diialog.show();
                order2Cart.setOnClickListener(e -> {
                    diialog.dismiss();
                    AlertDialog.Builder mmBuilder = new AlertDialog.Builder(getActivity());
                    View mmView = getLayoutInflater().inflate(R.layout.dialog_product_order_cart_quantity, null);
                    EditText quantity = (EditText) mmView.findViewById(R.id.product_order_quantity);
                    Button btn = (Button) mmView.findViewById(R.id.Order_2_list);

                    mmBuilder.setView(mmView);

                    AlertDialog dialogg = mmBuilder.create();
                    dialogg.show();
                    btn.setOnClickListener(f -> {
                        OrderCartSession order = new OrderCartSession(productList.get(i).getProductsId(), quantity.getText().toString());
                        cartList.add(order);
                        dialogg.dismiss();
                    });
                });
            }
        });

        floatCartBtn = (FloatingActionButton) v.findViewById(R.id.product_order_list);
        floatCartBtn.setOnClickListener(e -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_product_order_cart, null);
            ListView orderCartList = mView.findViewById(R.id.Product_Order_Cart_ListView);
            Button saveOrderCart = mView.findViewById(R.id.CompleteOrderCart);

            final ProductOrderListAdapter orderListAdapter = new ProductOrderListAdapter(getActivity(), R.layout.adapter_view_product_order_list, cartList);
            orderListAdapter.notifyDataSetChanged();
            orderCartList.setAdapter(orderListAdapter);

            mBuilder.setView(mView);
            AlertDialog ddialog = mBuilder.create();
            ddialog.show();
            saveOrderCart.setOnClickListener(x -> {
                ddialog.dismiss();
                AlertDialog.Builder mBuilderr = new AlertDialog.Builder(getActivity());
                View mVieww = getLayoutInflater().inflate(R.layout.dialog_product_order_cart_buyer_list, null);

                ListView orderBuyerList = mVieww.findViewById(R.id.Product_Order_Cart_Buyer_List_View);
                Spinner orderCartBuyerLocationSpinner = mVieww.findViewById(R.id.Product_Order_Cart_Buyer_List_Location_Spinner);
                Button orderBuyerSubmitOrderBtn = mVieww.findViewById(R.id.CompleteOrderCart);

                final ProductOrderBuyerListAdapter orderBuyerListAdapter = new ProductOrderBuyerListAdapter(getActivity(), R.layout.adapter_view_product_order_cart_buyer_list, buyerList);
                orderBuyerListAdapter.notifyDataSetChanged();
                orderBuyerList.setAdapter(orderBuyerListAdapter);

                ArrayAdapter<CharSequence> aadapter = ArrayAdapter.createFromResource(getActivity(), R.array.locations, android.R.layout.simple_spinner_item);
                aadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                orderCartBuyerLocationSpinner.setAdapter(aadapter);

                mBuilderr.setView(mVieww);
                AlertDialog diaalog = mBuilderr.create();
                diaalog.show();
                orderBuyerSubmitOrderBtn.setOnClickListener(l -> {
                    Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                });
            });
        });
        return v;
    }
}