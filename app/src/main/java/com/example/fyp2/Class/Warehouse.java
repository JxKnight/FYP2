package com.example.fyp2.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Warehouse {
    @SerializedName("productsId")
    @Expose
    private String productsId;
    @SerializedName("productsName")
    @Expose
    private String productsName;
    @SerializedName("productsCategory")
    @Expose
    private String productsCategory;
    @SerializedName("productsQuantity")
    @Expose
    private String productsQuantity;
    @SerializedName("userUpdate")
    @Expose
    private String userUpdate;

    public Warehouse(String productsCategory) {
        this.productsCategory = productsCategory;
    }

    //update Warehouse
    public Warehouse(String productsId, String productsQuantity, String userUpdate) {
        this.productsId = productsId;
        this.productsQuantity = productsQuantity;
        this.userUpdate = userUpdate;
    }

    public String getProductsId() {
        return productsId;
    }

    public void setProductsId(String productsId) {
        this.productsId = productsId;
    }

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }

    public String getProductsCategory() {
        return productsCategory;
    }

    public void setProductsCategory(String productsCategory) {
        this.productsCategory = productsCategory;
    }

    public String getProductsQuantity() {
        return productsQuantity;
    }

    public void setProductsQuantity(String productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    public String getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
    }
}
