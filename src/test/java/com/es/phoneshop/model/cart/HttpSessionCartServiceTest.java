package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.recently.viewed.HttpSessionRecentlyViewedProducts;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private Product product;
    @Mock
    private ProductDao productDao;
    @Mock
    private CartServiceMethodsResult cartServiceMethodsResult;
    @Mock
    private Cart cart;
    @Mock
    private CartItem cartItem;


    @InjectMocks
    private HttpSessionCartService httpSessionCartService;
    private Locale locale = Locale.US;
    private Optional<CartItem> optionalCartItem;


    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(cart);
        when(httpSessionCartService.getCart(request)).thenReturn(cart);
        when(request.getLocale()).thenReturn(locale);
        when(productDao.getProduct(anyLong())).thenReturn(product);
    }

    @Test
    public void testParseProductId() {
        when(request.getPathInfo()).thenReturn("/1");
        Long expectedId = 1L;
        Long actualId = httpSessionCartService.parseProductId(request);
        Assert.assertEquals(expectedId, actualId);
    }

    @Test(expected = NumberFormatException.class)
    public void testParseProductIdIncorrectPath() {
        when(request.getPathInfo()).thenReturn("null");
        httpSessionCartService.parseProductId(request);
    }

    @Test
    public void getCart() {
        httpSessionCartService.getCart(request);

        verify(session).getAttribute("cart");
    }

    @Test
    public void getInstance() {
        HttpSessionRecentlyViewedProducts temp = HttpSessionRecentlyViewedProducts.getInstance();
        Assert.assertNotNull(temp);
    }

    @Test
    public void addWithOutOfStockException() {
        int stock = 0;
        when(product.getStock()).thenReturn(stock);
        when(request.getParameter("quantity")).thenReturn("1");
        when(request.getPathInfo()).thenReturn("/1");

        httpSessionCartService.add(request);
        verify(cartServiceMethodsResult).setErrorMessage("Out of stock. Max stock is " + stock);
    }

    @Test
    public void addWithNumberFormatException() {
        when(request.getParameter("quantity")).thenReturn("1");
        when(request.getPathInfo()).thenReturn("null");
        httpSessionCartService.add(request);
        verify(cartServiceMethodsResult).setErrorMessage("Not a number");
    }

    @Test
    public void addWithParseException() {
        when(request.getParameter("quantity")).thenReturn("null");
        httpSessionCartService.add(request);
        verify(cartServiceMethodsResult).setErrorMessage("Not a number");
    }
}
