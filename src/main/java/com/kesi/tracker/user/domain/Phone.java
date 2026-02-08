package com.kesi.tracker.user.domain;

import com.kesi.tracker.core.exception.BusinessException;
import com.kesi.tracker.core.exception.ErrorCode;
import com.kesi.tracker.util.validator.PhoneValidator;

public record Phone(String value) {
    public Phone {
        if(!PhoneValidator.isValid(value))
            throw new BusinessException(ErrorCode.INVALID_PHONE_FORMAT);
    }
}
