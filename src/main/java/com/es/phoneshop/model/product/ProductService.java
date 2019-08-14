package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.*;
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
        return search(query).stream()
                .sorted(comparator).collect(Collectors.toList());
    }

    List<Product> search(String query) {
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


    public AdvancedSearchResult advancedSearch(String description, String stringMinPrice, String stringMaxPrice, String stringMinStock, String stringMaxStock) {
        boolean hasError = false;
        AdvancedSearchResult advancedSearchResult = new AdvancedSearchResult();
        Map<String, String> errors = new HashMap<>();
        if (errors.size() != 0) {
            hasError = true;
            advancedSearchResult.setMapErrors(errors);
            advancedSearchResult.setHasError(hasError);
            return advancedSearchResult;
        }
        BigDecimal minPrice = new BigDecimal(0);
        BigDecimal maxPrice = new BigDecimal(0);
        int minStock = 0;
        int maxStock = 0;
        try {
            minPrice = new BigDecimal(parseStringNumber(stringMinPrice));
        } catch (NumberFormatException exception) {
            hasError = true;
            errors.put("errorMinPrice", "Not a number");
        }
        try {
            maxPrice = new BigDecimal(parseStringNumber(stringMaxPrice));
        } catch (NumberFormatException exception) {
            hasError = true;
            errors.put("errorMaxPrice", "Not a number");
        }
        try {
            minStock = parseStringNumber(stringMinStock);
        } catch (NumberFormatException exception) {
            hasError = true;
            errors.put("errorMinStock", "Not a number");
        }
        try {
            maxStock = parseStringNumber(stringMaxStock);
        } catch (NumberFormatException exception) {
            hasError = true;
            errors.put("errorMaxStock", "Not a number");
        }

        if (hasError) {
            advancedSearchResult.setMapErrors(errors);
            advancedSearchResult.setHasError(hasError);
            return advancedSearchResult;
        }

        BigDecimal finalMinPrice = minPrice;
        BigDecimal finalMaxPrice = maxPrice;
        int finalMinStock = minStock;
        int finalMaxStock = maxStock;
        List<Product> products = search(description).stream()
                .filter(product -> product.getPrice().compareTo(finalMinPrice) >= 0)
                .filter(product -> product.getPrice().compareTo(finalMaxPrice) <= 0)
                .filter(product -> product.getStock() >= finalMinStock)
                .filter(product -> product.getStock() <= finalMaxStock)
                .collect(Collectors.toList());

        advancedSearchResult.setProducts(products);

        return advancedSearchResult;
    }

    private int parseStringNumber(String number) {
        return Integer.valueOf(number);
    }

    public Product getProductById(Long id) {
        return productDao.getProduct(id);
    }
}
