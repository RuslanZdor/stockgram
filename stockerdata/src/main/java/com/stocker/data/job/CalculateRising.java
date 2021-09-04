package com.stocker.data.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class CalculateRising implements ICalculateJob {

    public void calculate(Company company) {
        Objects.requireNonNull(company, "Company cannot be null");

        double prevDayPrice = 0.0;
        for (Day day : company.getDays()) {
            if (!day.isFinished()) {
                day.setRising(day.getPrice() > prevDayPrice);
            }
            prevDayPrice = day.getPrice();
        }
    }
}
