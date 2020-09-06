package com.example.fyp2.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Salary {
    @SerializedName("salaryId")
    @Expose
    private String salaryId;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("userIc")
    @Expose
    private String userIc;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("totalHours")
    @Expose
    private String totalHours;
    @SerializedName("year")
    @Expose
    private String year;


    public String getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(String salaryId) {
        this.salaryId = salaryId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getUserIc() {
        return userIc;
    }

    public void setUserIc(String userIc) {
        this.userIc = userIc;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
