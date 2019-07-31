package com.stocker;

import java.util.Arrays;
import java.util.Optional;

/**
 * enum for company request parameter
 */
public enum GraphInterval {
    DAILY("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly");

    private final String value;

    GraphInterval(String p) {
        value = p;
    }

    public static GraphInterval of(String str) {
        Optional<GraphInterval> founded = Arrays.stream(GraphInterval.values()).filter(v -> v.value.equals(str)).findFirst();
        return founded.orElse(DAILY);
    }
}
