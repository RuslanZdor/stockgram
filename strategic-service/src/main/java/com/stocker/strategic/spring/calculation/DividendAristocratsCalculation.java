package com.stocker.strategic.spring.calculation;

import com.stocker.strategic.spring.CompanyRepository;
import com.stocker.strategic.spring.StrategyResultRepository;
import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Dividend;
import com.stocker.yahoo.data.StrategyResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Log4j2
@Component
public class DividendAristocratsCalculation {

    public static final String NAME = "DIVIDENDARISTOCRATS";

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private StrategyResultRepository strategyRepository;

    public List<Company> calculate() {

        strategyRepository.deleteAll().block();
        companyRepository.findLongDividendsHistory().subscribe(company -> {

            boolean isAristocrat = company.getCompanyStats().getMarketCap() > 1000000000;

            if (isAristocrat) {
                Iterator<Dividend> iterator = company.getDividends().iterator();
                double lastYear = 0;
                for (int i = 0; i < 24; i++) {
                    double newYear = getYearDividends(iterator);
                    if (lastYear > newYear || newYear == 0.0) {
                        isAristocrat = false;
                        break;
                    } else {
                        lastYear = newYear;
                    }
                }
                log.info(company.getSymbol() + " " + isAristocrat);
                if (isAristocrat) {
                    strategyRepository.save(new StrategyResult(NAME, company.getSymbol())).subscribe();
                }
            }
        });

        return Collections.emptyList();
    }

    private double getYearDividends(Iterator<Dividend> dividends) {
        return dividends.next().getValue() + dividends.next().getValue() + dividends.next().getValue() + dividends.next().getValue();
    }

}
