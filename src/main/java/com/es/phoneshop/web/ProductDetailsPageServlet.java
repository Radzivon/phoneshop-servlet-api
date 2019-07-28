package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.recently.viewed.HttpSessionRecentlyViewedProducts;
import com.es.phoneshop.model.recently.viewed.RecentlyViewedProductsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.Locale;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private RecentlyViewedProductsService recentlyViewedProductsService;
    private static final String QUANTITY = "quantity";
    private static final String PRODUCT = "product";
    private static final String JSP_PATH = "/WEB-INF/pages/product.jsp";
    private static final String CART = "cart";
    private static final String RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE = "recentlyviewed";
    private static final String URL_MESSAGE = "?message=Added to cart successfully";
    private static final String NUMBER_FORMAT_EXCEPTION_MESSAGE = "Not a number";
    private static final String OUT_OF_STOCK_EXCEPTION_MESSAGE = "Out of stock. Max stock is ";

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        recentlyViewedProductsService = HttpSessionRecentlyViewedProducts.getInstance();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        LinkedList<Product> recentlyViewedProducts = recentlyViewedProductsService.getRecentlyViewedProducts(request);

        Product product = productDao.getProduct(parseProductId(request));
        recentlyViewedProductsService.add(recentlyViewedProducts, product);

        request.setAttribute(CART, cart);
        request.setAttribute(PRODUCT, product);
        request.setAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE, recentlyViewedProducts);
        request.getRequestDispatcher(JSP_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);

        try {
            Locale locale = request.getLocale();
            int quantity = Integer.valueOf(NumberFormat.getInstance(locale).parse(request.getParameter(QUANTITY)).intValue());
            Long productId = parseProductId(request);
            Product product = productDao.getProduct(productId);

            cartService.add(cart, product, quantity);

            response.sendRedirect(request.getRequestURI() +
                    URL_MESSAGE);
            return;
        } catch (NumberFormatException | ParseException exception) {
            request.setAttribute("error", NUMBER_FORMAT_EXCEPTION_MESSAGE);
        } catch (OutOfStockException e) {
            request.setAttribute("error", OUT_OF_STOCK_EXCEPTION_MESSAGE + e.getMaxStock());
        }

        doGet(request, response);
    }

    private Long parseProductId(HttpServletRequest request) {
        return Long.valueOf(request.getPathInfo().substring(1));
    }
}
