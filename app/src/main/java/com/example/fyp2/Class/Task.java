package com.example.fyp2.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {
    @SerializedName("taskId")
    @Expose
    private String taskId;
    @SerializedName("taskDescription")
    @Expose
    private String taskDescription;
    @SerializedName("taskCreateDate")
    @Expose
    private String taskCreateDate;
    @SerializedName("taskUpdateDate")
    @Expose
    private String taskUpdateDate;
    @SerializedName("taskSequence")
    @Expose
    private String taskSequence;
    @SerializedName("userRole")
    @Expose
    private String userRole;
    @SerializedName("productsId")
    @Expose
    private String productsId;

    public Task(String taskDescription, String taskCreateDate, String taskUpdateDate, String taskSequence, String userRole, String productsId) {
        this.taskDescription = taskDescription;
        this.taskCreateDate = taskCreateDate;
        this.taskUpdateDate = taskUpdateDate;
        this.taskSequence = taskSequence;
        this.userRole = userRole;
        this.productsId = productsId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskCreateDate() {
        return taskCreateDate;
    }

    public void setTaskCreateDate(String taskCreateDate) {
        this.taskCreateDate = taskCreateDate;
    }

    public String getTaskUpdateDate() {
        return taskUpdateDate;
    }

    public void setTaskUpdateDate(String taskUpdateDate) {
        this.taskUpdateDate = taskUpdateDate;
    }

    public String getTaskSequence() {
        return taskSequence;
    }

    public void setTaskSequence(String taskSequence) {
        this.taskSequence = taskSequence;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getProductsId() {
        return productsId;
    }

    public void setProductsId(String productsId) {
        this.productsId = productsId;
    }
}
