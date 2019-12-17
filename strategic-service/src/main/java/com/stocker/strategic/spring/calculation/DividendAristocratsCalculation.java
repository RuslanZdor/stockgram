package com.stocker.strategic.spring.calculation;

import com.stocker.strategic.spring.CompanyRepository;
import com.stocker.strategic.spring.StrategyResultRepository;
import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Dividend;
import com.stocker.yahoo.data.StrategyResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DividendAristocratsCalculation {

    private static final String NAME = "DIVIDENDARISTOCRATS";

    private final CompanyRepository companyRepository;
    private final StrategyResultRepository strategyRepository;

    public List<Company> calculate() {

        strategyRepository.deleteAll().block();
        companyRepository.findLongDividendsHistory().subscribe(company -> {

            boolean isAristocrat = company.getCompanyStats().getMarketCap() > 1000000000 && company.isSp500Flag();

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
