package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductService;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
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
    public AddCartResult add(HttpSession session, String stringProductId, String stringQuantity) {
        boolean hasError = false;
        Cart cart = getCart(session);
        AddCartResult addCartResult = new AddCartResult();
        Long productId;
        int quantity;

        try {
            productId = parseProductId(stringProductId);
            quantity = parseQuantity(stringQuantity);
        } catch (NumberFormatException | ParseException exception) {
            hasError = true;
            addCartResult.setErrorMessage("Not a number");
            addCartResult.setHasError(hasError);
            return addCartResult;
        }
        Product product = productDao.getProduct(productId);

        if (quantity > product.getStock()) {
            hasError = true;
            addCartResult.setErrorMessage("Out of stock. Max stock is " + product.getStock());
            addCartResult.setHasError(hasError);
            return addCartResult;
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

        addCartResult.setCart(cart);
        addCartResult.setProduct(product);
        addCartResult.setHasError(hasError);
        return addCartResult;
    }

    @Override
    public UpdateCartResult update(HttpSession session, String[] productIds, String[] quantities) {
        UpdateCartResult updateCartResult = new UpdateCartResult();

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
        updateCartResult.setCart(cart);
        updateCartResult.setHasError(hasError);
        updateCartResult.setErrors(errors);
        return updateCartResult;
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
    public void delete(HttpSession session, String stringProductId) {
        Cart cart = getCart(session);
        Long productId = parseProductId(stringProductId);

        cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));
        cart.recalculateCart();
    }

    Long parseProductId(String stringProductId) {
        return Long.valueOf(stringProductId);
    }

    int parseQuantity(String quantity) throws ParseException {
        return Integer.valueOf(quantity);
    }
}
