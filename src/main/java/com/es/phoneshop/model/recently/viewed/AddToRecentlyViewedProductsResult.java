package com.es.phoneshop.model.recently.viewed;

import com.es.phoneshop.model.product.Product;

import java.util.LinkedList;
import java.util.List;

public class AddToRecentlyViewedProductsResult {
    private static LinkedList<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(LinkedList<Product> products) {
        AddToRecentlyViewedProductsResult.products = products;
    }
}
