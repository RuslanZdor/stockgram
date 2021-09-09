package com.stocker.data.job.stock;

import com.stocker.data.job.ICalculateJob;
import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class CalculateNextRising implements ICalculateJob {

    public void calculate(Company company) {
        Objects.requireNonNull(company, "Company cannot be null");
        Day prevDay = null;
        for (Day day : company.getDays()) {
            if (prevDay != null && !prevDay.isFinished()) {
                prevDay.setNextRise(day.getPrice() > prevDay.getPrice());
            }
            prevDay = day;
        }
    }
}
