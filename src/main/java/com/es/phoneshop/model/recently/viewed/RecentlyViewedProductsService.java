package com.es.phoneshop.model.recently.viewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

public interface RecentlyViewedProductsService {
    LinkedList<Product> getRecentlyViewedProducts(HttpServletRequest request);
    void add(LinkedList<Product> recentlyViewedProducts, Product product);
}
