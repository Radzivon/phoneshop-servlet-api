package com.es.phoneshop.model.product;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance = new ArrayListProductDao();

    private List<Product> productList;

    private ArrayListProductDao() {
        productList = new CopyOnWriteArrayList<>();
    }

    public static ArrayListProductDao getInstance() {
        return instance;
    }

    @Override
    public Product getProduct(Long id) {
        return productList.stream().filter(product -> product.getId()
                .equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product with  id: " + id + " isn't"));
    }


    public List<Product> findProducts() {
        return productList.stream()
                .filter(product -> (product.getPrice() != null && product.getStock() > 0))
                .collect(Collectors.toList());

    }

    @Override
    public void save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product is null");
        }
        productList.add(product);
    }

    @Override
    public void delete(Long id) {
        productList.removeIf(product -> product.getId().equals(id));
    }
}
