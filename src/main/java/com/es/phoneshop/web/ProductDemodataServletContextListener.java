package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductPrice;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class ProductDemodataServletContextListener implements ServletContextListener {
    private ProductDao productDao = ArrayListProductDao.getInstance();;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String insertProductDemodata = servletContextEvent.getServletContext().getInitParameter("productDemodata");
        if ("true".equals(insertProductDemodata)) {
            fillProductList().forEach(productDao::save);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    synchronized List<Product> fillProductList() {
        List<Product> result = new ArrayList();
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(1L, "sgs", "Samsung Galaxy S", new ArrayList<>(), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        product.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(100)));
        product.getPrices().add(new ProductPrice("10 Oct 2018", new BigDecimal(110)));
        product.getPrices().add(new ProductPrice("1 Sep 2018", new BigDecimal(150)));
        result.add(product);
        product = new Product(2L, "sgs2", "Samsung Galaxy S II", new ArrayList<>(), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        product.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(200)));
        product.getPrices().add(new ProductPrice("10 Oct 2018", new BigDecimal(220)));
        product.getPrices().add(new ProductPrice("1 Sep 2018", new BigDecimal(270)));
        result.add(product);
        product = new Product(3L, "sgs3", "Samsung Galaxy S III", new ArrayList<>(), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg");
        product.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(300)));
        product.getPrices().add(new ProductPrice("10 Oct 2018", new BigDecimal(350)));
        product.getPrices().add(new ProductPrice("1 Sep 2018", new BigDecimal(420)));
        result.add(product);
        product = new Product(4L, "iphone", "Apple iPhone", new ArrayList<>(), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
        product.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(200)));
        product.getPrices().add(new ProductPrice("10 Oct 2018", new BigDecimal(250)));
        product.getPrices().add(new ProductPrice("1 Sep 2018", new BigDecimal(320)));
        result.add(product);
        product = new Product(5L, "iphone6", "Apple iPhone 6", new ArrayList<>(), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg");
        product.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(1000)));
        product.getPrices().add(new ProductPrice("10 Oct 2018", new BigDecimal(1250)));
        product.getPrices().add(new ProductPrice("1 Sep 2018", new BigDecimal(1320)));
        result.add(product);
        product = new Product(6L, "htces4g", "HTC EVO Shift 4G", new ArrayList<>(), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        product.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(350)));
        product.getPrices().add(new ProductPrice("10 Oct 2018", new BigDecimal(450)));
        product.getPrices().add(new ProductPrice("1 Sep 2018", new BigDecimal(520)));
        result.add(product);
        product = new Product(7L, "sec901", "Sony Ericsson C901", new ArrayList<>(), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg");
        product.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(420)));
        product.getPrices().add(new ProductPrice("10 Oct 2018", new BigDecimal(480)));
        product.getPrices().add(new ProductPrice("1 Sep 2018", new BigDecimal(520)));
        result.add(product);
        product = new Product(8L, "xperiaxz", "Sony Xperia XZ", new ArrayList<>(), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg");
        product.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(120)));
        product.getPrices().add(new ProductPrice("10 Oct 2018", new BigDecimal(150)));
        product.getPrices().add(new ProductPrice("1 Sep 2018", new BigDecimal(170)));
        result.add(product);
        product = new Product(9L, "nokia3310", "Nokia 3310", new ArrayList<>(), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg");
        product.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(70)));
        product.getPrices().add(new ProductPrice("10 Oct 2018", new BigDecimal(100)));
        product.getPrices().add(new ProductPrice("1 Sep 2018", new BigDecimal(120)));
        result.add(product);
        product = new Product(10L, "palmp", "Palm Pixi", new ArrayList<>(), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg");
        product.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(170)));
        product.getPrices().add(new ProductPrice("10 Oct 2018", new BigDecimal(200)));
        product.getPrices().add(new ProductPrice("1 Sep 2018", new BigDecimal(220)));
        result.add(product);
        product = new Product(11L, "simc56", "Siemens C56", new ArrayList<>(), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg");
        product.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(70)));
        product.getPrices().add(new ProductPrice("10 Oct 2018", new BigDecimal(100)));
        product.getPrices().add(new ProductPrice("1 Sep 2018", new BigDecimal(120)));
        result.add(product);
        product = new Product(12L, "simc61", "Siemens C61", new ArrayList<>(), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg");
        product.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(80)));
        product.getPrices().add(new ProductPrice("10 Oct 2018", new BigDecimal(110)));
        product.getPrices().add(new ProductPrice("1 Sep 2018", new BigDecimal(130)));
        result.add(product);
        product = new Product(13L, "simsxg75", "Siemens SXG75", new ArrayList<>(), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        product.getPrices().add(new ProductPrice("10 Jan 2019", new BigDecimal(150)));
        product.getPrices().add(new ProductPrice("10 Oct 2018", new BigDecimal(180)));
        product.getPrices().add(new ProductPrice("1 Sep 2018", new BigDecimal(200)));
        result.add(product);
        return result;
    }
}
