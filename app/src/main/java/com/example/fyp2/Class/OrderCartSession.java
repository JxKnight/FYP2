package com.example.fyp2.Class;

public class OrderCartSession {
    private String productId;
    private String productName;
    private String orderQuantity;

    public OrderCartSession(String productId, String productName, String orderQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.orderQuantity = orderQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
