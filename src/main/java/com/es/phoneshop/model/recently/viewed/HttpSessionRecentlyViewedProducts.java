package com.es.phoneshop.model.recently.viewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

public class HttpSessionRecentlyViewedProducts implements RecentlyViewedProductsService {
    private static final String RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE = "recentlyviewed";
    private static final int MAX_QUANTITY_PRODUCTS = 3;
    private static HttpSessionRecentlyViewedProducts instance = new HttpSessionRecentlyViewedProducts();

    private LinkedList<Product> recentlyViewedProducts;

    private HttpSessionRecentlyViewedProducts() {
        recentlyViewedProducts = new LinkedList<>();
    }

    public synchronized static HttpSessionRecentlyViewedProducts getInstance() {
        return instance;
    }

    @Override
    public LinkedList<Product> getRecentlyViewedProducts(HttpServletRequest request) {
        LinkedList<Product> recentlyViewedProducts = (LinkedList<Product>) request.getSession().getAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE);
        if (recentlyViewedProducts == null) {
            recentlyViewedProducts = new LinkedList<>();
            request.getSession().setAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE, recentlyViewedProducts);
        }
        return recentlyViewedProducts;
    }

    @Override
    public void add(LinkedList<Product> recentlyViewedProducts, Product product) {
        if (recentlyViewedProducts.size() == MAX_QUANTITY_PRODUCTS) {
            recentlyViewedProducts.removeLast();
        }
        recentlyViewedProducts.addFirst(product);
    }
}
