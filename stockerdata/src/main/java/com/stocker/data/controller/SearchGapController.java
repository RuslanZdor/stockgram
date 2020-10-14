package com.stocker.data.controller;

import com.stocker.data.spring.repo.CompanyRepository;
import com.stocker.yahoo.data.Day;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SearchGapController {

    private final CompanyRepository companyRepository;

    @GetMapping("/gaps/{symbol}/")
    public void findCompany(@PathVariable("symbol") String symbol) throws IllegalArgumentException {
        companyRepository.findAll().subscribe(company -> {
            Iterator<Day> dayIterator = company.getDays().iterator();
            Day lastDay = null;
            while (dayIterator.hasNext()) {
                Day currentDay = dayIterator.next();
                if (lastDay != null) {
                    if (currentDay.getOpenPrice() < lastDay.getMinPrice()
                    || currentDay.getOpenPrice() > lastDay.getMaxPrice()) {
                        double gap = 0.0f;
                        if (currentDay.getOpenPrice() < lastDay.getMinPrice()) {
                            gap = lastDay.getMinPrice() - currentDay.getOpenPrice();
                        } else {
                            gap = currentDay.getOpenPrice() - lastDay.getMaxPrice();
                        }
                        if (gap * 10 > currentDay.getOpenPrice()) {
                            log.info(String.format("%s gap is %s price ", company.getSymbol(), currentDay.getDate().toString()) + currentDay.getOpenPrice() + " " + lastDay.getMinPrice() + " " + lastDay.getMaxPrice());
                        }
                    }
                }
                lastDay = currentDay;
            }
        });
    }
}
