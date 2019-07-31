package com.es.phoneshop.model.recently.viewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RecentlyViewedProductsService {
    List<Product> getRecentlyViewedProducts(HttpServletRequest request);
    void add(HttpServletRequest request);
}
