package com.es.phoneshop.model.product;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private Currency usd;
    private Product productForSaveFirst;
    private Product productForSaveSecond;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        usd = Currency.getInstance("USD");
        productForSaveFirst = new Product(1L, "simsxg75",
                "FirstForTest", new ArrayList<>(), usd, 40,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        productForSaveFirst.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(150)));
    }

    @After
    public void clean() {
        final Long ID = 1L;
        productDao.delete(ID);
    }

    @Test
    public void testGetInstance() {
        ProductDao productDaoTest = ArrayListProductDao.getInstance();

        Assert.assertTrue(productDao == productDaoTest);
        Assert.assertEquals(productDao, productDaoTest);
    }

    @Test
    public void testFindProductsNullPrice() {

        ProductDao actualProductDao = ArrayListProductDao.getInstance();
        productForSaveFirst.setPrice(new ProductPrice("", null));
        actualProductDao.save(productForSaveFirst);
        Assert.assertEquals(productDao.findProducts().size(), actualProductDao.findProducts().size());
        Assert.assertEquals(productDao.findProducts(), actualProductDao.findProducts());
    }

    @Test
    public void testFindProductZeroStock() {
        ProductDao actualProductDao = ArrayListProductDao.getInstance();
        productForSaveFirst.setStock(0);
        actualProductDao.save(productForSaveFirst);
        Assert.assertEquals(productDao.findProducts().size(), actualProductDao.findProducts().size());
        Assert.assertEquals(productDao.findProducts(), actualProductDao.findProducts());
    }

    @Test
    public void testGetProducts() {
        productDao.save(productForSaveFirst);
        Assert.assertEquals(productForSaveFirst, productDao.getProduct(1L));
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductsNegativeIndex() {
        productDao.getProduct(-1L);
    }

    @Test
    public void testSave() {
        int expectedSize = 1;
        productDao.save(productForSaveFirst);
        Assert.assertEquals(expectedSize, productDao.findProducts().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductNull() {
        productDao.save(null);
    }

    @Test
    public void testDelete() {
        final Long id = 1L;
        productDao.save(productForSaveFirst);
        int expectedSize = 0;
        productDao.delete(id);
        Assert.assertEquals(expectedSize, productDao.findProducts().size());
    }
}
