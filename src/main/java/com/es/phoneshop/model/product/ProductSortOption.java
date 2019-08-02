package com.es.phoneshop.model.product;

import java.util.Comparator;

public enum ProductSortOption implements Comparator<Product> {
    SORT_DESCRIPTION(Comparator.comparing(Product::getDescription)),
    SORT_DESCRIPTION_DESC(Comparator.comparing(Product::getDescription).reversed()),
    SORT_PRICE(Comparator.comparing(Product::getPrice)),
    SORT_PRICE_DESC(Comparator.comparing(Product::getPrice).reversed());

    private Comparator<Product> comparator;

    ProductSortOption(Comparator<Product> productComparator) {
        comparator = productComparator;
    }

    @Override
    public int compare(Product o1, Product o2) {
        return comparator.compare(o1, o2);
    }
}
