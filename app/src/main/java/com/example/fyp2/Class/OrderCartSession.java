package com.example.fyp2.Class;

public class OrderCartSession {
    private String productId;
    private String orderQuantity;

    public OrderCartSession(String productId, String orderQuantity) {
        this.productId = productId;
        this.orderQuantity = orderQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
