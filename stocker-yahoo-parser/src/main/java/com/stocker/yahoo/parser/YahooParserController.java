package com.stocker.yahoo.parser;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.exception.NoDayException;
import com.stocker.yahoo.spring.CompanyRepository;
import com.stocker.yahoo.spring.DownloadHistoricalData;
import com.stocker.yahoo.spring.job.CalculateAverageVolume;
import com.stocker.yahoo.spring.job.CalculateEMA;
import com.stocker.yahoo.spring.job.CalculateRSI;
import com.stocker.yahoo.spring.job.CalculateSMA;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.TreeSet;

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


    @GetMapping("/manager/reloadStocks")
    public void reloadStocks() {
        companyRepository.findAll().subscribe(company -> {
            try {
                company.setDays(new TreeSet<>());
                downloadHistoricalData.download(company);
                companyRepository.save(company).subscribe();
            } catch (NoDayException e) {
                log.info(String.format("removing company %s", company.getSymbol()));
                companyRepository.delete(company).subscribe();
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
    public void refreshStock(@PathVariable(name="symbol") String symbol) {
        log.info(String.format("Update all data for company %s", symbol));
        companyRepository.findFirstBySymbol(Mono.just(symbol.toUpperCase())).subscribe(company -> {
            try {
                downloadHistoricalData.download(company);
            } catch (NoDayException e) {
                log.error(e);
            }
            allUpdates(company);
            companyRepository.save(company).subscribe();
        });
    }

    private void allUpdates(Company company) {
        calculateSMA.calculate(company);
        calculateEMA.calculate(company);
        calculateAverageVolume.calculate(company);
        calculateRSI.calculate(company);
    }
}
