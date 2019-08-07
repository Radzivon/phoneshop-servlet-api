package com.es.phoneshop.model.cart;

import javax.servlet.http.HttpSession;
import java.util.Locale;

public interface CartService {
    Cart getCart(HttpSession session);
    CartServiceMethodsResult add(HttpSession session, String requestPathInfo, String stringQuantity, Locale locale);
    CartServiceMethodsResult update(HttpSession session, String[] productIds, String[] quantities);
    void delete(HttpSession httpSession, String requestPathInfo);
}
