package com.stocker.data.job;

import com.stocker.yahoo.data.Company;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class CalculateNextRising implements ICalculateJob {

    public void calculate(Company company) {
        Objects.requireNonNull(company, "Company cannot be null");

//        company.getDays().stream().filter(day -> !day.isFinished()).forEach(day -> {
//            List<Day> tail = new ArrayList<>(company.getDays().headSet(day));
//            Collections.reverse(tail);
//            if (!tail.isEmpty()) {
//                tail.get(0).setNextRise(day.getPrice() > tail.get(0).getPrice());
//            }
//        });
    }
}