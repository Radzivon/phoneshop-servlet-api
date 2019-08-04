package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceMethodsResult;
import com.es.phoneshop.model.cart.HttpSessionCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartPageServlet extends HttpServlet {
    private CartService cartService;
    private CartServiceMethodsResult cartServiceMethodsResult;
    private static final String JSP_PATH = "/WEB-INF/pages/cart.jsp";
    private static final String CART = "cart";
    private static final String URL_MESSAGE = "?message=Updated successfully";

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
        cartServiceMethodsResult = new CartServiceMethodsResult();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);

        request.setAttribute(CART, cart);

        request.getRequestDispatcher(JSP_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean hasError = cartService.update(request);

        if (hasError) {
            request.setAttribute("errors", cartServiceMethodsResult.getErrors());
            doGet(request, response);
        } else {
            request.setAttribute(CART, cartServiceMethodsResult.getCart());
            response.sendRedirect(request.getRequestURI() +
                    URL_MESSAGE);
        }
    }

}
