package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

public class AddToCartResult {
    private static Cart cart;
    private static Product product;
    private static String errorMessage;

    public  String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        AddToCartResult.errorMessage = errorMessage;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
