package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class CalculateRising implements ICalculateJob {

    public void calculate(Company company) {
        Objects.requireNonNull(company, "Company cannot be null");

        company.getDays().forEach(day -> {
            List<Day> tail = new ArrayList<>(company.getDays().headSet(day));
            Collections.reverse(tail);
            if (!tail.isEmpty()) {
                day.setRising(day.getPrice() > tail.get(0).getPrice());
            }
        });
    }
}
