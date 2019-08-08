package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.AddCartResult;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.UpdateCartResult;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.recently.viewed.AddToRecentlyViewedProductsResult;
import com.es.phoneshop.model.recently.viewed.RecentlyViewedProductsService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;

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
    private HttpSession session;
    @Mock
    private CartService cartService;
    @Mock
    private AddCartResult addCartResult;
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
    @InjectMocks
    private ProductDetailsPageServlet servlet;

    private String requestPathInfo = "/1";
    private String productId = "1";
    private String quantity = "1";
    private boolean hasError;

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(cartService.getCart(session)).thenReturn(cart);
        when(request.getPathInfo()).thenReturn(requestPathInfo);
        when(recentlyViewedProductsService.add(session, productId)).thenReturn(addToRecentlyViewedProductsResult);
        when(addToRecentlyViewedProductsResult.getProduct()).thenReturn(product);
        when(addToRecentlyViewedProductsResult.getProducts()).thenReturn(recentlyViewedProducts);

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameter("quantity")).thenReturn(quantity);
        when(cartService.add(session, productId, quantity)).thenReturn(addCartResult);
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
        hasError = false;
        when(addCartResult.hasError()).thenReturn(hasError);
        when(addCartResult.getCart()).thenReturn(cart);
        when(addCartResult.getProduct()).thenReturn(product);
        when(request.getParameter("quantity")).thenReturn("1");
        when(request.getRequestURI()).thenReturn("RequestURI");

        servlet.doPost(request, response);

        verify(request).setAttribute("cart", cart);
        verify(request).setAttribute("product", product);
        verify(response).sendRedirect("RequestURI?message=Added to cart successfully");

    }

    @Test
    public void testDoPostWithParseException() throws IOException, ServletException {
        hasError = true;
        when(addCartResult.getErrorMessage()).thenReturn("Not a number");
        when(addCartResult.hasError()).thenReturn(hasError);

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Not a number");
    }


    @Test
    public void testDoPostWithNumberFormatException() throws IOException, ServletException {
        hasError = true;
        when(addCartResult.hasError()).thenReturn(hasError);
        when(addCartResult.getErrorMessage()).thenReturn("Not a number");

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Not a number");
    }

    @Test
    public void testDoPostWithOutOfStockException() throws IOException, ServletException {
        hasError = true;
        when(addCartResult.hasError()).thenReturn(hasError);
        when(addCartResult.getErrorMessage()).thenReturn("Out of stock. Max stock is 100");
        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Out of stock. Max stock is 100");
    }
}
