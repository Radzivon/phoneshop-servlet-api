package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Comparator;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    private ProductDao productDao;
    @Mock
    private Product firstProduct;
    @Mock
    private Product secondProduct;
    @InjectMocks
    private ProductService productService;
    private String query;
    private String sortBy;
    private String order;

    @Before
    public void setup() {
        query = null;
        sortBy = null;
        order = null;
    }

    @Ignore
    @Test
    public void findProductsNullParam() {
        productService.findProducts(query, sortBy, order);


    }
}
