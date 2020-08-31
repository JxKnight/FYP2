package com.example.fyp2.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Role {
    @SerializedName("roleName")
    @Expose
    private String roleName;
    @SerializedName("roleNum")
    @Expose
    private String roleNum;
    @SerializedName("roleDescription")
    @Expose
    private String roleDescription;
    @SerializedName("roleRate")
    @Expose
    private String roleRate;
    @SerializedName("warehouse")
    @Expose
    private String warehouse;
    @SerializedName("orders")
    @Expose
    private String orders;
    @SerializedName("customers")
    @Expose
    private String customers;
    @SerializedName("reports")
    @Expose
    private String reports;
    @SerializedName("tasks")
    @Expose
    private String tasks;

    public Role(String roleNum) {
        this.roleNum = roleNum;
    }

    public Role(String roleName, String roleNum, String roleDescription, String roleRate, String warehouse, String orders, String customers, String reports, String tasks) {
        this.roleName = roleName;
        this.roleNum = roleNum;
        this.roleDescription = roleDescription;
        this.roleRate = roleRate;
        this.warehouse = warehouse;
        this.orders = orders;
        this.customers = customers;
        this.reports = reports;
        this.tasks = tasks;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getRoleRate() {
        return roleRate;
    }

    public void setRoleRate(String roleRate) {
        this.roleRate = roleRate;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getCustomers() {
        return customers;
    }

    public void setCustomers(String customers) {
        this.customers = customers;
    }

    public String getReports() {
        return reports;
    }

    public void setReports(String reports) {
        this.reports = reports;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public String getRoleNum() {
        return roleNum;
    }

    public void setRoleNum(String roleNum) {
        this.roleNum = roleNum;
    }
}
