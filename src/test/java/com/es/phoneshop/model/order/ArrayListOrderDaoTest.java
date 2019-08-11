package com.es.phoneshop.model.order;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListOrderDaoTest {
    @Mock
    private Order order;
    @Spy
    private List<Order> orderList = new ArrayList<>();
    @InjectMocks
    private ArrayListOrderDao arrayListOrderDao;
    private String id = "id";

    @After
    public void clean() {
        orderList.clear();
    }

    @Test
    public void getInstance() {
        ArrayListOrderDao temp = ArrayListOrderDao.getInstance();
        Assert.assertNotNull(temp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveThrowIllegalArgumentException() {
        Order temp = null;
        arrayListOrderDao.save(temp);
    }

    @Test
    public void save() {
        arrayListOrderDao.save(order);

        verify(orderList).add(order);
    }

    @Test
    public void getById() {
        String id = "id";
        orderList.add(order);
        when(order.getId()).thenReturn(id);

        arrayListOrderDao.getById(id);

        verify(order).getId();
    }

    @Test(expected = NoSuchElementException.class)
    public void getByIdWithNoSuchElementException() {
        orderList.add(order);
        when(order.getId()).thenReturn(id);

        arrayListOrderDao.getById("incorrect id");
    }
}
