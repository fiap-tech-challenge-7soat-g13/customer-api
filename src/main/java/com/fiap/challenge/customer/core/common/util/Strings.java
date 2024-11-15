package com.fiap.challenge.customer.core.common.util;

import br.com.caelum.stella.validation.CPFValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Strings {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    public static boolean isValidEmail(String value) {
        return EMAIL_PATTERN.matcher(value).matches();
    }

    public static boolean isValidDocument(String value) {
        return new CPFValidator().invalidMessagesFor(value).isEmpty();
    }

}
