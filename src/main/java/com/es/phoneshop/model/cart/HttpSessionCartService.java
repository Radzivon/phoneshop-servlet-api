package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductService;

import javax.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

public class HttpSessionCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = "cart";
    private static HttpSessionCartService instance = new HttpSessionCartService();
    private ProductDao productDao;
    private ProductService productService;


    private HttpSessionCartService() {
        productDao = ArrayListProductDao.getInstance();
        productService = ProductService.getInstance();
    }

    public static HttpSessionCartService getInstance() {
        return instance;
    }

    @Override
    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_SESSION_ATTRIBUTE, cart);
        }
        return cart;
    }

    @Override
    public CartServiceMethodsResult add(HttpSession session, String requestPathInfo, String stringQuantity, Locale locale) {
        boolean hasError = false;
        Cart cart = getCart(session);
        CartServiceMethodsResult cartServiceMethodsResult = new CartServiceMethodsResult();
        Long productId;
        int quantity;

        try {
            productId = parseProductId(requestPathInfo);
            quantity = parseQuantity(stringQuantity, locale);
        } catch (NumberFormatException | ParseException exception) {
            hasError = true;
            cartServiceMethodsResult.setErrorMessage("Not a number");
            cartServiceMethodsResult.setHasError(hasError);
            return cartServiceMethodsResult;
        }
        Product product = productDao.getProduct(productId);

        if (quantity > product.getStock()) {
            hasError = true;
            cartServiceMethodsResult.setErrorMessage("Out of stock. Max stock is " + product.getStock());
            cartServiceMethodsResult.setHasError(hasError);
            return cartServiceMethodsResult;
        }

        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().equals(product)).findFirst();
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            int totalQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(totalQuantity);
        } else {
            cart.getCartItems().add(new CartItem(product, quantity));
        }

        cart.recalculateCart();

        cartServiceMethodsResult.setCart(cart);
        cartServiceMethodsResult.setProduct(product);
        cartServiceMethodsResult.setHasError(hasError);
        return cartServiceMethodsResult;
    }

    @Override
    public CartServiceMethodsResult update(HttpSession session, String[] productIds, String[] quantities) {
        CartServiceMethodsResult cartServiceMethodsResult = new CartServiceMethodsResult();

        boolean hasError = false;
        Cart cart = getCart(session);
        String[] errors = new String[productIds.length];
        for (int i = 0; i < productIds.length; i++) {
            Long productId = Long.valueOf(productIds[i]);
            int quantity;
            try {
                quantity = Integer.valueOf(quantities[i]);
            } catch (NumberFormatException exception) {
                errors[i] = "Not a number";
                hasError = true;
                continue;
            }

            boolean checkError = update(cart, productId, quantity);
            if (checkError) {
                errors[i] = "Out of stock. Max stock is " + productService.getProductById(productId).getStock();
                hasError = true;
            }
        }
        cartServiceMethodsResult.setCart(cart);
        cartServiceMethodsResult.setHasError(hasError);
        cartServiceMethodsResult.setErrors(errors);
        return cartServiceMethodsResult;
    }

    private boolean update(Cart cart, Long productId, int quantity) {
        boolean hasError = false;
        Product product = productService.getProductById(productId);
        if (quantity > product.getStock()) {
            hasError = true;
            return hasError;
        }
        cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().equals(product)).findFirst()
                .ifPresent(cartItem -> cartItem.setQuantity(quantity));

        cart.recalculateCart();
        return hasError;
    }

    @Override
    public void delete(HttpSession session, String requestPathInfo) {
        Cart cart = getCart(session);
        Long productId = parseProductId(requestPathInfo);

        cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));
        cart.recalculateCart();
    }

    Long parseProductId(String requestPathInfo) {
        return Long.valueOf(requestPathInfo.substring(1));
    }

    int parseQuantity(String quantity, Locale locale) throws ParseException {
        return NumberFormat.getInstance(locale).parse(quantity).intValue();
    }
}
