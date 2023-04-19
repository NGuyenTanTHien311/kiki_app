package com.example.kiki.Model;

public class Cart {
    public Cart(){
    }
    private Truyen truyen;
    private int stock;
    private double total;
    public Cart(Truyen truyen , int stock, double total) {
        this.truyen = truyen;
        this.stock = stock;
        this.total = total;
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
