package com.es.phoneshop.model.cart;

public class CartItem {
    private Long productId;
    private Long quantity;

    public CartItem(Long productId, Long quantity) {
        if(productId == null){
            throw new IllegalArgumentException();
        }
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
