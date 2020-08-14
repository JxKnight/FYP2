package com.example.fyp2.BackEndServer;

import com.example.fyp2.Class.Buyer;
import com.example.fyp2.Class.Location;
import com.example.fyp2.Class.Order;
import com.example.fyp2.Class.Product;
import com.example.fyp2.Class.Role;
import com.example.fyp2.Class.Storage;
import com.example.fyp2.Class.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

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

    @GET("/users")
    Call<List<User>> findAllUser();

}