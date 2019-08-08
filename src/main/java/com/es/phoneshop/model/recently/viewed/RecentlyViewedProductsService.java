package com.es.phoneshop.model.recently.viewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface RecentlyViewedProductsService {
    List<Product> getRecentlyViewedProducts(HttpSession session);
    AddToRecentlyViewedProductsResult add(HttpSession session, String productId);
}
