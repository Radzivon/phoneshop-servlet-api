package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDemodataServletContextListenerTest {
    @Mock
    private ServletContextEvent servletContextEvent;
    @Mock
    Product product;
    @InjectMocks
    private ProductDemodataServletContextListener productDemodataServletContextListener;
    @Mock
    private ProductDao productDao;

    @Ignore
    @Test
    public void testFillProductList() {
        when(servletContextEvent.getServletContext().getInitParameter("productDemodata")).thenReturn("true");
        productDemodataServletContextListener.contextInitialized(servletContextEvent);
        verify(productDao).save(product);
    }

    @Ignore
    @Test
    public void testFillProductListNoResult() {
        when(servletContextEvent.getServletContext().getInitParameter("productDemodata")).thenReturn("false");
        productDemodataServletContextListener.contextInitialized(servletContextEvent);
        verify(productDao, never()).save(product);
    }
}
