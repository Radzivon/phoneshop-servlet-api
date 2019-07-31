package com.es.phoneshop.model.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class Cart {
    private List<CartItem> cartItems;
    private BigDecimal totalCost;
    private int totalQuantity;

    public Cart() {
        cartItems = new ArrayList<>();
        totalCost = new BigDecimal(0);
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public String toString() {
        return "Quantity: " + totalQuantity + " Total: " + totalCost + Currency.getInstance(Locale.US) + cartItems;

    }
}
