package com.es.phoneshop.model.recently.viewed;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Assert;
import org.junit.Before;
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
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(recentlyViewedProducts);
        when(httpSessionRecentlyViewedProducts.getRecentlyViewedProducts(request)).thenReturn(recentlyViewedProducts);
    }

    @Test
    public void testParseId() {
        when(request.getPathInfo()).thenReturn("/1");
        Long expectedId = 1L;
        Long actualId = httpSessionRecentlyViewedProducts.parseProductId(request);
        Assert.assertEquals(expectedId, actualId);
    }

    @Test(expected = NumberFormatException.class)
    public void testParseIdIncorrectPath() {
        when(request.getPathInfo()).thenReturn("null");
        httpSessionRecentlyViewedProducts.parseProductId(request);
    }

    @Test
    public void getRecentlyViewedProductsListNull() {

        httpSessionRecentlyViewedProducts.getRecentlyViewedProducts(request);

        verify(session).getAttribute("recentlyviewed");
    }

    @Test
    public void getInstance() {
        HttpSessionRecentlyViewedProducts temp = HttpSessionRecentlyViewedProducts.getInstance();
        Assert.assertNotNull(temp);
    }


    @Test
    public void addWithSizeThree() {
        int listSize = 3;
        when(recentlyViewedProducts.size()).thenReturn(listSize);
        when(request.getPathInfo()).thenReturn("/1");

        httpSessionRecentlyViewedProducts.add(request);

        verify(recentlyViewedProducts).removeLast();
    }

    @Test
    public void addFirstElement() {
        int listSize = 0;
        when(recentlyViewedProducts.size()).thenReturn(listSize);
        when(request.getPathInfo()).thenReturn("/1");

        httpSessionRecentlyViewedProducts.add(request);

        verify(recentlyViewedProducts, never()).removeLast();
        verify(recentlyViewedProducts).addFirst(product);
        verify(addToRecentlyViewedProductsResult).setProducts(recentlyViewedProducts);
    }
}