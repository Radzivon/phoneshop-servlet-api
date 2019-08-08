package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.UpdateCartResult;
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
    private HttpSession session;
    @Mock
    private CartService cartService;
    @Mock
    private UpdateCartResult updateCartResult;
    @Mock
    private Cart cart;
    @InjectMocks
    private CartPageServlet servlet;

    private String[] errors;
    private String[] productsIds;
    private String[] quantities;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(updateCartResult.getCart()).thenReturn(cart);
        errors = new String[1];
        productsIds = new String[1];
        quantities = new String[1];
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(cartService.getCart(session)).thenReturn(cart);

        servlet.doGet(request, response);

        verify(request).setAttribute("cart", cart);
        verify(request).getRequestDispatcher("/WEB-INF/pages/cart.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostWithErrors() throws ServletException, IOException {
        when(request.getParameterValues("productId")).thenReturn(productsIds);
        when(request.getParameterValues("quantity")).thenReturn(quantities);
        when(cartService.update(session, productsIds, quantities)).thenReturn(updateCartResult);
        when(updateCartResult.hasError()).thenReturn(true);
        when(updateCartResult.getErrors()).thenReturn(errors);

        servlet.doPost(request, response);

        verify(request).setAttribute("errors", updateCartResult.getErrors());
    }


    @Test
    public void testDoPostWithoutErrors() throws ServletException, IOException {
        when(request.getParameterValues("productId")).thenReturn(productsIds);
        when(request.getParameterValues("quantity")).thenReturn(quantities);
        when(cartService.update(session, productsIds, quantities)).thenReturn(updateCartResult);
        when(updateCartResult.hasError()).thenReturn(false);
        when(request.getRequestURI()).thenReturn("contextPath");

        servlet.doPost(request, response);

        verify(request).setAttribute("cart", cart);
        verify(response).sendRedirect("contextPath" + "?message=Updated successfully");
    }


}
