package com.es.phoneshop.model.product;

import java.util.Comparator;

public enum ProductSortOption implements Comparator<Product> {
    SORT_DESCRIPTION {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getDescription().compareTo(o2.getDescription());
        }
    },
    SORT_DESCRIPTION_DESC {
        @Override
        public int compare(Product o1, Product o2) {
            return o2.getDescription().compareTo(o1.getDescription());
        }
    },
    SORT_PRICE {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getPrice().compareTo(o2.getPrice());
        }
    },
    SORT_PRICE_DESC {
        @Override
        public int compare(Product o1, Product o2) {
            return o2.getPrice().compareTo(o1.getPrice());
        }
    };
    private Comparator<Product> comparator;

    ProductSortOption(Comparator<Product> productComparator) {
        comparator = productComparator;
    }
    ProductSortOption() {
    }


    public Comparator<Product> reversed() {
        return comparator.reversed();
    }
}
