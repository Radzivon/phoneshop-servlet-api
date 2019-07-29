package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.recently.viewed.RecentlyViewedProductsService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;

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
    private CartService cartService;
    @Mock
    private Cart cart;
    @Mock
    private RecentlyViewedProductsService recentlyViewedProductsService;
    @Mock
    private LinkedList<Product> recentlyViewedProducts;
    @Mock
    private Product product;
    @Mock
    private ProductDao productDao;
    @InjectMocks
    private ProductDetailsPageServlet servlet;
    private Locale locale = Locale.US;

    @Before
    public void setup() throws ServletException {
        when(request.getLocale()).thenReturn(locale);
        when(cartService.getCart(request)).thenReturn(cart);
        when(recentlyViewedProductsService.getRecentlyViewedProducts(request)).thenReturn(recentlyViewedProducts);
        when(productDao.getProduct(1L)).thenReturn(product);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn("/1");
    }

    @Test
    public void testParseId() {
        Long expectedId = 1L;
        Long actualId = servlet.parseProductId(request);
        Assert.assertEquals(expectedId, actualId);
    }

    @Test(expected = NumberFormatException.class)
    public void testParseIdIncorrectPath() {
        when(request.getPathInfo()).thenReturn("null");
        servlet.parseProductId(request);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(productDao).getProduct(1L);
        verify(request).setAttribute("cart", cart);
        verify(request).setAttribute("product", product);
        verify(request).setAttribute("recentlyviewed", recentlyViewedProducts);
        verify(request).getRequestDispatcher("/WEB-INF/pages/product.jsp");
        verify(requestDispatcher).forward(request, response);
    }


    @Test
    public void testDoPost() throws OutOfStockException, IOException, ServletException {
        when(request.getParameter("quantity")).thenReturn("1");
        when(request.getRequestURI()).thenReturn("RequestURI");

        servlet.doPost(request, response);

        verify(cartService).add(cart, product, 1);
        verify(response).sendRedirect("RequestURI?message=Added to cart successfully");

    }

    @Test
    public void testDoPostWithParseException() throws IOException, ServletException {
        when(request.getLocale()).thenReturn(locale);
        when(request.getParameter("quantity")).thenReturn("null");

        servlet.doPost(request, response);
        verify(request).setAttribute("error", "Not a number");
    }
}
