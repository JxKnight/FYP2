package com.example.fyp2.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTP {
    @SerializedName("otpID")
    @Expose
    private String otpID;
    @SerializedName("dateStart")
    @Expose
    private String dateStart;
    @SerializedName("dateEnd")
    @Expose
    private String dateEnd;
    @SerializedName("userIc")
    @Expose
    private String userIc;
    @SerializedName("otpNum")
    @Expose
    private String otpNum;
    @SerializedName("currentDate")
    @Expose
    private String currentDate;
    @SerializedName("qrCodeString")
    @Expose
    private String qrCodeString;

    public OTP(String userIc, String otpNum, String qrCodeString) {
        this.userIc = userIc;
        this.otpNum = otpNum;
        this.qrCodeString = qrCodeString;
    }

    public String getOtpID() {
        return otpID;
    }

    public void setOtpID(String otpID) {
        this.otpID = otpID;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getUserIc() {
        return userIc;
    }

    public void setUserIc(String userIc) {
        this.userIc = userIc;
    }

    public String getOtpNum() {
        return otpNum;
    }

    public void setOtpNum(String otpNum) {
        this.otpNum = otpNum;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getQrCodeString() {
        return qrCodeString;
    }

    public void setQrCodeString(String qrCodeString) {
        this.qrCodeString = qrCodeString;
    }
}
