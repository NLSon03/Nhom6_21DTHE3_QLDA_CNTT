package com.qlda.nhom6.model;

public class CartItem {
    private Thuoc product;
    private int quantity;

    // Constructors
    public CartItem(Thuoc product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Thuoc getProduct() {
        return product;
    }

    public void setProduct(Thuoc product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}