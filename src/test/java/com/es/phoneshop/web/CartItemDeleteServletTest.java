package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.CartService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartItemDeleteServletTest {
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
    @InjectMocks
    private CartItemDeleteServlet servlet;
    private final String requestPathInfo = "/1";

    @Before
    public void setup() {
        when(request.getContextPath()).thenReturn("contextPath");
        when(request.getPathInfo()).thenReturn(requestPathInfo);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.doPost(request, response);

        verify(cartService).delete(session, requestPathInfo);
        verify(response).sendRedirect("contextPath" + "/cart");

    }

}
