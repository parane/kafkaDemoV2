package com.para.kafka.demoKafka.dto;

public class Customer {
    private int customerID;
    private String customerName;
    public Customer(int ID, String name) {
        this.customerID = ID;
        this.customerName = name;
    }
    public int getID() {
        return customerID;
    }
    public String getName() {
        return customerName;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}