package com.example.fyp2.BackEndServer;

import com.example.fyp2.Class.Buyer;
import com.example.fyp2.Class.Order;
import com.example.fyp2.Class.Product;
import com.example.fyp2.Class.User;
import com.example.fyp2.Class.Warehouse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {


    //user
    @GET("/users")
    Call<List<User>> findAllUser();

    @POST("/searchUser")
    Call<User> searchUser(@Body User user);

    @POST("/currentUser")
    Call<User> searchCurrentUser(@Body User user);

    @POST("/registerUser")
    Call<User> createUser(@Body User user);

    @POST("/updateUser")
    Call<User> updateUser(@Body User user);

    @POST("/deleteUser")
    Call<User> deleteUser(@Body User user);

//    @FormUrlEncoded
//    @POST("/searchUser")
//    Call<User> searchUserr(@Field("uID") String uid,@Field("uPassword") String password);

    //buyer
    @GET("/buyers")
    Call<List<Buyer>> findAllBuyer();

    @POST("/createBuyer")
    Call<Void> addBuyer(@Body Buyer buyer);

    @POST("/getBuyerDetails")
    Call<Buyer> getBuyerDetails(@Body Buyer buyer);

    @POST("/buyerAdminCheck")
    Call<Buyer> checkBuyer(@Body Buyer buyer);

    @POST("/getBuyerByLocation")
    Call<List<Buyer>> findBuyersByLocation(@Body Buyer buyer);

    @POST("/getBuyerDetails")
    Call<Buyer> getBuyerDetails(@Query("buyerID") String buyerID);

    //product
    @GET("/products")
    Call<List<Product>> findAllProduct();

    @POST("/createProduct")
    Call<Product> createProduct(@Body Product product);

    @GET("/orders")
    Call<List<Order>> findAllOrder();

    @POST("/createOrder")
    Call<Order> createOrder(@Body Order order);

    @POST("/productFilter")
    Call<List<Product>> findAllProductByFilter(@Body Product product);

    //warehouse
    @GET("/warehouse")
    Call<List<Warehouse>> findAllWareHouseProduct();

    @POST("/updateWarehouse")
    Call<Warehouse> updateWarehouse(@Body Warehouse warehouse);

    @POST("/warehouseFilter")
    Call<List<Warehouse>> findAllWareHouseByFilter(@Body Warehouse warehouse);
}
