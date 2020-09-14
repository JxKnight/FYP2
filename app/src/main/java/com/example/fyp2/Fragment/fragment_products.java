package com.example.fyp2.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp2.Adapter.BuyerListAdapter;
import com.example.fyp2.Adapter.ProductListAdapter;
import com.example.fyp2.Adapter.ProductOrderListAdapter;
import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.Buyer;
import com.example.fyp2.Class.Order;
import com.example.fyp2.Class.OrderCartSession;
import com.example.fyp2.Class.Product;
import com.example.fyp2.Class.Task;
import com.example.fyp2.Class.Warehouse;
import com.example.fyp2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class fragment_products extends Fragment {
    View v;
    Spinner CategoryFilter;
    FloatingActionButton floatFilterBtn, floatCartBtn, floatAddProduct;
    int x;
    ArrayList<Product> productList;
    ArrayList<Buyer> buyerList = new ArrayList<>();
    ArrayList<OrderCartSession> cartList = new ArrayList<>();
    ListView orderBuyerList;
    String userIc, orderPermission, taskTxt, role;
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;
    ProductListAdapter ProductListAdapter;
    ImageView productAddImage;
    ArrayList<EditText> editText = new ArrayList<>();
    EditText eT;
    EditText AddProductId;
    int i = 0;
    //Handler mHandler = new Handler();
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    static final int IMAGE_PICK_CODE = 1000;
    static final int PERMISSION_CODE = 1001;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_products, container, false);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        productList = new ArrayList<>();
        getProductList(getContext());

        recyclerView = v.findViewById(R.id.productsList);

        ProductListAdapter = new ProductListAdapter(productList);
        recyclerView.setLayoutManager(gridLayoutManager);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        date = dateFormat.format(calendar.getTime());

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("BOM_PREFS", MODE_PRIVATE);
        userIc = sharedPreferences.getString("USERIC", "");
        orderPermission = sharedPreferences.getString("role-orders", "");
        role = sharedPreferences.getString("ROLE", "");


        floatAddProduct = (FloatingActionButton) v.findViewById(R.id.product_add_product);
        floatFilterBtn = (FloatingActionButton) v.findViewById(R.id.product_filter);


        floatFilterBtn.setOnClickListener(e -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_product_filter, null);
            CategoryFilter = (Spinner) mView.findViewById(R.id.product_category_spinner);
            Button productFilterBtn = (Button) mView.findViewById(R.id.ProductFilterBtn);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.category, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            CategoryFilter.setAdapter(adapter);

            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
            productFilterBtn.setOnClickListener(o -> {
                if (CategoryFilter.getSelectedItem().toString().equals("Select Category")) {
                    getProductList(getContext());
                    dialog.dismiss();
                } else {
                    Product product = new Product(CategoryFilter.getSelectedItem().toString());
                    getProductListByCategory(product, getContext());
                    dialog.dismiss();
                }

            });

        });

        ProductListAdapter.setOnItemClickListener(new ProductListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(getActivity(),productList.get(position).getProductsId(),Toast.LENGTH_LONG).show();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog_product_detail, null);
                TextView productDetailCategory = mView.findViewById(R.id.Product_Detail_Category);
                TextView productDetailID = (TextView) mView.findViewById(R.id.Product_Detail_ID);
                TextView productDescription = (TextView) mView.findViewById(R.id.Product_Detail_Description);
                TextView productName = (TextView) mView.findViewById(R.id.Product_Detail_Name);
                TextView productPrice = (TextView) mView.findViewById(R.id.Product_Detail_Price);
                Button order2Cart = (Button) mView.findViewById(R.id.Product_Detail_Add_2_Cart);
                if (orderPermission.equals("true") || role.equals("1")) {
                    order2Cart.setVisibility(View.VISIBLE);
                } else {
                    order2Cart.setVisibility(View.GONE);
                }
                productDetailCategory.setText(productList.get(position).getProductsCategory());
                productDetailID.setText(productList.get(position).getProductsId());
                productDescription.setText(productList.get(position).getProductsDescription());
                productName.setText(productList.get(position).getProductsName());
                productPrice.setText(productList.get(position).getProductsPrice());
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
                        OrderCartSession order = new OrderCartSession(productList.get(position).getProductsId(), quantity.getText().toString());
                        cartList.add(order);
                        dialogg.dismiss();
                    });
                });
            }
        });
        floatCartBtn = (FloatingActionButton) v.findViewById(R.id.product_order_list);
        if (orderPermission.equals("true")) {
            floatCartBtn.show();
        } else {
            floatCartBtn.hide();
        }
        floatCartBtn.setOnClickListener(e -> {
            if (cartList.size() == 0) {
                Toast.makeText(getActivity(), "Please order at least one product!", Toast.LENGTH_LONG).show();
            } else {
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
                    getBuyerList(getContext());

                    Spinner orderCartBuyerLocationSpinner = mVieww.findViewById(R.id.Product_Order_Cart_Buyer_List_Location_Spinner);
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
                            Order order = new Order(orderDescription.getText().toString(), buyerList.get(position).getBuyerId(), productId, productQuantity, userIc, "true");
                            createOrder(order, getContext());
                            diaalog.dismiss();
                        }
                    });
                });
            }
        });
        if (orderPermission.equals("true") || role.equals("1")) {
            floatAddProduct.show();
        } else {
            floatAddProduct.hide();
        }

        floatAddProduct.setOnClickListener(p -> {
            AlertDialog.Builder mBuilderr = new AlertDialog.Builder(getActivity());
            View mVieww = getLayoutInflater().inflate(R.layout.dialog_product_add_product, null);


            EditText AddProductName = mVieww.findViewById(R.id.Product_Add_Name);
            EditText AddProductDescription = mVieww.findViewById(R.id.Product_Add_Description);
            EditText AddProductPrice = mVieww.findViewById(R.id.Product_Add_Price);
            AddProductId = mVieww.findViewById(R.id.Product_Add_ID);
            ImageView image = mVieww.findViewById(R.id.Product_Add_Photo);
            Button AddProductBtn = mVieww.findViewById(R.id.Product_Add_Product_Next_Btn);
            productAddImage = mVieww.findViewById(R.id.Product_Add_Picture);

            Spinner AddProductPackagingSpinner = mVieww.findViewById(R.id.Product_Add_Packaging);
            ArrayAdapter<CharSequence> aaadapter = ArrayAdapter.createFromResource(getActivity(), R.array.numbers, android.R.layout.simple_spinner_item);
            aaadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            AddProductPackagingSpinner.setAdapter(aaadapter);

            Spinner AddProductCategorySpinner = mVieww.findViewById(R.id.Product_Add_Category);
            ArrayAdapter<CharSequence> aadapter = ArrayAdapter.createFromResource(getActivity(), R.array.category, android.R.layout.simple_spinner_item);
            aadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            AddProductCategorySpinner.setAdapter(aadapter);

            mBuilderr.setView(mVieww);
            AlertDialog diaalog = mBuilderr.create();
            diaalog.show();

            AddProductBtn.setOnClickListener(r -> {
                x = 1;
                AlertDialog.Builder Builder = new AlertDialog.Builder(getActivity());
                View View = getLayoutInflater().inflate(R.layout.dialog_product_add_product_task, null);
                ImageView addET = View.findViewById(R.id.Product_add_Product_Task_EdiText);
                LinearLayout linearLayoutAddTask = View.findViewById(R.id.Product_Add_Task);
                Button button = View.findViewById(R.id.Product_Add_Product_Btn);
                Builder.setView(View);
                AlertDialog diialog = Builder.create();
                diialog.show();
                addET.setOnClickListener(k -> {

                    ScrollView sv = new ScrollView(getActivity());
                    sv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                    LinearLayout ll = new LinearLayout(getActivity());
                    ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    ll.setOrientation(LinearLayout.VERTICAL);

                    eT = new EditText(getActivity());
                    eT.setHint("Task" + x);
                    eT.setId(x);
                    ll.addView(eT);
                    editText.add(eT);
                    //Toast.makeText(getActivity(),Integer.toString(editText.get(x-1).getId()),Toast.LENGTH_LONG).show();
                    sv.addView(ll);
                    x++;
                    linearLayoutAddTask.addView(sv);

                });
                button.setOnClickListener(e -> {
                    i = 1;
                    taskTxt = "";
                    String seq = "";
                    String roles = "";
                    productAddImage.buildDrawingCache();
                    Bitmap bmap = productAddImage.getDrawingCache();
                    String x = getEncodeImage(bmap);
                    Product product = new Product(AddProductId.getText().toString(), AddProductName.getText().toString(), AddProductDescription.getText().toString(), AddProductPrice.getText().toString(), AddProductPackagingSpinner.getSelectedItem().toString(), AddProductCategorySpinner.getSelectedItem().toString(), x);
                    addProduct(product, getContext());
                    for (EditText var : editText) {
                        taskTxt = taskTxt + var.getText().toString() + "/";
                        //mHandler.postDelayed(mAddTaskRunnable,5000);
                        seq = seq + i + "/";
                        roles = roles + i + "/";
                        i++;
                    }
                    Task task = new Task(taskTxt, date, "", seq, roles, AddProductId.getText().toString());
                    storeTask(task, getContext());
                    diaalog.dismiss();
                    diialog.dismiss();
                });
            });

            image.setOnClickListener(y -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission not granted,request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        //permission already granted
                        pickImageFromGallery();
                    }
                } else {
                    //system os is less than marshmallow
                    pickImageFromGallery();
                }
            });
        });


        return v;
    }

    public void getProductList(Context context) {
        Call<List<Product>> call = RetrofitClient.getInstance().getApi().findAllProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList.clear();
                if (!response.isSuccessful()) {
                    //Toast.makeText(context, "Get Buyers Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<Product> products = response.body();
                    for (Product currentProduct : products) {
                        productList.add(currentProduct);
                    }
                    //Toast.makeText(getActivity(), productList.get(1).getProductsId(), Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(ProductListAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addProduct(Product product, Context context) {
        Call<Void> call = RetrofitClient.getInstance().getApi().createProduct(product);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Add Product Fail", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Add Product Successfully", Toast.LENGTH_LONG).show();
                    productList.clear();
                    getProductList(getContext());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
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

    public String getEncodeImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byeformat = stream.toByteArray();
        String imgString = Base64.encodeToString(byeformat, Base64.NO_WRAP);
        return imgString;
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //set image to image view
            productAddImage.setImageURI(data.getData());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(getActivity(), "Permission denied...", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void getProductListByCategory(Product product, Context context) {
        Call<List<Product>> call = RetrofitClient.getInstance().getApi().findAllProductByFilter(product);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList.clear();
                if (!response.isSuccessful()) {
                    //Toast.makeText(context, "Get Buyers Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<Product> products = response.body();
                    for (Product currentProduct : products) {
                        productList.add(currentProduct);
                    }
                    //Toast.makeText(getActivity(), productList.get(1).getProductsId(), Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(ProductListAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void storeTask(Task task, Context context) {
        Call<Void> call = RetrofitClient.getInstance().getApi().addTask(task);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Add Product Completed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Add Product Fail", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }
}