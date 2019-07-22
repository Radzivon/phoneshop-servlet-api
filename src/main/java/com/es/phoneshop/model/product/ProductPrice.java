package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductPrice {
    private String date;
    private BigDecimal price;

    public ProductPrice(String date, BigDecimal price) {
        this.date = date;
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(price, that.price) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, date);
    }
}
