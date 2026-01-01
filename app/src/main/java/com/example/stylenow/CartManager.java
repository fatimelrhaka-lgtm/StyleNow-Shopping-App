package com.example.stylenow;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private final List<CartItem> cartItems = new ArrayList<>();

    private CartManager() {}

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(Item item, String selectedSize) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.item.id == item.id && cartItem.selectedSize.equals(selectedSize)) {
                cartItem.quantity++;
                return;
            }
        }
        cartItems.add(new CartItem(item, 1, selectedSize));
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem cartItem : cartItems) {
            total += cartItem.getTotalPrice();
        }
        return total;
    }
}
