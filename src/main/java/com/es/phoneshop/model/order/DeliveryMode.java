package com.es.phoneshop.model.order;

import java.math.BigDecimal;

public enum DeliveryMode {
    COURIER("courier", new BigDecimal(5)), STORE_PICK_UP("storePickUp", new BigDecimal(0));
    private String stringDeliveryMode;
    private BigDecimal deliveryCost;

    DeliveryMode(String stringDeliveryMode, BigDecimal deliveryCost) {
        this.stringDeliveryMode = stringDeliveryMode;
        this.deliveryCost = deliveryCost;
    }

    public static DeliveryMode getDeliveryMode(String stringDeliveryMode) {
        for (DeliveryMode deliveryMode : values()) {
            if (deliveryMode.stringDeliveryMode.equals(stringDeliveryMode)) {
                return deliveryMode;
            }
        }
        return null;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }
}
