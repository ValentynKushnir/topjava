package ru.javawebinar.topjava.util;

public class ValidationUtils {
    public static void requireNotNullOrEmpty(Integer value, String errorMessage) {
        if (!(value != null && value != 0)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
