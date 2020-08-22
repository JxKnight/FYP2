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
    @SerializedName("buyerAddress")
    @Expose
    private String buyerAddress;
    @SerializedName("userIc")
    @Expose
    private String userIc;
    @SerializedName("adminCheck")
    @Expose
    private String adminCheck;

    public Buyer(String buyerId, String userIc, String adminCheck) {
        this.buyerId = buyerId;
        this.userIc = userIc;
        this.adminCheck = adminCheck;
    }

    public Buyer(String buyerName, String buyerContact, String buyerLocation, String buyerAddress, String userIc) {
        this.buyerName = buyerName;
        this.buyerContact = buyerContact;
        this.buyerLocation = buyerLocation;
        this.buyerAddress = buyerAddress;
        this.userIc = userIc;
    }

    public Buyer(String buyerLocation) {
        this.buyerLocation = buyerLocation;
    }

    public Buyer(String buyerId, String buyerLocation) {
        this.buyerId = buyerId;
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

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getUserIc() {
        return userIc;
    }

    public void setUserIc(String userIc) {
        this.userIc = userIc;
    }

    public String getAdminCheck() {
        return adminCheck;
    }

    public void setAdminCheck(String adminCheck) {
        this.adminCheck = adminCheck;
    }

}
