package com.kesi.tracker.user.domain;

import com.kesi.tracker.core.exception.BusinessException;
import com.kesi.tracker.core.exception.ErrorCode;
import org.apache.commons.validator.routines.EmailValidator;

public record Email (String value) {
    public Email {
        if (!EmailValidator.getInstance().isValid(value)) {
            throw new BusinessException(ErrorCode.INVALID_EMAIL_FORMAT);
        }
    }
}
