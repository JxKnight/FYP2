package com.example.fyp2.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import com.example.fyp2.Adapter.BuyerListAdapter;
import com.example.fyp2.Adapter.ProductListAdapter;
import com.example.fyp2.Adapter.ProductOrderBuyerListAdapter;
import com.example.fyp2.Adapter.ProductOrderListAdapter;
import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.Buyer;
import com.example.fyp2.Class.Order;
import com.example.fyp2.Class.OrderCartSession;
import com.example.fyp2.Class.Product;
import com.example.fyp2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class fragment_products extends Fragment {
    View v;
    Spinner CategoryFilter;
    FloatingActionButton floatFilterBtn, floatCartBtn, floatAddProduct;
    ArrayList<Product> productList;
    ArrayList<Buyer> buyerList = new ArrayList<>();
    ArrayList<OrderCartSession> cartList = new ArrayList<>();
    ListView productListView, orderBuyerList;
    String userIc, roles;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_products, container, false);
        productList = new ArrayList<>();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("BOM_PREFS", MODE_PRIVATE);
        userIc = sharedPreferences.getString("USERIC", "");
        roles = sharedPreferences.getString("ROLE", "");

        floatAddProduct = (FloatingActionButton) v.findViewById(R.id.product_add_product);
        floatFilterBtn = (FloatingActionButton) v.findViewById(R.id.filter_products);
        floatFilterBtn.setOnClickListener(e -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_product_filter, null);
            CategoryFilter = (Spinner) mView.findViewById(R.id.product_category_spinner);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.category, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            CategoryFilter.setAdapter(adapter);

            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        });

        productListView = (ListView) v.findViewById(R.id.productsList);
        getProductList();
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(), productList.get(i).getProductsId(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog_product_detail, null);
                TextView productDetailCategory = mView.findViewById(R.id.Product_Detail_Category);
                TextView productDetailID = (TextView) mView.findViewById(R.id.Product_Detail_ID);
                TextView productDescription = (TextView) mView.findViewById(R.id.Product_Detail_Description);
                TextView productName = (TextView) mView.findViewById(R.id.Product_Detail_Name);
                TextView productPrice = (TextView) mView.findViewById(R.id.Product_Detail_Price);
                Button order2Cart = (Button) mView.findViewById(R.id.Product_Detail_Add_2_Cart);
                productDetailCategory.setText(productList.get(i).getProductsCategory());
                productDetailID.setText(productList.get(i).getProductsId());
                productDescription.setText(productList.get(i).getProductsDescription());
                productName.setText(productList.get(i).getProductsName());
                productPrice.setText(productList.get(i).getProductsPrice());
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

            final ProductOrderListAdapter orderListAdapter = new ProductOrderListAdapter(getActivity(), R.layout.adapter_product_order_list, cartList);
            orderListAdapter.notifyDataSetChanged();
            orderCartList.setAdapter(orderListAdapter);

            mBuilder.setView(mView);
            AlertDialog ddialog = mBuilder.create();
            ddialog.show();
            saveOrderCart.setOnClickListener(x -> {
                ddialog.dismiss();
                AlertDialog.Builder mBuilderr = new AlertDialog.Builder(getActivity());
                View mVieww = getLayoutInflater().inflate(R.layout.dialog_product_order_cart_buyer_list, null);

                orderBuyerList = mVieww.findViewById(R.id.Product_Order_Cart_Buyer_List_View);
                Spinner orderCartBuyerLocationSpinner = mVieww.findViewById(R.id.Product_Order_Cart_Buyer_List_Location_Spinner);
//                final ProductOrderBuyerListAdapter orderBuyerListAdapter = new ProductOrderBuyerListAdapter(getActivity(), R.layout.adapter_product_order_cart_buyer_list, buyerList);
//                orderBuyerListAdapter.notifyDataSetChanged();
//                orderBuyerList.setAdapter(orderBuyerListAdapter);
                getBuyerList(getContext());
                ArrayAdapter<CharSequence> aadapter = ArrayAdapter.createFromResource(getActivity(), R.array.locations, android.R.layout.simple_spinner_item);
                aadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                orderCartBuyerLocationSpinner.setAdapter(aadapter);
                EditText orderDescription = (EditText) mVieww.findViewById(R.id.Product_order_Cart_Buyer_Description);
                ImageView filterBuyerList = (ImageView) mVieww.findViewById(R.id.product_order_cart_buyer_list_search);
                mBuilderr.setView(mVieww);
                AlertDialog diaalog = mBuilderr.create();
                diaalog.show();
                filterBuyerList.setOnClickListener(k -> {
                    buyerList.clear();
                    // orderCartBuyerLocationSpinner.getSelectedItem().toString();
                    Buyer buyerByLocation = new Buyer(orderCartBuyerLocationSpinner.getSelectedItem().toString());
                    getBuyerListInLocation(buyerByLocation, getContext());
                });
                orderBuyerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    String productId = "", productQuantity = "";

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (OrderCartSession product : cartList) {

                            productId = productId + product.getProductId() + "/";
                            productQuantity = productQuantity + product.getOrderQuantity() + "/";
                        }
                        //Toast.makeText(getActivity(),productId,Toast.LENGTH_LONG).show();
                        Order order = new Order(orderDescription.getText().toString(), "DATE", buyerList.get(position).getBuyerId(), productId, productQuantity, userIc);
                        createOrder(order, getContext());
                        diaalog.dismiss();
                    }
                });
            });
        });
        if (roles.equals("5")) {
            floatAddProduct.show();
        } else {
            floatAddProduct.hide();
        }

        floatAddProduct.setOnClickListener(p -> {
            AlertDialog.Builder mBuilderr = new AlertDialog.Builder(getActivity());
            View mVieww = getLayoutInflater().inflate(R.layout.dialog_product_add_product, null);

            Spinner AddProductCategorySpinner = mVieww.findViewById(R.id.Product_Add_Category);
            EditText AddProductName = mVieww.findViewById(R.id.Product_Add_Name);
            EditText AddProductDescription = mVieww.findViewById(R.id.Product_Add_Description);
            EditText AddProductPrice = mVieww.findViewById(R.id.Product_Add_Price);
            EditText AddProductId = mVieww.findViewById(R.id.Product_Add_ID);
            Button AddProductBtn = mVieww.findViewById(R.id.Product_Add_Product_Btn);

            ArrayAdapter<CharSequence> aadapter = ArrayAdapter.createFromResource(getActivity(), R.array.category, android.R.layout.simple_spinner_item);
            aadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            AddProductCategorySpinner.setAdapter(aadapter);

            mBuilderr.setView(mVieww);
            AlertDialog diaalog = mBuilderr.create();
            diaalog.show();

            AddProductBtn.setOnClickListener(r -> {
                Product newProduct = new Product(AddProductId.getText().toString(), AddProductName.getText().toString(), AddProductDescription.getText().toString(), AddProductPrice.getText().toString(), AddProductCategorySpinner.getSelectedItem().toString());
                addProduct(newProduct, getContext());
                diaalog.dismiss();
            });
        });
        return v;
    }

    public void getProductList() {
        Call<List<Product>> call = RetrofitClient.getInstance().getApi().findAllProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    //Toast.makeText(context, "Get Buyers Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<Product> products = response.body();
                    for (Product currentProduct : products) {
                        productList.add(currentProduct);
                    }
                    //Toast.makeText(getActivity(), productList.get(1).getProductsId(), Toast.LENGTH_SHORT).show();
                    final ProductListAdapter ProductListAdapter = new ProductListAdapter(getActivity(), R.layout.adapter_products, productList);
                    ProductListAdapter.notifyDataSetChanged();
                    productListView.setAdapter(ProductListAdapter);
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                //Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addProduct(Product product, Context context) {
        Call<Product> call = RetrofitClient.getInstance().getApi().createProduct(product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Add Product Fail", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Add Product Successfully", Toast.LENGTH_LONG).show();
                    productList.clear();
                    getProductList();
                }

            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(context, "Fail To Connect To Server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getBuyerList(Context context) {
        buyerList.clear();
        Call<List<Buyer>> call = RetrofitClient.getInstance().getApi().findAllBuyer();
        call.enqueue(new Callback<List<Buyer>>() {
            @Override
            public void onResponse(Call<List<Buyer>> call, Response<List<Buyer>> response) {
                if (!response.isSuccessful()) {
                    //Toast.makeText(context, "Get Buyers Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<Buyer> buyers = response.body();
                    for (Buyer currentBuyer : buyers) {
                        buyerList.add(currentBuyer);
                    }
                    final BuyerListAdapter adapter = new BuyerListAdapter(getActivity(), R.layout.adapter_buyer_list, buyerList);
                    adapter.notifyDataSetChanged();
                    orderBuyerList.setAdapter(adapter);
                    //Toast.makeText(context,buyerList.get(0).getBuyerId(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Buyer>> call, Throwable t) {
                //Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getBuyerListInLocation(Buyer buyer, Context context) {
        Call<List<Buyer>> call = RetrofitClient.getInstance().getApi().findBuyersByLocation(buyer);
        call.enqueue(new Callback<List<Buyer>>() {
            @Override
            public void onResponse(Call<List<Buyer>> call, Response<List<Buyer>> response) {
                if (!response.isSuccessful()) {
                    //Toast.makeText(context, "Get Buyers Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<Buyer> buyers = response.body();
                    for (Buyer currentBuyer : buyers) {
                        buyerList.add(currentBuyer);
                    }
                    final BuyerListAdapter adapter = new BuyerListAdapter(getActivity(), R.layout.adapter_buyer_list, buyerList);
                    adapter.notifyDataSetChanged();
                    orderBuyerList.setAdapter(adapter);
                    //Toast.makeText(context,buyerList.get(0).getBuyerId(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Buyer>> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createOrder(Order order, Context context) {
        Call<Order> call = RetrofitClient.getInstance().getApi().createOrder(order);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Place Order Fail", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Place Order Success", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }
}