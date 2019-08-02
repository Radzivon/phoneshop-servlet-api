package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.AddToCartResult;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;
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
    private ProductService productService;
    private static final String PRODUCT = "product";
    private static final String JSP_PATH = "/WEB-INF/pages/product.jsp";
    private static final String CART = "cart";
    private static final String RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE = "recentlyviewed";
    private static final String URL_MESSAGE = "?message=Added to cart successfully";

    @Override
    public void init() throws ServletException {
        cartService = HttpSessionCartService.getInstance();
        productService = new ProductService();
        recentlyViewedProductsService = HttpSessionRecentlyViewedProducts.getInstance();
        addToCartResult = new AddToCartResult();
        addToRecentlyViewedProductsResult = new AddToRecentlyViewedProductsResult();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Long productsId = parseProductId(request);
        Product product = productService.getProductById(productsId);

        recentlyViewedProductsService.add(request);

        request.setAttribute(CART, cart);
        request.setAttribute(PRODUCT, product);
        request.setAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE, addToRecentlyViewedProductsResult.getProducts());

        request.getRequestDispatcher(JSP_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean hasError = cartService.add(request);
        if (hasError) {
            request.setAttribute("error", addToCartResult.getErrorMessage());
            doGet(request, response);
            return;
        }

        request.setAttribute(CART, addToCartResult.getCart());
        request.setAttribute(PRODUCT, addToCartResult.getProduct());
        response.sendRedirect(request.getRequestURI() + URL_MESSAGE);

    }

    public Long parseProductId(HttpServletRequest request) {
        return Long.valueOf(request.getPathInfo()
                .substring(1));
    }
}
