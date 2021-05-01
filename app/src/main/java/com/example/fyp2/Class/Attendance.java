package com.example.fyp2.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attendance {
    @SerializedName("AttendanceID")
    @Expose
    private String AttendanceID;
    @SerializedName("userIc")
    @Expose
    private String userIc;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("day")
    @Expose
    private String day;

    public Attendance(String userIc) {
        this.userIc = userIc;
    }

    public Attendance(String attendanceID, String userIc, String time) {
        AttendanceID = attendanceID;
        this.userIc = userIc;
        this.time = time;
    }

    public Attendance(String userIc, String day) {
        this.userIc = userIc;
        this.day = day;
    }

    public String getAttendanceID() {
        return AttendanceID;
    }

    public void setAttendanceID(String attendanceID) {
        AttendanceID = attendanceID;
    }

    public String getUserIc() {
        return userIc;
    }

    public void setUserIc(String userIc) {
        this.userIc = userIc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
