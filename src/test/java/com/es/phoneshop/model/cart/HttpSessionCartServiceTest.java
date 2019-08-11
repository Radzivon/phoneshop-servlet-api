package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductService;
import com.es.phoneshop.model.recently.viewed.HttpSessionRecentlyViewedProducts;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @Mock
    private HttpSession session;
    @Mock
    private Product product;
    @Mock
    private ProductDao productDao;
    @Mock
    private Cart cart;
    @Mock
    private ProductService productService;
    @Mock
    private CartItem cartItem;
    @Spy
    private List<CartItem> list = new ArrayList<>();
    @InjectMocks
    private HttpSessionCartService httpSessionCartService;

    private String parametrQuantity;
    private String stringProductId;
    private int stock;
    private String[] productIds;
    private String[] quantities;
    private int quantity = 0;


    @Before
    public void setup() {
        when(session.getAttribute(anyString())).thenReturn(cart);
        when(productDao.getProduct(anyLong())).thenReturn(product);
        when(productService.getProductById(anyLong())).thenReturn(product);
        when(cartItem.getProduct()).thenReturn(product);
        when(cartItem.getQuantity()).thenReturn(quantity);
        list.add(cartItem);
        when(cart.getCartItems()).thenReturn(list);
        stock = 1;
        productIds = new String[1];
        quantities = new String[1];
    }
    @After
    public void clean(){
        list.clear();
    }

    @Test
    public void testParseProductId() {
        stringProductId = "1";
        Long expectedId = 1L;
        Long actualId = httpSessionCartService.parseProductId(stringProductId);
        Assert.assertEquals(expectedId, actualId);
    }

    @Test(expected = NumberFormatException.class)
    public void testParseProductIdIncorrectPath() {
        stringProductId = "null";

        httpSessionCartService.parseProductId(stringProductId);
    }

    @Test
    public void getCart() {
        httpSessionCartService.getCart(session);

        verify(session).getAttribute("cart");
    }

    @Test
    public void getInstance() {
        HttpSessionCartService temp = HttpSessionCartService.getInstance();
        Assert.assertNotNull(temp);
    }


    @Test
    public void addWithOutOfStockException() {
        stock = 0;
        parametrQuantity = "1";
        stringProductId = "1";
        when(product.getStock()).thenReturn(stock);

        httpSessionCartService.add(session, stringProductId, parametrQuantity);

        verify(cart, never()).recalculateCart();
    }


    @Test
    public void addWithNumberFormatException() {
        parametrQuantity = "1";
        stringProductId = "null";

        httpSessionCartService.add(session, stringProductId, parametrQuantity);

        verify(cart, never()).recalculateCart();
    }


    @Test
    public void addWithParseException() {
        parametrQuantity = "";
        stringProductId = "1";

        httpSessionCartService.add(session, stringProductId, parametrQuantity);

        verify(cart, never()).recalculateCart();
    }

    @Test
    public void add() {
        parametrQuantity = "1";
        stringProductId = "1";
        when(product.getStock()).thenReturn(stock);

        httpSessionCartService.add(session, stringProductId, parametrQuantity);

        verify(cart).recalculateCart();
    }

    @Test
    public void updateWithOutOfStockException() {
        stock = 0;
        productIds[0] = "1";
        quantities[0] = "1";
        when(product.getStock()).thenReturn(stock);

        httpSessionCartService.update(session, productIds, quantities);

        verify(cart, never()).recalculateCart();
    }

    @Test
    public void updateWithNumberFormatException() {
        stock = 0;
        productIds[0] = "1";
        quantities[0] = "";

        httpSessionCartService.update(session, productIds, quantities);

        verify(cart, never()).recalculateCart();
    }

    @Test
    public void update() {
        stock = 1;
        productIds[0] = "1";
        quantities[0] = "1";
        when(product.getStock()).thenReturn(stock);

        httpSessionCartService.update(session, productIds, quantities);

        verify(cart, times(2)).getCartItems();
        verify(cart).recalculateCart();
    }

    @Test
    public void delete() {
        stringProductId = "1";

        httpSessionCartService.delete(session, stringProductId);

        verify(cart, times(2)).getCartItems();
        verify(cart).recalculateCart();
    }
}
