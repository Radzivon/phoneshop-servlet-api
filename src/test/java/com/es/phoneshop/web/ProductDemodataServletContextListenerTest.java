package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDemodataServletContextListenerTest {
    @Mock
    private ServletContextEvent servletContextEvent;
    @Mock
    ServletContext servletContext;
    @Mock
    private Product product;
    @Mock
    private ProductDao productDao;
    @Mock
    ArrayListProductDao arrayListProductDao;
    @Mock
    List<Product> list;
    @Spy
    private ProductDemodataServletContextListener productDemodataServletContextListener;

    @Before
    public void setup() {
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
    }

    @Ignore
    @Test
    public void testFillProductList() {
        when(servletContextEvent.getServletContext().getInitParameter("productDemodata")).thenReturn("true");
        doReturn(Collections.singletonList(product)).when(productDemodataServletContextListener).fillProductList();

        productDemodataServletContextListener.contextInitialized(servletContextEvent);

        verify(productDao).save(product);
    }

    @Test
    public void testFillProductListNoResult() {
        when(servletContextEvent.getServletContext().getInitParameter("productDemodata")).thenReturn("false");
        productDemodataServletContextListener.contextInitialized(servletContextEvent);

        verify(productDao, never()).save(product);
    }
}