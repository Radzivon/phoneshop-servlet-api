package com.es.phoneshop.model.order;

public enum PaymentMethod {
    CASH("cash"), CREDIT_CARD("creditCard");
    private String stringPaymentMethod;

    PaymentMethod(String stringPaymentMethod) {
        this.stringPaymentMethod = stringPaymentMethod;
    }

    public static PaymentMethod getPaymentMethod(String stringPaymentMethod) {
        for (PaymentMethod paymentMethod : values()) {
            if (paymentMethod.stringPaymentMethod.equals(stringPaymentMethod)) {
                return paymentMethod;
            }
        }
        return null;
    }
}
