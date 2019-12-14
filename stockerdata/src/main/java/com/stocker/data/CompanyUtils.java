package com.stocker.data;

import com.stocker.yahoo.data.Company;

import java.time.LocalDateTime;
import java.util.Objects;

public class CompanyUtils {

    private static final int MAX_TIME_UPDATE = 5;

    public static boolean needUpdate(Company company) {
        if (company.getDays().isEmpty()) {
            return true;
        }

        if (Objects.isNull(company.getDays().last().getLastUpdate())) {
            return true;
        }

        return LocalDateTime.now().minusMinutes(MAX_TIME_UPDATE).isAfter(company.getDays().last().getLastUpdate());
    }

}
