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
import java.math.BigDecimal;

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

        boolean hasError = false;
        String firstName = request.getParameter("firstName");
        if (firstName != null && !firstName.equals("")) {
            order.setFirstName(firstName);
        } else {
            hasError = true;
            request.setAttribute("errorFirstName", "Incorrect value");
        }

        String lastName = request.getParameter("lastName");
        if (lastName != null && !lastName.equals("")) {
            order.setLastName(lastName);
        } else {
            hasError = true;
            request.setAttribute("errorLastName", "Incorrect value");
        }


        String phoneNumber = request.getParameter("phoneNumber");
        if (phoneNumber != null && !phoneNumber.equals("") && ValidPhoneNumber.isPhoneNumber(phoneNumber)) {
            order.setPhoneNumber(phoneNumber);
        } else {
            hasError = true;
            request.setAttribute("errorPhoneNumber", "Incorrect value");
        }

        String stringDeliveryMode = request.getParameter("deliveryMode");
        DeliveryMode deliveryMode = DeliveryMode.getDeliveryMode(stringDeliveryMode);
        order.setDeliveryMode(deliveryMode);

        BigDecimal deliveryCost = deliveryMode.getDeliveryCost();
        order.setDeliveryCost(deliveryCost);

        order.calculateTotalCost();

        String deliveryAddress = request.getParameter("deliveryAddress");
        if (deliveryAddress != null && !deliveryAddress.equals("")) {
            order.setDeliveryAddress(deliveryAddress);
        } else {
            hasError = true;
            request.setAttribute("errorDeliveryAddress", "Incorrect value");
        }

        String stringPaymentMethod = request.getParameter("paymentMethod");
        PaymentMethod paymentMethod = PaymentMethod.getPaymentMethod(stringPaymentMethod);
        order.setPaymentMethod(paymentMethod);

        if (!hasError) {
            orderService.placeOrder(order);
            cartService.clearCart(cart);
            response.sendRedirect(request.getContextPath() + "/order/overview/" + order.getId());
        } else {
            request.setAttribute("hasError", hasError);
            request.setAttribute("order", order);
            request.getRequestDispatcher(JSP_PATH).forward(request, response);
        }
    }

}
