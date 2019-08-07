package com.es.phoneshop.model.product;

import java.util.Comparator;

public enum ProductSortOption implements Comparator<Product> {
    SORT_DESCRIPTION(Comparator.comparing(Product::getDescription), "descriptionasc"),
    SORT_DESCRIPTION_DESC(Comparator.comparing(Product::getDescription).reversed(), "descriptiondesc"),
    SORT_PRICE(Comparator.comparing(Product::getPrice), "priceasc"),
    SORT_PRICE_DESC(Comparator.comparing(Product::getPrice).reversed(), "pricedesc");

    private Comparator<Product> comparator;
    private String sortOption;

    ProductSortOption(Comparator<Product> productComparator, String sortOption) {
        this.comparator = productComparator;
        this.sortOption = sortOption;
    }

    @Override
    public int compare(Product o1, Product o2) {
        return comparator.compare(o1, o2);
    }

    public static Comparator<Product> getComparator(String sortOption) {
        for (ProductSortOption productSortOption : values()) {
            if (productSortOption.sortOption.equals(sortOption)) {
                return productSortOption.comparator;
            }
        }
        return null;
    }
}
