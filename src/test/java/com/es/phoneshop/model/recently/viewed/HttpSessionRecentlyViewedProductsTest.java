package com.es.phoneshop.model.recently.viewed;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductPrice;
import org.junit.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.LinkedList;

public class HttpSessionRecentlyViewedProductsTest {
    private ProductDao productDao;
    private RecentlyViewedProductsService recentlyViewedProductsService;
    private LinkedList<Product> recentlyViewedProducts;
    private Product productFirstForSave;
    private Product productSecondForSave;
    private Product productThirdForSave;
    private Product productFourthForSave;
    private Long idForFirstProduct = 1L;
    private Long idForSecondProduct = 2L;
    private Long idForThirdProduct = 3L;
    private Long idForFourthProduct = 4L;
    private Currency usd = Currency.getInstance("USD");
    private HttpServletRequest request;

    @Before
    public void setup() {
        recentlyViewedProductsService = HttpSessionRecentlyViewedProducts.getInstance();
        productDao = ArrayListProductDao.getInstance();
        recentlyViewedProducts = new LinkedList<>();

        productFirstForSave = new Product(idForFirstProduct, "sgs", "Samsung Galaxy S", new ArrayList<>(), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productFirstForSave.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(100)));
        productDao.save(productFirstForSave);

        productSecondForSave = new Product(idForSecondProduct, "sgs2", "Samsung Galaxy S II", new ArrayList<>(), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        productSecondForSave.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(200)));
        productDao.save(productSecondForSave);

        productThirdForSave = new Product(idForThirdProduct, "sgs3", "Samsung Galaxy S III", new ArrayList<>(), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg");
        productThirdForSave.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(300)));
        productDao.save(productThirdForSave);

        productFourthForSave = new Product(idForFourthProduct, "iphone", "Apple iPhone", new ArrayList<>(), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
        productFourthForSave.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(200)));
        productDao.save(productFourthForSave);

    }

    @After
    public void clean() {
        productDao.delete(idForFirstProduct);
        productDao.delete(idForSecondProduct);
        productDao.delete(idForThirdProduct);
        productDao.delete(idForFourthProduct);
    }

    @Test
    public void getInstance() {
        RecentlyViewedProductsService recentlyViewedProducts = HttpSessionRecentlyViewedProducts.getInstance();

        Assert.assertTrue(recentlyViewedProductsService == recentlyViewedProducts);
        Assert.assertEquals(recentlyViewedProductsService, recentlyViewedProducts);
    }

    @Ignore
    @Test
    public void getRecentlyViewedProductsNotNull() {

    }

    @Test
    public void addFourProducts() {
        LinkedList<Product> expectedListProducts = new LinkedList<>();
        int expectedSize = 3;
        expectedListProducts.add(productFourthForSave);
        expectedListProducts.add(productThirdForSave);
        expectedListProducts.add(productSecondForSave);
        recentlyViewedProductsService.add(recentlyViewedProducts, productFirstForSave);
        recentlyViewedProductsService.add(recentlyViewedProducts, productSecondForSave);
        recentlyViewedProductsService.add(recentlyViewedProducts, productThirdForSave);
        recentlyViewedProductsService.add(recentlyViewedProducts, productFourthForSave);

        Assert.assertEquals(expectedSize,recentlyViewedProducts.size());
        Assert.assertEquals(expectedListProducts, recentlyViewedProducts);
    }
}