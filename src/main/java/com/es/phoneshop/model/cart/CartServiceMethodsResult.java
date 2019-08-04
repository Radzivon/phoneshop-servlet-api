package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

public class CartServiceMethodsResult {
    private static Cart cart;
    private static Product product;
    private static String errorMessage;
    private static String[] errors;

    public String[] getErrors() {
        return errors;
    }

    public  void setErrors(String[] errors) {
        CartServiceMethodsResult.errors = errors;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        CartServiceMethodsResult.errorMessage = errorMessage;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        CartServiceMethodsResult.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        CartServiceMethodsResult.product = product;
    }
}
