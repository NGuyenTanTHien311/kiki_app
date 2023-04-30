package com.example.kiki.Model;

import java.io.Serializable;

public class Cart implements Serializable {
    public Cart(){
    }
    private Truyen truyen;
    private int stock;
    private boolean ifRent;
    private String CartId;
    private double total;
    public Cart(String CartId, Truyen truyen , int stock, double total, boolean ifRent) {
        this.truyen = truyen;
        this.stock = stock;
        this.total = total;
        this.ifRent = ifRent;
        this.CartId = CartId;
    }

    public String getCartId() {
        return CartId;
    }

    public void setCartId(String cartId) {
        CartId = cartId;
    }

    public boolean isIfRent() {
        return ifRent;
    }

    public void setIfRent(boolean ifRent) {
        this.ifRent = ifRent;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Truyen getTruyen() {
        return truyen;
    }

    public void setTruyen(Truyen truyen) {
        this.truyen = truyen;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
