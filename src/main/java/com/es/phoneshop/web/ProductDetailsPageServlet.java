package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.AddToCartResult;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.recently.viewed.AddToRecentlyViewedProductsResult;
import com.es.phoneshop.model.recently.viewed.HttpSessionRecentlyViewedProducts;
import com.es.phoneshop.model.recently.viewed.RecentlyViewedProductsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private CartService cartService;
    private RecentlyViewedProductsService recentlyViewedProductsService;
    private AddToCartResult addToCartResult;
    private AddToRecentlyViewedProductsResult addToRecentlyViewedProductsResult;
    private static final String PRODUCT = "product";
    private static final String JSP_PATH = "/WEB-INF/pages/product.jsp";
    private static final String CART = "cart";
    private static final String RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE = "recentlyviewed";
    private static final String URL_MESSAGE = "?message=Added to cart successfully";

    @Override
    public void init() throws ServletException {
        cartService = HttpSessionCartService.getInstance();
        recentlyViewedProductsService = HttpSessionRecentlyViewedProducts.getInstance();
        addToCartResult = new AddToCartResult();
        addToRecentlyViewedProductsResult = new AddToRecentlyViewedProductsResult();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        recentlyViewedProductsService.add(request);

        request.setAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE, addToRecentlyViewedProductsResult.getProducts());

        request.getRequestDispatcher(JSP_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean isError = cartService.add(request);
        if (isError) {
            request.setAttribute("error", addToCartResult.getErrorMessage());
            doGet(request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + URL_MESSAGE);

        request.setAttribute(CART, addToCartResult.getCart());
        request.setAttribute(PRODUCT, addToCartResult.getProduct());
    }

}
