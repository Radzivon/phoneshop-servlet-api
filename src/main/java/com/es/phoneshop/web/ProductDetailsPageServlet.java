package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.*;
import com.es.phoneshop.model.recently.viewed.AddToRecentlyViewedProductsResult;
import com.es.phoneshop.model.recently.viewed.HttpSessionRecentlyViewedProducts;
import com.es.phoneshop.model.recently.viewed.RecentlyViewedProductsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private CartService cartService;
    private RecentlyViewedProductsService recentlyViewedProductsService;
    private static final String PRODUCT = "product";
    private static final String JSP_PATH = "/WEB-INF/pages/product.jsp";
    private static final String CART = "cart";
    private static final String RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE = "recentlyviewed";
    private static final String URL_MESSAGE = "?message=Added to cart successfully";
    private static final String QUANTITY = "quantity";
    private static final String ERROR = "error";

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
        recentlyViewedProductsService = HttpSessionRecentlyViewedProducts.getInstance();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = cartService.getCart(session);
        String stringProductId = request.getPathInfo().substring(1);

        AddToRecentlyViewedProductsResult addToRecentlyViewedProductsResult = recentlyViewedProductsService.add(session, stringProductId);

        request.setAttribute(CART, cart);
        request.setAttribute(PRODUCT, addToRecentlyViewedProductsResult.getProduct());
        request.setAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE, addToRecentlyViewedProductsResult.getProducts());

        request.getRequestDispatcher(JSP_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String stringProductId= request.getPathInfo().substring(1);
        String quantity = request.getParameter(QUANTITY);

        AddCartResult addCartResult = cartService.add(session, stringProductId, quantity);
        if (addCartResult.hasError()) {
            request.setAttribute(ERROR, addCartResult.getErrorMessage());
            doGet(request, response);
            return;
        }

        request.setAttribute(CART, addCartResult.getCart());
        request.setAttribute(PRODUCT, addCartResult.getProduct());
        response.sendRedirect(request.getRequestURI() + URL_MESSAGE);

    }
}
