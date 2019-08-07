package com.es.phoneshop.model.recently.viewed;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
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
import java.util.LinkedList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionRecentlyViewedProductsTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private Product product;
    @Mock
    private ProductDao productDao;
    @Mock
    private AddToRecentlyViewedProductsResult addToRecentlyViewedProductsResult;
    @Mock
    private LinkedList<Product> recentlyViewedProducts;
    @InjectMocks
    HttpSessionRecentlyViewedProducts httpSessionRecentlyViewedProducts;


    @Before
    public void setup() {
        when(productDao.getProduct(anyLong())).thenReturn(product);
        when(session.getAttribute(anyString())).thenReturn(recentlyViewedProducts);
        when(httpSessionRecentlyViewedProducts.getRecentlyViewedProducts(session)).thenReturn(recentlyViewedProducts);
    }

    @Test
    public void testParseId() {
        String requestPathInfo = "/1";
        Long expectedId = 1L;
        Long actualId = httpSessionRecentlyViewedProducts.parseProductId(requestPathInfo);
        Assert.assertEquals(expectedId, actualId);
    }


    @Test(expected = NumberFormatException.class)
    public void testParseIdIncorrectPath() {
        String requestPathInfo = "null";
        httpSessionRecentlyViewedProducts.parseProductId(requestPathInfo);
    }
    @Test
    public void getRecentlyViewedProductsListNull() {
        httpSessionRecentlyViewedProducts.getRecentlyViewedProducts(session);

        verify(session).getAttribute("recentlyviewed");
    }

    @Test
    public void getInstance() {
        HttpSessionRecentlyViewedProducts temp = HttpSessionRecentlyViewedProducts.getInstance();
        Assert.assertNotNull(temp);
    }

    @Test
    public void addWithSizeThree() {
        String requestPathInfo = "/1";
        int listSize = 3;
        when(recentlyViewedProducts.size()).thenReturn(listSize);
        when(httpSessionRecentlyViewedProducts.getRecentlyViewedProducts(session)).thenReturn(recentlyViewedProducts);

        httpSessionRecentlyViewedProducts.add(session, requestPathInfo);

        verify(recentlyViewedProducts).addFirst(product);
        verify(recentlyViewedProducts).removeLast();
    }


    @Test
    public void addFirstElement() {
        int listSize = 0;
        String requestPathInfo = "/1";
        when(recentlyViewedProducts.size()).thenReturn(listSize);

        httpSessionRecentlyViewedProducts.add(session, requestPathInfo);

        verify(recentlyViewedProducts, never()).removeLast();
        verify(recentlyViewedProducts).addFirst(product);
    }
}