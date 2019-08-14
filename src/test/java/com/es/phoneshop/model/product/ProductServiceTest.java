package com.es.phoneshop.model.product;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    private ProductDao productDao;
    @Mock
    private Product firstProduct;
    @Mock
    private Product secondProduct;
    @Spy
    private List<Product> productList;
    @InjectMocks
    private ProductService productService;
    private String query;
    private String sortBy;
    private String order;

    @Before
    public void setup() {
        when(firstProduct.getDescription()).thenReturn("a");
        when(secondProduct.getDescription()).thenReturn("z");
        when(firstProduct.getPrice()).thenReturn(new BigDecimal(2));
        when(secondProduct.getPrice()).thenReturn(new BigDecimal(1));
        productList = new ArrayList<>();
        productList.add(firstProduct);
        productList.add(secondProduct);
        when(productDao.findProducts()).thenReturn(productList);
        query = null;
        sortBy = null;
        order = null;
    }

    @Test
    public void testGetInstance() {
        ProductService productService = ProductService.getInstance();
        Assert.assertNotNull(productService);
    }

    @Test
    public void testFindProductsQueryNotNull() {
        query = "a";

        Assert.assertNotEquals(productList, productService.findProducts(query, sortBy, order));
    }

    @Test
    public void testFindProductsSortByDescriptionAsc() {
        sortBy = "description";
        order = "asc";

        Assert.assertEquals(productList, productService.findProducts(query, sortBy, order));
    }
    @Test
    public void testFindProductsSortByDescriptionDesc() {
        sortBy = "description";
        order = "desc";

        Assert.assertNotEquals(productList, productService.findProducts(query, sortBy, order));
    }
    @Test
    public void testFindProductsSortByPriceAsc() {
        sortBy = "price";
        order = "asc";

        Assert.assertNotEquals(productList, productService.findProducts(query, sortBy, order));
    }
    @Test
    public void testFindProductsSortByPriceDesc() {
        sortBy = "price";
        order = "desc";

        Assert.assertEquals(productList, productService.findProducts(query, sortBy, order));
    }
}
