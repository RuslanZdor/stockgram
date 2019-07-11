package com.stocker;

import java.util.Arrays;
import java.util.Optional;

/**
 * enum for company request parameter
 */
public enum Graphnterval {
    DAILY("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly");

    String value;

    Graphnterval(String p) {
        value = p;
    }

    public static Graphnterval of(String str) {
        Optional<Graphnterval> founded = Arrays.stream(Graphnterval.values()).filter(v -> v.value.equals(str)).findFirst();
        return founded.orElse(DAILY);
    }
}
