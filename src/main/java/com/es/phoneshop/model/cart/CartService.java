package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

public interface CartService {
    Cart getCart();
    void add(Cart cart, Product product, Long quantity);
}
