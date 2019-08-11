package com.es.phoneshop.model.order;

public interface OrderDao {
    void save(Order order);
    Order getById(String id);
}
