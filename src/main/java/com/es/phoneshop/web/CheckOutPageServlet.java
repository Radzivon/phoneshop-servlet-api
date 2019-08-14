package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.order.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class CheckOutPageServlet extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;
    private static final String JSP_PATH = "/WEB-INF/pages/checkout.jsp";
    private static final String ORDER = "order";

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = cartService.getCart(session);
        Order order = orderService.getOrder(cart);

        request.setAttribute(ORDER, order);

        request.getRequestDispatcher(JSP_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = cartService.getCart(session);
        Order order = orderService.getOrder(cart);

        OrderValidator orderValidator = new OrderValidator();
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String stringDeliveryMode = request.getParameter("deliveryMode");
        String deliveryAddress = request.getParameter("deliveryAddress");
        String stringPaymentMethod = request.getParameter("paymentMethod");

        ValidOrderResult validOrderResult = orderValidator.validOrder(order, firstName, lastName, phoneNumber, stringDeliveryMode, deliveryAddress, stringPaymentMethod);

        if (!validOrderResult.hasError()) {
            orderService.placeOrder(order);
            cartService.clearCart(cart);
            response.sendRedirect(request.getContextPath() + "/order/overview/" + order.getId());
        } else {
            request.setAttribute("hasError", validOrderResult.hasError());
            for (Map.Entry<String, String> entry : validOrderResult.getMapErrors().entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
            request.setAttribute(ORDER, order);
            request.getRequestDispatcher(JSP_PATH).forward(request, response);
        }
    }

}
