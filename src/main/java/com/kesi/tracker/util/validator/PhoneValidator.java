package com.kesi.tracker.util.validator;


public class PhoneValidator {
    public static boolean isValid(String phone) {
        if (phone == null || phone.length() != 11)
            return false;

        for(char c : phone.toCharArray())
            if(!Character.isDigit(c)) return false;

        if(!phone.startsWith("010") && !phone.startsWith("011"))
            return false;

        return true;
    }
}
