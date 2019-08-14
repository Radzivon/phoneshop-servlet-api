package com.es.phoneshop.model.order;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class OrderValidator {
    private boolean isPhoneNumber(String string) {
        return Pattern.matches("^\\+[1-9]{1}[0-9]{3,14}$", string);
    }

    public ValidOrderResult validOrder(Order order, String firstName, String lastName, String phoneNumber, String stringDeliveryMode, String deliveryAddress, String stringPaymentMethod) {
        boolean hasError = false;
        ValidOrderResult validOrderResult = new ValidOrderResult();
        if (firstName != null && !firstName.equals("")) {
            order.setFirstName(firstName);
        } else {
            hasError = true;
            validOrderResult.addError("errorFirstName", "Incorrect value");
        }

        if (lastName != null && !lastName.equals("")) {
            order.setLastName(lastName);
        } else {
            hasError = true;
            validOrderResult.addError("errorLastName", "Incorrect value");
        }

        if (phoneNumber != null && !phoneNumber.equals("") && isPhoneNumber(phoneNumber)) {
            order.setPhoneNumber(phoneNumber);
        } else {
            hasError = true;
            validOrderResult.addError("errorPhoneNumber", "Incorrect value");
        }

        DeliveryMode deliveryMode = DeliveryMode.getDeliveryMode(stringDeliveryMode);
        order.setDeliveryMode(deliveryMode);

        BigDecimal deliveryCost = deliveryMode.getDeliveryCost();
        order.setDeliveryCost(deliveryCost);

        order.calculateTotalCost();

        if (deliveryAddress != null && !deliveryAddress.equals("")) {
            order.setDeliveryAddress(deliveryAddress);
        } else {
            hasError = true;
            validOrderResult.addError("errorDeliveryAddress", "Incorrect value");
        }
        PaymentMethod paymentMethod = PaymentMethod.getPaymentMethod(stringPaymentMethod);
        order.setPaymentMethod(paymentMethod);

        validOrderResult.setHasError(hasError);
        return validOrderResult;
    }
}
