package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.recently.viewed.HttpSessionRecentlyViewedProducts;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
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
    private String parametrQuantity;
    private String requestPathInfo;


    @Before
    public void setup() {
        when(session.getAttribute(anyString())).thenReturn(cart);
    }

    @Test
    public void testParseProductId() {
        requestPathInfo = "/1";
        Long expectedId = 1L;
        Long actualId = httpSessionCartService.parseProductId(requestPathInfo);
        Assert.assertEquals(expectedId, actualId);
    }

    @Test(expected = NumberFormatException.class)
    public void testParseProductIdIncorrectPath() {
        requestPathInfo = "null";

        httpSessionCartService.parseProductId(requestPathInfo);
    }

    @Test
    public void getCart() {
        httpSessionCartService.getCart(session);

        verify(session).getAttribute("cart");
    }

    @Test
    public void getInstance() {
        HttpSessionRecentlyViewedProducts temp = HttpSessionRecentlyViewedProducts.getInstance();
        Assert.assertNotNull(temp);
    }

    @Ignore
    @Test
    public void addWithOutOfStockException() {
        int stock = 0;
        parametrQuantity = "1";
        requestPathInfo = "/1";
        when(product.getStock()).thenReturn(stock);

        httpSessionCartService.add(session, requestPathInfo, parametrQuantity, locale);
        verify(cartServiceMethodsResult).setErrorMessage("Out of stock. Max stock is " + stock);
    }

    @Ignore
    @Test
    public void addWithNumberFormatException() {
        parametrQuantity = "1";
        requestPathInfo = "null";
        httpSessionCartService.add(session, requestPathInfo, parametrQuantity, locale);
        verify(cartServiceMethodsResult).setErrorMessage("Not a number");
    }

    @Ignore
    @Test
    public void addWithParseException() {
        parametrQuantity = "";
        requestPathInfo = "null";
        httpSessionCartService.add(session, requestPathInfo, parametrQuantity, locale);
        verify(cartServiceMethodsResult).setErrorMessage("Not a number");
    }
}
