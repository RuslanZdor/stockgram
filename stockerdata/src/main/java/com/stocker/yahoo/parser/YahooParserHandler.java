package com.stocker.yahoo.parser;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Injector;
import com.stocker.data.dao.DayDAO;
import com.stocker.data.module.DIFactory;
import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import com.stocker.yahoo.data.Stock;
import com.stocker.yahoo.exception.NoDayException;
import com.stocker.yahoo.spring.DownloadHistoricalData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Main Controller for Yahoo parser service
 */
@Slf4j
@AllArgsConstructor
public class YahooParserHandler implements RequestHandler<Stock, Company> {

    private final DayDAO dayDAO;
    private final DownloadHistoricalData downloadHistoricalData;

    public YahooParserHandler() {
        this(DIFactory.init());
    }

    public YahooParserHandler(Injector injector) {
        downloadHistoricalData = injector.getInstance(DownloadHistoricalData.class);
        dayDAO = injector.getInstance(DayDAO.class);
    }


//    @GetMapping("/manager/reloadStocks")
//    public void reloadStocks() {
//        companyRepository.findAll().subscribe(company -> {
//            try {
//                downloadHistoricalData.download(company);
//                allUpdates(company);
//                companyRepository.save(company).block();
//            } catch (NoDayException e) {
//                log.error("no historical data", e);
//                log.info(String.format("removing company %s", company.getSymbol()));
//            }
//        });
//    }
//
////    @GetMapping("/manager/refreshStocks")
//    public void refreshStocks() {
//        companyRepository.findAll().subscribe(company -> {
//            log.info(String.format("Update company %s", company.getSymbol()));
//            allUpdates(company);
//            companyRepository.save(company).block();
//        });
//    }
//

    public Company handleRequest(Stock stock, Context context) {
        log.info(String.format("Update all data for company %s", stock.getSymbol()));
        Company company = new Company();
        try {
            Optional<Day> day = dayDAO.findLastStockDay(stock);
            if (day.isPresent()) {
                company = downloadHistoricalData.download(stock, day.get());
            } else {
                company = downloadHistoricalData.download(stock);
            }
//            allUpdates(company);
//            companyRepository.save(company).subscribe(comp -> {
//                log.info(String.format("id %s", comp.getId()));
//                log.info(String.format("Saved new value %s", comp.getDays().size()));
//            });
        } catch (NoDayException e) {
            log.error("Company hasn't historical information", e);
        }
        return company;
    }

    private void allUpdates(Company company) {
        log.info(String.format("Calculate new values for %s", company.getName()));
//        calculateAllFields.calculate(company);
    }
}
