package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

public class HttpSessionCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = "cart";
    private static HttpSessionCartService instance = new HttpSessionCartService();
    private ProductDao productDao;
    private AddToCartResult addToCartResult;
    private static final String QUANTITY = "quantity";

    private HttpSessionCartService() {
        productDao = ArrayListProductDao.getInstance();
        addToCartResult = new AddToCartResult();
    }

    public static HttpSessionCartService getInstance() {
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
    public boolean add(HttpServletRequest request) {
        boolean isError = false;
        Cart cart = getCart(request);
        try {
            int quantity = parseQuantity(request);

            Long productId = parseProductId(request);
            Product product = productDao.getProduct(productId);
            if (quantity > product.getStock()) {
                throw new OutOfStockException(product.getStock());
            }
            Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getProductId().equals(product.getId())).findFirst();
            if (optionalCartItem.isPresent()) {
                CartItem cartItem = optionalCartItem.get();
                int totalQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(totalQuantity);
            } else {
                cart.getCartItems().add(new CartItem(product.getId(), quantity));
            }

            recalculateCart(cart);

            addToCartResult.setCart(cart);
            addToCartResult.setProduct(product);

        } catch (NumberFormatException | ParseException exception) {
            addToCartResult.setErrorMessage("Not a number");
            isError = true;
        } catch (OutOfStockException exception) {
            addToCartResult.setErrorMessage("Out of stock. Max stock is " + exception.getMaxStock());
            isError = true;
        }
        return isError;
    }

    private void recalculateCart(Cart cart) {
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

    Long parseProductId(HttpServletRequest request) {
        return Long.valueOf(request.getPathInfo()
                .substring(1));
    }

    int parseQuantity(HttpServletRequest request) throws ParseException {
        Locale locale = request.getLocale();
        return NumberFormat.getInstance(locale).parse(request.getParameter(QUANTITY)).intValue();
    }
}
