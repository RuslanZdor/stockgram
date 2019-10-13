package com.stocker.yahoo.parser;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.exception.NoDayException;
import com.stocker.yahoo.spring.CompanyRepository;
import com.stocker.yahoo.spring.DownloadHistoricalData;
import com.stocker.yahoo.spring.job.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
class YahooParserController {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DownloadHistoricalData downloadHistoricalData;

    @Autowired
    private CalculateSMA calculateSMA;

    @Autowired
    private CalculateEMA calculateEMA;

    @Autowired
    private CalculateAverageVolume calculateAverageVolume;

    @Autowired
    private CalculateRSI calculateRSI;

    @Autowired
    private CalculateMACDLine calculateMACDLine;

    @Autowired
    private CalculateMACDSignal calculateMACDSignal;

    @Autowired
    private CalculateRising calculateRising;

    @Autowired
    private CalculateNextRising calculateNextRising;


    @GetMapping("/manager/reloadStocks")
    public void reloadStocks() {
        companyRepository.findAll().subscribe(company -> {
            try {
                downloadHistoricalData.download(company);
                allUpdates(company);
                companyRepository.save(company).subscribe();
            } catch (NoDayException e) {
                log.info(String.format("removing company %s", company.getSymbol()));
            }
        });
    }

    @GetMapping("/manager/refreshStocks")
    public void refreshStocks() {
        companyRepository.findAll().subscribe(company -> {
            log.info(String.format("Update company %s", company.getSymbol()));
            allUpdates(company);
            companyRepository.save(company).subscribe();
        });
    }

    @GetMapping("/manager/refresh/{symbol}/")
    public Mono<Company> refreshStock(@PathVariable(name="symbol") String symbol) {
        log.info(String.format("Update all data for company %s", symbol));
        companyRepository.findFirstBySymbol(Mono.just(symbol.toUpperCase())).subscribe(company -> {
            try {
                downloadHistoricalData.download(company);
            } catch (NoDayException e) {
                log.error(e);
            }
            allUpdates(company);
            companyRepository.save(company).subscribe(company1 -> {
                log.info(String.format("id %s", company1.getId()));
                log.info(String.format("Saved new value %s", company1.getDays().size()));
            });
        });
        return Mono.just(new Company());
    }

    private void allUpdates(Company company) {
        log.info(String.format("Calculate new values for %s", company.getName()));
        calculateSMA.calculate(company);
        calculateEMA.calculate(company);
        calculateAverageVolume.calculate(company);
        calculateRSI.calculate(company);
        calculateMACDLine.calculate(company);
        calculateMACDSignal.calculate(company);
        calculateRising.calculate(company);
        calculateNextRising.calculate(company);
    }
}
