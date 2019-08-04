package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductService;

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
    private ProductService productService;
    private CartServiceMethodsResult cartServiceMethodsResult;
    private static final String QUANTITY = "quantity";

    private HttpSessionCartService() {
        productDao = ArrayListProductDao.getInstance();
        productService = ProductService.getInstance();
        cartServiceMethodsResult = new CartServiceMethodsResult();
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
        boolean hasError = false;
        Cart cart = getCart(request);
        try {
            int quantity = parseQuantity(request);

            Long productId = parseProductId(request);
            Product product = productDao.getProduct(productId);
            if (quantity > product.getStock()) {
                throw new OutOfStockException(product.getStock());
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

            recalculateCart(cart);

            cartServiceMethodsResult.setCart(cart);
            cartServiceMethodsResult.setProduct(product);

        } catch (NumberFormatException | ParseException exception) {
            cartServiceMethodsResult.setErrorMessage("Not a number");
            hasError = true;
        } catch (OutOfStockException exception) {
            cartServiceMethodsResult.setErrorMessage("Out of stock. Max stock is " + exception.getMaxStock());
            hasError = true;
        }
        return hasError;
    }

     void recalculateCart(Cart cart) {
        BigDecimal totalCost = new BigDecimal(0);
        int totalQuantity = 0;
        for (CartItem cartItem : cart.getCartItems()) {
            totalCost = totalCost.add(cartItem.getProduct().getPrice()
                    .multiply(new BigDecimal(cartItem.getQuantity())));
            totalQuantity += cartItem.getQuantity();
        }
        cart.setTotalCost(totalCost);
        cart.setTotalQuantity(totalQuantity);
    }

    @Override
    public boolean update(HttpServletRequest request) {
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");

        boolean hasError = false;
        Cart cart = getCart(request);
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
            try {
                update(cart, productId, quantity);
            } catch (OutOfStockException exception) {
                errors[i] = "Out of stock. Max stock is " + exception.getMaxStock();
                hasError = true;
            }
        }
        cartServiceMethodsResult.setCart(cart);
        cartServiceMethodsResult.setErrors(errors);
        return hasError;
    }

    private boolean update(Cart cart, Long productId, int quantity) throws OutOfStockException {
        Product product = productService.getProductById(productId);
        if (quantity > product.getStock()) {
            throw new OutOfStockException(product.getStock());
        }
        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().equals(product)).findFirst();
        CartItem cartItem = cartItemOptional.get();
        if (cartItemOptional.isPresent()) {
            cartItem.setQuantity(quantity);
        }
        recalculateCart(cart);
        return false;
    }

    @Override
    public void delete(HttpServletRequest request) {
        Cart cart = getCart(request);
        Long productId = parseProductId(request);

        cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));
        recalculateCart(cart);
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
