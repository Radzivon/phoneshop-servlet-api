package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceMethodsResult;
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
public class CartPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private CartService cartService;
    @Mock
    private CartServiceMethodsResult cartServiceMethodsResult;
    @Mock
    private Cart cart;
    @InjectMocks
    private CartPageServlet servlet;
    private String[] errors;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(cartServiceMethodsResult.getCart()).thenReturn(cart);
        errors = new String[1];
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(cartService.getCart(request)).thenReturn(cart);

        servlet.doGet(request, response);

        verify(request).setAttribute("cart", cart);
        verify(request).getRequestDispatcher("/WEB-INF/pages/cart.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostWithErrors() throws ServletException, IOException {
        when(cartService.update(request)).thenReturn(true);
        when(cartServiceMethodsResult.getErrors()).thenReturn(errors);

        servlet.doPost(request, response);

        verify(request).setAttribute("errors", cartServiceMethodsResult.getErrors());
    }

    @Test
    public void testDoPostWithoutErrors() throws ServletException, IOException {
        when(cartService.update(request)).thenReturn(false);
        when(request.getRequestURI()).thenReturn("contextPath");

        servlet.doPost(request, response);

        verify(request).setAttribute("cart", cart);
        verify(response).sendRedirect("contextPath" + "?message=Updated successfully");
    }


}
