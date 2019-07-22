package com.es.phoneshop.model.product;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
        productForSaveFirst = new Product(14L, "simsxg75",
                "FirstForTest", new ArrayList<>(), usd, 40,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        productForSaveFirst.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(150)));
        productForSaveSecond = new Product(14L, "simsxg75",
                "SecondForTest", new ArrayList<>(), usd, 40,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        productForSaveSecond.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(100)));
    }

    @Test
    public void testGetInstance() {
        ProductDao productDaoTest = ArrayListProductDao.getInstance();

        Assert.assertTrue(productDao == productDaoTest);
        Assert.assertEquals(productDao, productDaoTest);
    }

    @Test
    public void testFindProductsIncorrectQuerySortByNullOrderNull() {
        productDao.save(productForSaveSecond);
        String incorrectQuery = "FirstForTestB";
        int expectedSize = 0;
        Assert.assertEquals(expectedSize, productDao.findProducts(incorrectQuery, null, null).size());
    }

    @Test
    public void testFindProductsCorrectQuerySortByOrder() {
        productDao.save(productForSaveSecond);

        Assert.assertEquals(productForSaveSecond, productDao.findProducts("SecondForTest", "description", "asc").get(0));
    }

    @Test
    public void testFindProductsQueryNull() {
        productDao.save(productForSaveSecond);

        Assert.assertEquals(productForSaveSecond, productDao.findProducts(null, null, null).get(0));
    }

    @Test
    public void testFindProductsCorrectQueryNotNullSortAndByOrderNull() {
        productDao.save(productForSaveSecond);

        Assert.assertEquals(productForSaveSecond, productDao.findProducts("SecondForTest", null, null).get(0));
    }

    @Test
    public void testFindProductsNullPrice() {
        ProductDao actualProductDao = ArrayListProductDao.getInstance();
        productForSaveFirst.setPrice(new ProductPrice("",null));
        actualProductDao.save(productForSaveFirst);
        Assert.assertEquals(productDao.findProducts(null, null, null).size(), actualProductDao.findProducts(null, null, null).size());
        Assert.assertEquals(productDao.findProducts(null, null, null), actualProductDao.findProducts(null, null, null));
    }

    @Test
    public void testFindProductZeroStock() {
        ProductDao actualProductDao = ArrayListProductDao.getInstance();
        productForSaveFirst.setStock(0);
        actualProductDao.save(productForSaveFirst);
        Assert.assertEquals(productDao.findProducts(null, null, null).size(), actualProductDao.findProducts(null, null, null).size());
        Assert.assertEquals(productDao.findProducts(null, null, null), actualProductDao.findProducts(null, null, null));
    }

    @Test
    public void testGetProducts() {
        productForSaveFirst.setId(13L);
        productDao.save(productForSaveFirst);
        Assert.assertEquals(productForSaveFirst, productDao.getProduct(13L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProductsNegativeIndex() {
        productDao.getProduct(-1L);
    }

    @Test
    public void testSave() {
        int expectedSize = 1;
        productDao.save(productForSaveFirst);
        Assert.assertEquals(expectedSize, productDao.findProducts("FirstForTest", null, null).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductNull() {
        productDao.save(null);
    }

    @Test
    public void testDelete() {
        productForSaveFirst.setDescription("TestDelete");
        productForSaveFirst.setId(999L);
        productDao.save(productForSaveFirst);
        int expectedSize = 0;
        productDao.delete(999L);
        Assert.assertEquals(expectedSize, productDao.findProducts("TestDelete", null, null).size());
    }
}
