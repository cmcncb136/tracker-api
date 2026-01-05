package com.kesi.tracker.user.domain;

import org.apache.commons.validator.routines.EmailValidator;

public record Email (String value) {
    public Email {
        if (!EmailValidator.getInstance().isValid(value)) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }
}
