package com.es.phoneshop.model.recently.viewed;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

public class HttpSessionRecentlyViewedProducts implements RecentlyViewedProductsService {
    private static final String RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE = "recentlyviewed";
    private static final int MAX_QUANTITY_PRODUCTS = 3;
    private static HttpSessionRecentlyViewedProducts instance = new HttpSessionRecentlyViewedProducts();
    private ProductDao productDao;

    private HttpSessionRecentlyViewedProducts() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static HttpSessionRecentlyViewedProducts getInstance() {
        return instance;
    }

    @Override
    public List<Product> getRecentlyViewedProducts(HttpSession session) {
        List<Product> recentlyViewedProducts = (List<Product>) session.getAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE);
        if (recentlyViewedProducts == null) {
            recentlyViewedProducts = new LinkedList<>();
            session.setAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE, recentlyViewedProducts);
        }
        return recentlyViewedProducts;
    }

    @Override
    public AddToRecentlyViewedProductsResult add(HttpSession session, String requestPathInfo) {
        LinkedList<Product> recentlyViewedProducts = (LinkedList<Product>) getRecentlyViewedProducts(session);
        AddToRecentlyViewedProductsResult addToRecentlyViewedProductsResult = new AddToRecentlyViewedProductsResult();
        Product product = productDao.getProduct(parseProductId(requestPathInfo));
        recentlyViewedProducts.remove(product);
        if (recentlyViewedProducts.size() == MAX_QUANTITY_PRODUCTS) {
            recentlyViewedProducts.removeLast();
        }

        recentlyViewedProducts.addFirst(product);
        addToRecentlyViewedProductsResult.setProduct(product);
        addToRecentlyViewedProductsResult.setProducts(recentlyViewedProducts);
        return addToRecentlyViewedProductsResult;
    }

    public Long parseProductId(String requestPathInfo) {
        return Long.valueOf(requestPathInfo
                .substring(1));
    }
}
