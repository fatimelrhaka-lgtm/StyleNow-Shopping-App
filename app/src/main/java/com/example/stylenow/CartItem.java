package com.example.stylenow;

public class CartItem {
    public Item item;
    public int quantity;
    public String selectedSize;

    public CartItem(Item item, int quantity, String selectedSize) {
        this.item = item;
        this.quantity = quantity;
        this.selectedSize = selectedSize;
    }

    public double getTotalPrice() {
        return item.price * quantity;
    }
}
