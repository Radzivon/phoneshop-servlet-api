package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductHistoryPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private Product product;
    @InjectMocks
    private ProductPriceHistoryServlet servlet;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(product.getId()).thenReturn(1L);
    }

    @Test(expected = NumberFormatException.class)
    public void testDoGetIncorrectNumberFormat() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("null");
        servlet.doGet(request, response);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDoGetNoResult() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/999");
        servlet.doGet(request, response);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        ProductDao productDao = ArrayListProductDao.getInstance();
        productDao.save(product);
        when(request.getPathInfo()).thenReturn("/1");
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher("/WEB-INF/pages/productPriceHistory.jsp");
        verify(requestDispatcher).forward(request, response);
    }

}
