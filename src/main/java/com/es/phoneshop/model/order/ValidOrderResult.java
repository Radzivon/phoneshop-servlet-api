package com.es.phoneshop.model.order;

import java.util.Map;
import java.util.LinkedHashMap;

public class ValidOrderResult {
    private boolean hasError;
    private Map<String, String> mapErrors;

    public ValidOrderResult() {
        this.mapErrors = new LinkedHashMap<>();
    }

    public Map<String, String> getMapErrors() {
        return mapErrors;
    }

    public void addError(String errorName, String message) {
        mapErrors.put(errorName, message);
    }

    public boolean hasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }
}
