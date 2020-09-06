package com.example.fyp2.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("ordersId")
    @Expose
    private String ordersId;
    @SerializedName("ordersDescription")
    @Expose
    private String ordersDescription;
    @SerializedName("ordersDate")
    @Expose
    private String ordersDate;
    @SerializedName("buyerId")
    @Expose
    private String buyerId;
    @SerializedName("productsId")
    @Expose
    private String productsId;
    @SerializedName("productsQuantity")
    @Expose
    private String productsQuantity;
    @SerializedName("userIc")
    @Expose
    private String userIc;
    @SerializedName("ordersStatus")
    @Expose
    private String ordersStatus;


    public Order(String ordersId, String ordersDescription, String buyerId) {
        this.ordersId = ordersId;
        this.ordersDescription = ordersDescription;
        this.buyerId = buyerId;
    }

    public Order(String productsId, String productsQuantity) {
        this.productsId = productsId;
        this.productsQuantity = productsQuantity;
    }

    public Order(String ordersDescription, String ordersDate, String buyerId, String productsId, String productsQuantity, String userIc, String ordersStatus) {
        this.ordersDescription = ordersDescription;
        this.ordersDate = ordersDate;
        this.buyerId = buyerId;
        this.productsId = productsId;
        this.productsQuantity = productsQuantity;
        this.userIc = userIc;
        this.ordersStatus = ordersStatus;
    }

    public Order(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public String getOrdersDescription() {
        return ordersDescription;
    }

    public void setOrdersDescription(String ordersDescription) {
        this.ordersDescription = ordersDescription;
    }

    public String getOrdersDate() {
        return ordersDate;
    }

    public void setOrdersDate(String ordersDate) {
        this.ordersDate = ordersDate;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getProductsId() {
        return productsId;
    }

    public void setProductsId(String productsId) {
        this.productsId = productsId;
    }

    public String getProductsQuantity() {
        return productsQuantity;
    }

    public void setProductsQuantity(String productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    public String getUserIc() {
        return userIc;
    }

    public void setUserIc(String userIc) {
        this.userIc = userIc;
    }

    public String getOrdersStatus() {
        return ordersStatus;
    }

    public void setOrdersStatus(String ordersStatus) {
        this.ordersStatus = ordersStatus;
    }
}
