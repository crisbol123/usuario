package com.pragma.usuario.usuario.domain.util;

public class DomainConstants {
    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }


    public static final String USER_MUST_BE_AT_LEAST_18_YEARS_OLD = "User must be at least 18 years old";
    public static final String PHONE_NUMBER_REGEX = "^\\+?[0-9]{1,13}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    public static final String PHONE_NUMBER_IS_INVALID = "Phone number is invalid";
    public static final String EMAIL_IS_INVALID = "Email is invalid";
}
