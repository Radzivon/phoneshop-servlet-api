package com.es.phoneshop.model.product;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductService {
    private static ProductService instance = new ProductService();
    private final String MATCH_ALL_QUERY = "";
    private ProductDao productDao = ArrayListProductDao.getInstance();

    public static ProductService getInstance() {
        return instance;
    }

    public List<Product> findProducts(String query, String sortBy, String order) {
        query = query == null ? MATCH_ALL_QUERY : query;
        String sortOption = sortBy + order;
        Comparator<Product> comparator = ProductSortOption.getComparator(sortOption);
        if (comparator == null) {
            return search(query);
        }
        return  search(query).stream()
                .sorted(comparator).collect(Collectors.toList());
    }

    private List<Product> search(String query) {
        String[] wordsForSearch = query.toLowerCase().split("\\s+");
        return productDao.findProducts().stream()
                .collect(Collectors.toMap(product -> product, product -> Arrays.stream(wordsForSearch)
                        .filter(word -> product.getDescription().toLowerCase().contains(word)).count()))
                .entrySet().stream()
                .filter(map -> map.getValue() > 0)
                .sorted(Comparator.comparing(Map.Entry<Product, Long>::getValue).reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Product getProductById(Long id) {
        return productDao.getProduct(id);
    }
}
