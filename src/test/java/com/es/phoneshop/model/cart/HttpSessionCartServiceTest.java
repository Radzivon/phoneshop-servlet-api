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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
    private AddToCartResult addToCartResult;
    @Mock
    private Cart cart;

    @InjectMocks
    private HttpSessionCartService httpSessionCartService;
    private Locale locale = Locale.US;


    @Before
    public void setup() {
        // when(productDao.getProduct(anyLong())).thenReturn(product);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(cart);
        when(httpSessionCartService.getCart(request)).thenReturn(cart);
        //  when(request.getLocale()).thenReturn(locale);
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

    @Ignore
    @Test
    public void add() {

    }

}
