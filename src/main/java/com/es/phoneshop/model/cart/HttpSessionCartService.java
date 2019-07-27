package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class HttpSessionCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = "cart";
    private static HttpSessionCartService instance = new HttpSessionCartService();
    private Cart cart;

    private HttpSessionCartService() {
        cart = new Cart();
    }

    public static synchronized HttpSessionCartService getInstance() {
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart);
        }
        return cart;
    }

    @Override
    public void add(Cart cart, Product product, int quantity) throws OutOfStockException {
        if (quantity > product.getStock()) {
            throw new OutOfStockException(product.getStock());
        }
        int actualQuantity = (product.getStock() - quantity);
        product.setStock(actualQuantity);
        cart.getCartItems().add(new CartItem(product.getId(), quantity));
        recalculateCart(cart);
    }

    private void recalculateCart(Cart cart) {
        cart.getTotalCost();
        cart.getTotalQuantity();
        BigDecimal totalCost = new BigDecimal(0);
        Integer totalQuantity = new Integer(0);
        for (CartItem cartItem : cart.getCartItems()) {
            totalCost = totalCost.add(ArrayListProductDao.getInstance().getProduct(cartItem.getProductId()).getPrice()
                    .multiply(new BigDecimal(cartItem.getQuantity())));
            totalQuantity += cartItem.getQuantity();
        }
        cart.setTotalCost(totalCost);
        cart.setTotalQuantity(totalQuantity);
    }

}
