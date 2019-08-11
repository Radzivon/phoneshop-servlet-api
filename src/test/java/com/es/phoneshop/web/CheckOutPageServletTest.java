package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;
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
public class CheckOutPageServletTest {
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
    private Cart cart;
    @Mock
    private OrderService orderService;
    @Mock
    private Order order;
    @InjectMocks
    private CheckOutPageServlet servlet;
    private static final String JSP_PATH = "/WEB-INF/pages/checkout.jsp";
    private static final String ORDER = "order";
    private static final String CONTEXT_PATH = "contextPath";
    private String id = "id";

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(cartService.getCart(session)).thenReturn(cart);
        when(orderService.getOrder(cart)).thenReturn(order);
        when(cartService.getCart(session)).thenReturn(cart);
        when(order.getId()).thenReturn(id);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute(ORDER, order);
        verify(request).getRequestDispatcher(JSP_PATH);
        verify(requestDispatcher).forward(request, response);

    }

    @Test
    public void testDoPostWithErrors() throws ServletException, IOException {
        when(request.getParameter("firstName")).thenReturn(null);
        when(request.getParameter("lastName")).thenReturn(null);
        when(request.getParameter("phoneNumber")).thenReturn(null);
        when(request.getParameter("deliveryMode")).thenReturn("courier");
        when(request.getParameter("deliveryAddress")).thenReturn(null);
        when(request.getParameter("paymentMethod")).thenReturn("cash");

        servlet.doPost(request, response);

        verify(request).setAttribute(ORDER, order);
        verify(request).getRequestDispatcher(JSP_PATH);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getParameter("firstName")).thenReturn("Not null");
        when(request.getParameter("lastName")).thenReturn("Not null");
        when(request.getParameter("phoneNumber")).thenReturn("+375111111");
        when(request.getParameter("deliveryMode")).thenReturn("courier");
        when(request.getParameter("deliveryAddress")).thenReturn("Not null");
        when(request.getParameter("paymentMethod")).thenReturn("cash");
        when(request.getContextPath()).thenReturn(CONTEXT_PATH);

        servlet.doPost(request, response);

        verify(orderService).placeOrder(order);
        verify(cartService).clearCart(cart);
        verify(response).sendRedirect(CONTEXT_PATH + "/order/overview/" + id);
    }

}
