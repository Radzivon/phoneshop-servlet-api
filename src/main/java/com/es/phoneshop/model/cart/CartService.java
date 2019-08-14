package com.es.phoneshop.model.cart;

import javax.servlet.http.HttpSession;

public interface CartService {
    Cart getCart(HttpSession session);
    AddCartResult add(HttpSession session, String stringProductId, String stringQuantity);
    UpdateCartResult update(HttpSession session, String[] productIds, String[] quantities);
    void delete(HttpSession httpSession, String stringProductId);
    void clearCart(Cart cart);
}
