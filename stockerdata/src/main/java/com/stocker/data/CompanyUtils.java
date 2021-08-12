package com.stocker.data;

import com.stocker.yahoo.data.Company;

public class CompanyUtils {

    private static final int MAX_TIME_UPDATE = 5;

    public static boolean needUpdate(Company company) {
        return !company.getDays().isEmpty();
    }

}
