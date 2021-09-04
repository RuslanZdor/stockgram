package com.stocker.data.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
public class CalculateRising implements ICalculateJob {

    public void calculate(Company company) {
        Objects.requireNonNull(company, "Company cannot be null");

        company.getDays().stream().filter(day -> !day.isFinished()).forEach(day -> {
            List<Day> tail = company.getDays();
            Collections.reverse(tail);
            if (!tail.isEmpty()) {
                day.setRising(day.getPrice() > tail.get(0).getPrice());
            }
        });
    }
}
