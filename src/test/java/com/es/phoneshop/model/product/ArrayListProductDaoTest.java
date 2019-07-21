package com.es.phoneshop.model.product;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private Currency usd;
    private Product product;
    private String QUERY = "Siemens SXG75";
    private String SORT_BY = "description";
    private String ORDER = "asc";

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        usd = Currency.getInstance("USD");
        product = new Product(14L, "simsxg75",
                "Siemens SXG75", new ArrayList<>(), usd, 40,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        product.getPrice().add(new ProductPrice("10 Jan 2019", new BigDecimal(150)));
    }

    @Test
    public void testFindProductsNullPrice() {

        ProductDao actualProductDao = ArrayListProductDao.getInstance();
        product.setPrice(null);
        actualProductDao.save(product);
        Assert.assertEquals(productDao.findProducts(QUERY, SORT_BY, ORDER).size(), actualProductDao.findProducts(QUERY, SORT_BY, ORDER).size());
        Assert.assertEquals(productDao.findProducts(QUERY, SORT_BY, ORDER), actualProductDao.findProducts(QUERY, SORT_BY, ORDER));
    }

    @Test
    public void testFindProductZeroStock() {
        ProductDao actualProductDao = ArrayListProductDao.getInstance();
        product.setStock(0);
        actualProductDao.save(product);
        Assert.assertEquals(productDao.findProducts(QUERY, SORT_BY, ORDER).size(), actualProductDao.findProducts(QUERY, SORT_BY, ORDER).size());
        Assert.assertEquals(productDao.findProducts(QUERY, SORT_BY, ORDER), actualProductDao.findProducts(QUERY, SORT_BY, ORDER));
    }

    @Test
    public void testGetProducts() {
        product.setId(13L);
        productDao.save(product);
        Assert.assertEquals(product, productDao.getProduct(13L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProductsNegativeIndex() {
        productDao.getProduct(-1L);
    }

    @Test
    public void testSave() {
        int expectedSize = productDao.findProducts(QUERY, SORT_BY, ORDER).size() + 1;
        productDao.save(product);
        Assert.assertEquals(expectedSize, productDao.findProducts(QUERY, SORT_BY, ORDER).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductNull() {
        productDao.save(null);
    }

    @Test
    public void testDelete() {
        productDao.save(product);
        int expectedSize = productDao.findProducts(QUERY, SORT_BY, ORDER).size() - 1;
        productDao.delete(14L);
        Assert.assertEquals(expectedSize, productDao.findProducts(QUERY, SORT_BY, ORDER).size());
    }
}
