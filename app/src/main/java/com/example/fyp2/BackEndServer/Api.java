package com.example.fyp2.BackEndServer;

import com.example.fyp2.Class.Attendance;
import com.example.fyp2.Class.Buyer;
import com.example.fyp2.Class.CalculateOrders;
import com.example.fyp2.Class.CheckOrder;
import com.example.fyp2.Class.OTP;
import com.example.fyp2.Class.Order;
import com.example.fyp2.Class.Product;
import com.example.fyp2.Class.Role;
import com.example.fyp2.Class.Salary;
import com.example.fyp2.Class.Task;
import com.example.fyp2.Class.User;
import com.example.fyp2.Class.Warehouse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

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

    @POST("/reNewUserPassword")
    Call<Void> reNewUserPassword(@Body User user);

    @POST("/updateUser")
    Call<User> updateUser(@Body User user);

    @POST("/updateUserRole")
    Call<User> updateUserRole(@Body User user);

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

    @POST("/deleteBuyer")
    Call<Buyer> deleteBuyer(@Query("buyerID") String buyerID);

    //product
    @GET("/products")
    Call<List<Product>> findAllProduct();

    @POST("/createProduct")
    Call<Void> createProduct(@Body Product product);


    @POST("/createOrder")
    Call<Order> createOrder(@Body Order order);

    @POST("/productFilter")
    Call<List<Product>> findAllProductByFilter(@Body Product product);

    @POST("/buyerHistoryList")
    Call<List<Order>> findAllBuyerOrderHistoryList(@Body Order order);

    @GET("/deleteProduct")
    Call<Product> deleteProduct(@Query("productsId") String x);

    //order
    @GET("/orders")
    Call<List<Order>> findAllOrder();

    @GET("/checkOrder")
    Call<List<CheckOrder>> ordersByCheck(@Query("ordersStatus") String text);

    @GET("/ordersByStatus")
    Call<List<Order>> ordersByStatus(@Query("ordersStatus") String text);

    //warehouse
    @GET("/warehouse")
    Call<List<Warehouse>> findAllWareHouseProduct();

    @POST("/updateWarehouse")
    Call<Warehouse> updateWarehouse(@Body Warehouse warehouse);

    @POST("/warehouseFilter")
    Call<List<Warehouse>> findAllWareHouseByFilter(@Body Warehouse warehouse);

    @GET("/deleteWarehouse")
    Call<Warehouse> deleteWarehouse(@Query("productsId") String x);

    //task
    @GET("/task")
    Call<List<Task>> findAllTask();

    @POST("/createTask")
    Call<Void> addTask(@Body Task task);

    @POST("/updateRole")
    Call<Task> updateRole(@Body Task task);

    @GET("/deleteTask")
    Call<Task> deleteTask(@Query("taskId") String x);

    //Role
    @GET("/roles")
    Call<List<Role>> findAllRoles();

    @POST("/createRoles")
    Call<Void> createRole(@Body Role role);

    @POST("/currentRole")
    Call<Role> currentRole(@Body Role role);

    @GET("/deleteRole")
    Call<Role> deleteRoles(@Query("roleNum") String x);

    //Attendance
    @POST("/requestAttendance")
    Call<Void> requestAttendance(@Body Attendance attendance);

    @GET("/getCurrentDayAttendance")
    Call<List<Attendance>> currentDayAttendance(@Query("day") String text);

    @GET("/MonthlyAttendance")
    Call<List<Attendance>> MonthlyAttendance(@Query("month") String month, @Query("userIc") String userIc);

    //OTP
    @POST("/createAttendance")
    Call<Void> createAttendance(@Body OTP otp);

    //Salary
    @GET("/monthlySalary")
    Call<Salary> monthlySalary(@Query("month") String month, @Query("userIc") String userIc);

    //CalculateOrders
    @GET("/calculationOrders")
    Call<List<CalculateOrders>> calculationOrders();
}
