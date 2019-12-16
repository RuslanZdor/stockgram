package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class CalculateNextRising implements ICalculateJob {

    public void calculate(Company company) {
        Objects.requireNonNull(company, "Company cannot be null");

        company.getDays().forEach(day -> {
            List<Day> tail = new ArrayList<>(company.getDays().headSet(day));
            Collections.reverse(tail);
            if (!tail.isEmpty()) {
                tail.get(0).setNextRise(day.getPrice() > tail.get(0).getPrice());
            }
        });
    }
}
