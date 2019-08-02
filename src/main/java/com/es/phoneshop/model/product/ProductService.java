package com.es.phoneshop.model.product;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductService {
    private final String QUERY = "";
    private ProductDao productDao = ArrayListProductDao.getInstance();

    public List<Product> findProducts(String query, String sortBy, String order) {
        Comparator<Product> comparator = null;
        query = query == null ? QUERY : query;
        comparator = "price".equals(sortBy)
                ? "asc".equals(order)
                ? ProductSortOption.SORT_PRICE : ProductSortOption.SORT_PRICE_DESC
                : "description".equals(sortBy) ? "asc".equals(order)
                ? ProductSortOption.SORT_DESCRIPTION : ProductSortOption.SORT_DESCRIPTION_DESC
                : null;
        if (comparator == null) {
            return search(query).stream().collect(Collectors.toList());
        }
        return search(query).stream().sorted(comparator).collect(Collectors.toList());
    }

    private List<Product> search(String query) {
        String[] wordsForSearch = query.toLowerCase().split("\\s+");
        return productDao.findProducts().stream()
                .collect(Collectors.toMap(product -> product, product -> Arrays.stream(wordsForSearch)
                        .filter(word -> product.getDescription().toLowerCase().contains(word)).count()))
                .entrySet().stream()
                .filter(map -> map.getValue() > 0)
                .sorted((x, y) -> y.getValue().compareTo(x.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Product getProductById(Long id) {
        return productDao.getProduct(id);
    }
}
