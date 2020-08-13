package com.example.fyp2.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Buyer {
    @SerializedName("buyerId")
    @Expose
    private String buyerId;
    @SerializedName("buyerName")
    @Expose
    private String buyerName;
    @SerializedName("buyerContact")
    @Expose
    private String buyerContact;
    @SerializedName("buyerLocation")
    @Expose
    private String buyerLocation;
    @SerializedName("buyerAddress1")
    @Expose
    private String buyerAddress1;
    @SerializedName("buyerAddress2")
    @Expose
    private String buyerAddress2;
    @SerializedName("buyerRate")
    @Expose
    private String buyerRate;
    @SerializedName("userIc")
    @Expose
    private String userIc;
    @SerializedName("userCheck")
    @Expose
    private String userCheck;

    public Buyer(String buyerId, String buyerName, String buyerContact, String buyerLocation, String buyerAddress1, String buyerAddress2, String buyerRate, String userCheck) {
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.buyerContact = buyerContact;
        this.buyerLocation = buyerLocation;
        this.buyerAddress1 = buyerAddress1;
        this.buyerAddress2 = buyerAddress2;
        this.buyerRate = buyerRate;
        this.userCheck = userCheck;
    }

    public Buyer(String buyerName, String buyerContact, String buyerLocation, String buyerAddress1, String buyerAddress2, String userIc) {
        this.buyerName = buyerName;
        this.buyerContact = buyerContact;
        this.buyerLocation = buyerLocation;
        this.buyerAddress1 = buyerAddress1;
        this.buyerAddress2 = buyerAddress2;
        this.userIc = userIc;
    }

    public Buyer(String buyerId, String buyerName, String buyerContact, String buyerLocation, String userCheck) {
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.buyerContact = buyerContact;
        this.buyerLocation = buyerLocation;
        this.userCheck = userCheck;
    }

    public Buyer(String buyerId, String buyerName, String buyerContact, String buyerLocation) {
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.buyerContact = buyerContact;
        this.buyerLocation = buyerLocation;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerContact() {
        return buyerContact;
    }

    public void setBuyerContact(String buyerContact) {
        this.buyerContact = buyerContact;
    }

    public String getBuyerLocation() {
        return buyerLocation;
    }

    public void setBuyerLocation(String buyerLocation) {
        this.buyerLocation = buyerLocation;
    }

    public String getBuyerAddress1() {
        return buyerAddress1;
    }

    public void setBuyerAddress1(String buyerAddress1) {
        this.buyerAddress1 = buyerAddress1;
    }

    public String getBuyerAddress2() {
        return buyerAddress2;
    }

    public void setBuyerAddress2(String buyerAddress2) {
        this.buyerAddress2 = buyerAddress2;
    }

    public String getBuyerRate() {
        return buyerRate;
    }

    public void setBuyerRate(String buyerRate) {
        this.buyerRate = buyerRate;
    }

    public String getUserIc() {
        return userIc;
    }

    public void setUserIc(String userIc) {
        this.userIc = userIc;
    }

    public String getUserCheck() {
        return userCheck;
    }

    public void setUserCheck(String userCheck) {
        this.userCheck = userCheck;
    }
}
