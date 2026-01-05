package com.kesi.tracker.user.domain;

import com.kesi.tracker.util.validator.PhoneValidator;

public record Phone(String value) {
    public Phone {
        if(!PhoneValidator.isValid(value))
            throw new IllegalArgumentException("Phone number is invalid");
    }
}
