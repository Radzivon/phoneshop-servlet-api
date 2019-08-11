package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
    @Mock
    private OrderDao orderDao;
    @Mock
    private Cart cart;
    @Mock
    private Order order;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void getInstance() {
        OrderServiceImpl orderServiceImpl = OrderServiceImpl.getInstance();
        Assert.assertNotNull(orderServiceImpl);
    }

    @Test
    public void getOrder() {
        orderService.getOrder(cart);

        verify(cart).getCartItems();
        verify(cart).getSubTotalCost();
        verify(cart).getTotalQuantity();

    }

    @Test
    public void placeOrder() {
        orderService.placeOrder(order);

        verify(order).setId(anyString());
        verify(orderDao).save(order);
    }
}
