package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceMethodsResult;
import com.es.phoneshop.model.cart.HttpSessionCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CartPageServlet extends HttpServlet {
    private CartService cartService;
    private static final String JSP_PATH = "/WEB-INF/pages/cart.jsp";
    private static final String CART = "cart";
    private static final String URL_MESSAGE = "?message=Updated successfully";
    private static final String QUANTITY = "quantity";

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = cartService.getCart(session);

        request.setAttribute(CART, cart);

        request.getRequestDispatcher(JSP_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        HttpSession session = request.getSession();

        CartServiceMethodsResult cartServiceMethodsResult = cartService.update(session, productIds, quantities);

        if (cartServiceMethodsResult.hasError()) {
            request.setAttribute("errors", cartServiceMethodsResult.getErrors());
            doGet(request, response);
        } else {
            request.setAttribute(CART, cartServiceMethodsResult.getCart());
            response.sendRedirect(request.getRequestURI() +
                    URL_MESSAGE);
        }
    }


}
