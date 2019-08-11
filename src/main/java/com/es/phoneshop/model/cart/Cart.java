package com.es.phoneshop.model.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class Cart {
    private List<CartItem> cartItems;
    private BigDecimal subTotalCost;
    private int totalQuantity;

    public Cart() {
        cartItems = new ArrayList<>();
        subTotalCost = new BigDecimal(0);
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getSubTotalCost() {
        return subTotalCost;
    }

    public void setSubTotalCost(BigDecimal subTotalCost) {
        this.subTotalCost = subTotalCost;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    void recalculateCart() {
        BigDecimal totalCost = new BigDecimal(0);
        int totalQuantity = 0;
        for (CartItem cartItem : cartItems) {
            totalCost = totalCost.add(cartItem.getProduct().getPrice()
                    .multiply(new BigDecimal(cartItem.getQuantity())));
            totalQuantity += cartItem.getQuantity();
        }
        this.subTotalCost = totalCost;
        this.totalQuantity = totalQuantity;
    }
}
