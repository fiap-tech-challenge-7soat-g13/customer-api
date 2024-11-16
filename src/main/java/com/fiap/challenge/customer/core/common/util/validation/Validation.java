package com.fiap.challenge.customer.core.common.util.validation;

import com.fiap.challenge.customer.core.common.util.Strings;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Validation {

    private String message;

    public static Validation notBlank(String value, String message, Object... params) {
        return assertFalse(StringUtils.isBlank(value), message, params);
    }

    public static Validation notInvalidEmail(String value, String message, Object... params) {
        return assertFalse(value != null && !Strings.isValidEmail(value), message, params);
    }

    public static Validation notInvalidDocument(String value, String message, Object... params) {
        return assertFalse(value != null && !Strings.isValidDocument(value), message, params);
    }

    public static Validation assertFalse(boolean condition, String message, Object... params) {
        return new Validation(condition ? String.format(message, params) : null);
    }

    public Optional<String> getMessage() {
        return Optional.ofNullable(message);
    }

}
