package com.example.fyp2.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("productsId")
    @Expose
    private String productsId;
    @SerializedName("productsName")
    @Expose
    private String productsName;
    @SerializedName("productsDescription")
    @Expose
    private String productsDescription;
    @SerializedName("productsPack")
    @Expose
    private String productsPack;
    @SerializedName("productsQuantity")
    @Expose
    private String productsQuantity;
    @SerializedName("productsPrice")
    @Expose
    private String productsPrice;
    @SerializedName("productsCategory")
    @Expose
    private String productsCategory;

    public Product(String productsId) {
        this.productsId = productsId;
    }

    public Product(String productsName, String productsCategory) {
        this.productsName = productsName;
        this.productsCategory = productsCategory;
    }

    public Product(String productsId, String productsName, String productsCategory) {
        this.productsId = productsId;
        this.productsName = productsName;
        this.productsCategory = productsCategory;
    }

    public Product(String productsId, String productsName, String productsDescription, String productsPack, String productsQuantity, String productsPrice, String productsCategory) {
        this.productsId = productsId;
        this.productsName = productsName;
        this.productsDescription = productsDescription;
        this.productsPack = productsPack;
        this.productsQuantity = productsQuantity;
        this.productsPrice = productsPrice;
        this.productsCategory = productsCategory;
    }

    public String getProductsPack() {
        return productsPack;
    }

    public void setProductsPack(String productsPack) {
        this.productsPack = productsPack;
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

    public String getProductsDescription() {
        return productsDescription;
    }

    public void setProductsDescription(String productsDescription) {
        this.productsDescription = productsDescription;
    }

    public String getProductsQuantity() {
        return productsQuantity;
    }

    public void setProductsQuantity(String productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    public String getProductsPrice() {
        return productsPrice;
    }

    public void setProductsPrice(String productsPrice) {
        this.productsPrice = productsPrice;
    }

    public String getProductsCategory() {
        return productsCategory;
    }

    public void setProductsCategory(String productsCategory) {
        this.productsCategory = productsCategory;
    }
}
