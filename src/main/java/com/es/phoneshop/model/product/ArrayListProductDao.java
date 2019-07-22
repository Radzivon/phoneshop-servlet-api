package com.es.phoneshop.model.product;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance;

    private List<Product> productList;

    private ArrayListProductDao() {
        productList = new ArrayList<>();
    }

    synchronized public static ArrayListProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    @Override
    public synchronized Product getProduct(Long id) {
        return productList.stream().filter(product -> product.getId()
                .equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with " + id + " isn't"));
    }

    public synchronized List<Product> findProducts(String query, String sortBy, String order) {
        if (query != null) {
            if (sortBy != null && order != null) {
                return search(query).stream()
                        .filter(product -> (product.getPrice() != null && product.getStock() > 0))
                        .sorted(getComparator(sortBy, order))
                        .collect(Collectors.toList());
            } else {
                return search(query).stream()
                        .filter(product -> (product.getPrice() != null && product.getStock() > 0))
                        .collect(Collectors.toList());
            }
        }
        return productList.stream()
                .filter(product -> (product.getPrice() != null && product.getStock() > 0))
                .collect(Collectors.toList());

    }

    private Comparator<Product> getComparator(String sortBy, String order) {
        Comparator<Product> comparator;
        if ("description".equals(sortBy)) {
            comparator = Comparator.comparing(Product::getDescription);
        } else {
            if ("price".equals(sortBy)) {
                comparator = Comparator.comparing(Product::getPrice);
            } else {
                comparator = Comparator.comparing(Product::getId);
            }
        }
        if ("desc".equals(order)) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

    private List<Product> search(String query) {
        String[] wordsForSearch = query.toLowerCase().split("\\s+");
        List<Product> result = new ArrayList<>();
        for (String word : wordsForSearch) {
            result.addAll(productList.stream()
                    .filter(product -> product.getDescription().toLowerCase().contains(word.toLowerCase()))
                    .distinct()
                    .collect(Collectors.toList()));
        }
        return result.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product is null");
        }
        productList.add(product);
    }

    @Override
    public synchronized void delete(Long id) {
        productList.removeIf(product -> product.getId().equals(id));
    }
}
