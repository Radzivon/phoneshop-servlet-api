package com.es.phoneshop.model.order;

import java.util.regex.Pattern;

public class ValidPhoneNumber {
    public static boolean isPhoneNumber(String string) {
        return Pattern.matches("^\\+[1-9]{1}[0-9]{3,14}$", string);
    }
}
