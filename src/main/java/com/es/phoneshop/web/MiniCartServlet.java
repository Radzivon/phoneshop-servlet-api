package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MiniCartServlet extends HttpServlet {
    private CartService cartService;
    private static final String JSP_PATH = "/WEB-INF/pages/minicart.jsp";
    private static final String CART = "cart";

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = cartService.getCart(session);

        request.setAttribute(CART, cart);

        request.getRequestDispatcher(JSP_PATH).include(request, response);
    }
}
