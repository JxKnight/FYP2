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
    @SerializedName("productsPrice")
    @Expose
    private String productsPrice;
    @SerializedName("productsCategory")
    @Expose
    private String productsCategory;
    @SerializedName("productsImage")
    @Expose
    private String productsImage;

    public Product(String productsCategory) {
        this.productsCategory = productsCategory;
    }

    public Product(String productsId, String productsName, String productsDescription, String productsPrice, String productsCategory, String productsImage) {
        this.productsId = productsId;
        this.productsName = productsName;
        this.productsDescription = productsDescription;
        this.productsPrice = productsPrice;
        this.productsCategory = productsCategory;
        this.productsImage = productsImage;
    }

    public Product(String productsId, String productsDescription) {
        this.productsId = productsId;
        this.productsDescription = productsDescription;
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

    public String getProductsImage() {
        return productsImage;
    }

    public void setProductsImage(String productsImage) {
        this.productsImage = productsImage;
    }
}
