package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    private static OrderServiceImpl instance = new OrderServiceImpl();
    private OrderDao orderDao;
    private int numberOfDaysForDelivery = 1;
    private int deliveryHour = 9;

    public OrderServiceImpl() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    public static OrderServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Order getOrder(Cart cart) {
        Order order = new Order();
        order.setCartItems(new ArrayList<>(cart.getCartItems()));
        order.setSubTotalCost(cart.getSubTotalCost());
        order.setTotalQuantity(cart.getTotalQuantity());

        LocalDate tomorrow = getTomorrowDate();
        order.setDeliveryDate(tomorrow);

        return order;
    }

    @Override
    public void placeOrder(Order order) {
        order.setId(UUID.randomUUID().toString());
        orderDao.save(order);
    }

    private LocalDate getTomorrowDate() {
        LocalDate today = LocalDate.now();
        return today.plusDays(numberOfDaysForDelivery);
    }
}
