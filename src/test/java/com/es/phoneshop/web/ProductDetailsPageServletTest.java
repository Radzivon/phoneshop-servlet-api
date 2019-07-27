package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    Product product;
    @Mock
    private ProductDao productDao;
    @Mock
    private CartService cartService;
    @Mock
    Cart cart;
    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() {
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(product.getId()).thenReturn(1L);
    }
    @Ignore
    @Test(expected = NumberFormatException.class)
    public void testDoGetBadPath() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("null");
        servlet.doGet(request, response);
    }
    @Ignore
    @Test(expected = ProductNotFoundException.class)
    public void testDoGetNoResult() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/999");
        servlet.doGet(request, response);
    }
    @Ignore
    @Test
    public void testDoGet() throws ServletException, IOException {
        ProductDao productDao = ArrayListProductDao.getInstance();
        productDao.save(product);
        when(request.getPathInfo()).thenReturn("/1");
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher("/WEB-INF/pages/product.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}
