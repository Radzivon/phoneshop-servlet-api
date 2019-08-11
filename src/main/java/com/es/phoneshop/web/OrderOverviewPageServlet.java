package com.es.phoneshop.web;

import com.es.phoneshop.model.order.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
    private OrderDao orderDao;
    private static final String JSP_PATH = "/WEB-INF/pages/orderOverview.jsp";
    private static final String ORDER = "order";

    @Override
    public void init() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Order order = orderDao.getById(parseOrderId(request));
        request.setAttribute(ORDER, order);

        request.getRequestDispatcher(JSP_PATH).forward(request, response);
    }

    private String parseOrderId(HttpServletRequest request) {
        return request.getPathInfo().substring(1);
    }
}
