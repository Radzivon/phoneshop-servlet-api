package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.*;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.recently.viewed.AddToRecentlyViewedProductsResult;
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
import java.text.ParseException;
import java.util.LinkedList;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.anyLong;
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
    private AddToCartResult addToCartResult;
    @Mock
    private AddToRecentlyViewedProductsResult addToRecentlyViewedProductsResult;
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
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(addToCartResult.getProduct()).thenReturn(product);
        when(addToCartResult.getCart()).thenReturn(cart);
        when(addToRecentlyViewedProductsResult.getProducts()).thenReturn(recentlyViewedProducts);
    }


    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);


        verify(request).setAttribute("recentlyviewed", recentlyViewedProducts);
        verify(request).getRequestDispatcher("/WEB-INF/pages/product.jsp");
        verify(requestDispatcher).forward(request, response);
    }


    @Test
    public void testDoPost() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("RequestURI");

        servlet.doPost(request, response);

        verify(request).setAttribute("cart", cart);
        verify(request).setAttribute("product", product);
        verify(cartService).add(request);
        verify(response).sendRedirect("RequestURI?message=Added to cart successfully");

    }

    @Test
    public void testDoPostWithParseException() throws IOException, ServletException {
        boolean isError = true;
        when(cartService.add(request)).thenReturn(isError);
        when(addToCartResult.getErrorMessage()).thenReturn("Not a number");
        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Not a number");
    }

    @Test
    public void testDoPostWithNumberFormatException() throws IOException, ServletException {
        boolean isError = true;
        when(cartService.add(request)).thenReturn(isError);
        when(addToCartResult.getErrorMessage()).thenReturn("Not a number");
        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Not a number");
    }

    @Test
    public void testDoPostWithOutOfStockException() throws IOException, ServletException {
        boolean isError = true;
        when(cartService.add(request)).thenReturn(isError);
        when(addToCartResult.getErrorMessage()).thenReturn("Out of stock. Max stock is 100");
        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Out of stock. Max stock is 100");
    }
}
