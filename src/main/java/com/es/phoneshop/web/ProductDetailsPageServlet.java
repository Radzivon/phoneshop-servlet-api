package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceMethodsResult;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.recently.viewed.AddToRecentlyViewedProductsResult;
import com.es.phoneshop.model.recently.viewed.HttpSessionRecentlyViewedProducts;
import com.es.phoneshop.model.recently.viewed.RecentlyViewedProductsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

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
        String requestPathInfo = request.getPathInfo();

        AddToRecentlyViewedProductsResult addToRecentlyViewedProductsResult = recentlyViewedProductsService.add(session, requestPathInfo);

        request.setAttribute(CART, cart);
        request.setAttribute(PRODUCT, addToRecentlyViewedProductsResult.getProduct());
        request.setAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE, addToRecentlyViewedProductsResult.getProducts());

        request.getRequestDispatcher(JSP_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String requestPathInfo = request.getPathInfo();
        String quantity = request.getParameter(QUANTITY);
        Locale locale = request.getLocale();
        CartServiceMethodsResult cartServiceMethodsResult = cartService.add(session, requestPathInfo, quantity, locale);
        if (cartServiceMethodsResult.hasError()) {
            request.setAttribute(ERROR, cartServiceMethodsResult.getErrorMessage());
            doGet(request, response);
            return;
        }

        request.setAttribute(CART, cartServiceMethodsResult.getCart());
        request.setAttribute(PRODUCT, cartServiceMethodsResult.getProduct());
        response.sendRedirect(request.getRequestURI() + URL_MESSAGE);

    }
}
