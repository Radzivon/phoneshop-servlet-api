package com.es.phoneshop.model.recently.viewed;

import com.es.phoneshop.model.product.Product;

import java.util.LinkedList;
import java.util.List;

public class AddToRecentlyViewedProductsResult {
    private static LinkedList<Product> products;
    private static Product product;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(LinkedList<Product> products) {
        AddToRecentlyViewedProductsResult.products = products;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        AddToRecentlyViewedProductsResult.product = product;
    }
}
