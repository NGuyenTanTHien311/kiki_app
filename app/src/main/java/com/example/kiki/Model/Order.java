package com.example.kiki.Model;

import java.util.ArrayList;

public class Order {
        String username, phonenumber, address, userId;
        String OrderId;
        ArrayList<Cart> carts;
    public Order(){
    }

    public Order(String orderId,String userId, String username, String phonenumber, String address, ArrayList<Cart> carts) {
        this.userId = userId;
        this.username = username;
        this.phonenumber = phonenumber;
        this.address = address;
        OrderId = orderId;
        this.carts = carts;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public ArrayList<Cart> getCarts() {
        return carts;
    }

    public void setCarts(ArrayList<Cart> carts) {
        this.carts = carts;
    }
}
