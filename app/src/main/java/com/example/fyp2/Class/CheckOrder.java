package com.example.fyp2.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckOrder {
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
    @SerializedName("checkOrder")
    @Expose
    private String checkOrder;

    public CheckOrder(String ordersId, String ordersDescription, String ordersDate, String buyerId, String checkOrder) {
        this.ordersId = ordersId;
        this.ordersDescription = ordersDescription;
        this.ordersDate = ordersDate;
        this.buyerId = buyerId;
        this.checkOrder = checkOrder;
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

    public String getCheckOrder() {
        return checkOrder;
    }

    public void setCheckOrder(String checkOrder) {
        this.checkOrder = checkOrder;
    }
}
