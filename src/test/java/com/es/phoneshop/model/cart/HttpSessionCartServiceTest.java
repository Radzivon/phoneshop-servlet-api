package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductPrice;
import org.junit.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

public class HttpSessionCartServiceTest {
    private ProductDao productDao;
    private CartService cartService;
    private Product productFirstForSave;
    private Product productSecondForSave;
    private BigDecimal productPriceForFirstProduct = new BigDecimal(100);
    private BigDecimal productPriceForSecondProduct = new BigDecimal(150);
    private Cart cart;
    private Currency usd = Currency.getInstance("USD");
    private Long idForFirstProduct = 1L;
    private Long idForSecondProduct = 2L;
    private int stock = 2;

    @Before
    public void setup() {
        cartService = HttpSessionCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();

        productFirstForSave = new Product(idForFirstProduct, "simsxg75",
                "FirstForTest", new ArrayList<>(), usd, stock,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        productFirstForSave.getPrices().add(new ProductPrice("10 Jan 2019", productPriceForFirstProduct));
        productDao.save(productFirstForSave);

        productSecondForSave = new Product(idForSecondProduct, "simsxg75",
                "FirstForTest", new ArrayList<>(), usd, stock,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        productSecondForSave.getPrices().add(new ProductPrice("10 Jan 2019", productPriceForSecondProduct));
        productDao.save(productSecondForSave);

        cart = new Cart();
    }

    @After
    public void clean() {
        productDao.delete(idForFirstProduct);
        productDao.delete(idForSecondProduct);
    }

    @Test
    public void getInstance() {
        CartService cartServiceTest = HttpSessionCartService.getInstance();

        Assert.assertTrue(cartService == cartServiceTest);
        Assert.assertEquals(cartService, cartServiceTest);
    }

    @Ignore
    @Test
    public void getCartNotNull() {

    }

    @Test(expected = OutOfStockException.class)
    public void addQuantityBiggerThanProductStock() throws OutOfStockException {
        int incorrectQuantity = 3;
        cartService.add(cart, productFirstForSave, incorrectQuantity);
    }

    @Test
    public void addCorrectWithQuantityOne() throws OutOfStockException {
        int quantity = 1;
        cartService.add(cart, productFirstForSave, quantity);
        Assert.assertEquals(productPriceForFirstProduct, cart.getTotalCost());
        Assert.assertEquals(quantity, cart.getTotalQuantity());
    }

    @Test
    public void recalculateThanAddFirstProduct() throws OutOfStockException {
        int quantity = 2;
        cartService.add(cart, productFirstForSave, quantity);
        BigDecimal actualPrice = productPriceForFirstProduct.multiply(new BigDecimal(quantity));
        Assert.assertEquals(actualPrice, cart.getTotalCost());
        Assert.assertEquals(quantity, cart.getTotalQuantity());
    }

    @Test
    public void recalculateThanAddSecondProduct() throws OutOfStockException {
        int quantityForFirstProduct = 2;
        int quantityForSecondProduct = 2;
        int actualQuantity = quantityForFirstProduct + quantityForSecondProduct;
        cartService.add(cart, productFirstForSave, quantityForFirstProduct);
        cartService.add(cart, productSecondForSave, quantityForSecondProduct);
        BigDecimal actualPrice = productPriceForFirstProduct.multiply(new BigDecimal(quantityForFirstProduct))
                .add(productPriceForSecondProduct.multiply(new BigDecimal(quantityForSecondProduct)));
        Assert.assertEquals(actualPrice, cart.getTotalCost());
        Assert.assertEquals(actualQuantity, cart.getTotalQuantity());
    }

}
