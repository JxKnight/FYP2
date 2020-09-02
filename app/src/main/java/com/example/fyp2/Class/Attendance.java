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

    public Attendance(String userIc) {
        this.userIc = userIc;
    }

    public Attendance(String attendanceID, String userIc, String time) {
        AttendanceID = attendanceID;
        this.userIc = userIc;
        this.time = time;
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

}
