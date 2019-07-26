package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public class HttpSessionCartService implements CartService {
    private static HttpSessionCartService instance;

    private Cart cart;

    private HttpSessionCartService() {
        cart = new Cart();
    }

    private static synchronized HttpSessionCartService getInstance() {
        if (instance == null) {
            instance = new HttpSessionCartService();
        }
        return instance;
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void add(Cart cart, Product product, Long quantity) {
        cart.getCartItems().add(new CartItem(product.getId(), quantity));
        recalculateCart(cart);
    }

    private void recalculateCart(Cart cart) {
        //TODO
    }

}
