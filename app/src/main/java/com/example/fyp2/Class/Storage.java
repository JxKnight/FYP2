package com.example.fyp2.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Storage {
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

    public Storage(String productsId) {
        this.productsId = productsId;
    }

    //get Warehouse
    public Storage(String productsName, String productsCategory, String productsQuantity) {
        this.productsName = productsName;
        this.productsCategory = productsCategory;
        this.productsQuantity = productsQuantity;
    }

    //update Warehouse
    public Storage(String productsId, String productsQuantity) {
        this.productsId = productsId;
        this.productsQuantity = productsQuantity;
    }

    //create Warehouse
    public Storage(String productsId, String productsName, String productsCategory, String productsQuantity) {
        this.productsId = productsId;
        this.productsName = productsName;
        this.productsCategory = productsCategory;
        this.productsQuantity = productsQuantity;
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
}
