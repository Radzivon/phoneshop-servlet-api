package com.es.phoneshop.model.product;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertFalse;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private Currency usd;
    private Product product;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        usd = Currency.getInstance("USD");
        product = new Product(14L, "simsxg75",
                "Siemens SXG75", new BigDecimal(150), usd, 40,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductsNegativePrice() {
        ProductDao actualProductDao = new ArrayListProductDao();
        product.setPrice(null);
        actualProductDao.save(product);
        Assert.assertEquals(productDao.findProducts().size(), actualProductDao.findProducts().size());
        Assert.assertEquals(productDao.findProducts(), actualProductDao.findProducts());
    }

    @Test
    public void testFindProductZeroStock() {
        ProductDao actualProductDao = new ArrayListProductDao();
        product.setStock(0);
        actualProductDao.save(product);
        Assert.assertEquals(productDao.findProducts().size(), actualProductDao.findProducts().size());
        Assert.assertEquals(productDao.findProducts(), actualProductDao.findProducts());
    }

    @Test
    public void testGetProducts() {
        product.setId(13L);
        Assert.assertEquals(product, productDao.getProduct(13L));
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductsNegativeIndex() {
        productDao.getProduct(-1L);
    }

    @Test
    public void testSave() {
        int expectedSize = productDao.findProducts().size() + 1;
        productDao.save(product);
        Assert.assertEquals(expectedSize, productDao.findProducts().size());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testSaveProductNull() {
        productDao.save(null);
    }

    @Test
    public void testDelete() {
        int expectedSize = productDao.findProducts().size() - 1;
        productDao.delete(1L);
        Assert.assertEquals(expectedSize, productDao.findProducts().size());
    }
}
