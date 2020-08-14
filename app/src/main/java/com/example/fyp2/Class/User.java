package com.example.fyp2.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userPassword")
    @Expose
    private String password;
    @SerializedName("userContact")
    @Expose
    private String contact;
    @SerializedName("userIc")
    @Expose
    private String userIc;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("userAddress")
    @Expose
    private String address;
    @SerializedName("userPostCode")
    @Expose
    private String postCode;
    @SerializedName("userState")
    @Expose
    private String state;
    @SerializedName("userRole")
    @Expose
    private String role;

    public User(String password, String contact, String userIc, String firstName, String lastName, String address, String postCode, String state, String role) {
        this.password = password;
        this.contact = contact;
        this.userIc = userIc;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postCode = postCode;
        this.state = state;
        this.role = role;
    }

    public User(String password, String contact, String userIc) {
        this.password = password;
        this.contact = contact;
        this.userIc = userIc;
    }

    public User(String userIc) {
        this.userIc = userIc;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUserIc() {
        return userIc;
    }

    public void setUserIc(String userIc) {
        this.userIc = userIc;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
