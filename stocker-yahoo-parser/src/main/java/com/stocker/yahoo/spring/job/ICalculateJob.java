package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;


/**
 * Interface for calculation daily parameter for company
 */
public interface ICalculateJob {
    void calculate(Company company);
}
