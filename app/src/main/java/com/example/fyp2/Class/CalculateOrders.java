package com.example.fyp2.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalculateOrders {
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("quantity")
    @Expose
    private String quantity;


    public CalculateOrders(String productID, String quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
