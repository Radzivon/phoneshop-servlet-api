package com.es.phoneshop.model.order;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ArrayListOrderDao implements OrderDao {
    private static ArrayListOrderDao instance = new ArrayListOrderDao();
    private List<Order> orderList;

    private ArrayListOrderDao() {
        orderList = new CopyOnWriteArrayList<>();
    }

    public static OrderDao getInstance() {
        return instance;
    }

    @Override
    public void save(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order is null");
        }
        orderList.add(order);
    }

    @Override
    public Order getById(String id) {
        return orderList.stream().filter(order -> order.getId().equals(id)).findFirst().get();
    }
}
