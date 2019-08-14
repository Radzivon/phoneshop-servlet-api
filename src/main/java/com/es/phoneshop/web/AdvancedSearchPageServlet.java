package com.es.phoneshop.web;

import com.es.phoneshop.model.product.AdvancedSearchResult;
import com.es.phoneshop.model.product.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class AdvancedSearchPageServlet extends HttpServlet {
    private ProductService productService;
    private static final String JSP_PATH = "/WEB-INF/pages/advancedSearchPage.jsp";
    private static final String ORDER = "order";

    @Override
    public void init() {
        productService = ProductService.getInstance();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", new ArrayList());
        request.getRequestDispatcher(JSP_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String description = request.getParameter("description");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String minStock = request.getParameter("minStock");
        String maxStock = request.getParameter("maxStock");

        AdvancedSearchResult advancedSearchResult = productService.advancedSearch(description, minPrice, maxPrice, minStock, maxStock);
        if (!advancedSearchResult.hasError()) {
            request.setAttribute("products", advancedSearchResult.getProducts());
            request.getRequestDispatcher(JSP_PATH).forward(request, response);
        } else {
            request.setAttribute("hasError", advancedSearchResult.hasError());
            for (Map.Entry<String, String> entry : advancedSearchResult.getMapErrors().entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
            request.setAttribute("products", advancedSearchResult.getProducts());
            request.getRequestDispatcher(JSP_PATH).forward(request, response);
        }
    }
}
