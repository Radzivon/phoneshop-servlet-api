package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

        Date tomorrow = getTomorrowDate();
        order.setDeliveryDate(tomorrow);

        return order;
    }

    @Override
    public void placeOrder(Order order) {
        order.setId(UUID.randomUUID().toString());
        orderDao.save(order);
    }

    private Date getTomorrowDate() {
        Calendar calendar = Calendar.getInstance();
        Date today = new Date(System.currentTimeMillis());
        calendar.setTime(today);
        calendar.add(Calendar.DATE, numberOfDaysForDelivery);
        calendar.set(Calendar.HOUR, deliveryHour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
