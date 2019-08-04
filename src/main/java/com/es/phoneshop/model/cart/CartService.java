package com.es.phoneshop.model.cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);
    boolean add(HttpServletRequest request);
    boolean update(HttpServletRequest request);
    void delete(HttpServletRequest request);
}
