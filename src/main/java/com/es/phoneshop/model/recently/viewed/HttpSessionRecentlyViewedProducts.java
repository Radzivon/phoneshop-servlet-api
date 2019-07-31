package com.es.phoneshop.model.recently.viewed;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

public class HttpSessionRecentlyViewedProducts implements RecentlyViewedProductsService {
    private static final String RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE = "recentlyviewed";
    private static final int MAX_QUANTITY_PRODUCTS = 3;
    private static HttpSessionRecentlyViewedProducts instance = new HttpSessionRecentlyViewedProducts();
    private ProductDao productDao;
    private AddToRecentlyViewedProductsResult addToRecentlyViewedProductsResult;

    private HttpSessionRecentlyViewedProducts() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static HttpSessionRecentlyViewedProducts getInstance() {
        return instance;
    }

    @Override
    public List<Product> getRecentlyViewedProducts(HttpServletRequest request) {
        List<Product> recentlyViewedProducts = (List<Product>) request.getSession().getAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE);
        if (recentlyViewedProducts == null) {
            recentlyViewedProducts = new LinkedList<>();
            request.getSession().setAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE, recentlyViewedProducts);
        }
        return recentlyViewedProducts;
    }

    @Override
    public void add(HttpServletRequest request) {
        LinkedList<Product> recentlyViewedProducts = (LinkedList<Product>) getRecentlyViewedProducts(request);

        Product product = productDao.getProduct(parseProductId(request));
        recentlyViewedProducts.remove(product);
        if (recentlyViewedProducts.size() == MAX_QUANTITY_PRODUCTS) {
            recentlyViewedProducts.removeLast();
        }
        recentlyViewedProducts.addFirst(product);
        addToRecentlyViewedProductsResult.setProducts(recentlyViewedProducts);
    }

    public Long parseProductId(HttpServletRequest request) {
        return Long.valueOf(request.getPathInfo()
                .substring(1));
    }
}
