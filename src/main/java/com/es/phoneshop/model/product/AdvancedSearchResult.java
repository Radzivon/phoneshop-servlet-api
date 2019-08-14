package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdvancedSearchResult {
    private List<Product> products;
    private boolean hasError;
    private Map<String, String> mapErrors;

    public AdvancedSearchResult() {
        mapErrors = new LinkedHashMap<>();
        products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean hasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Map<String, String> getMapErrors() {
        return mapErrors;
    }

    public void setMapErrors(Map<String, String> mapErrors) {
        this.mapErrors = mapErrors;
    }

    public void addError(String errorName, String message) {
        mapErrors.put(errorName, message);
    }
}
