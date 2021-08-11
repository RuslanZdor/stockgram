package com.stocker.yahoo.parser;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.stocker.data.dao.StockDAO;
import com.stocker.data.module.DAOModule;
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
class YahooParserController implements RequestHandler<Stock, Company> {

    private final StockDAO stockDAO;

    public YahooParserController() {
        this(Guice.createInjector(new DAOModule()));
    }

    public YahooParserController(Injector injector) {
        stockDAO = injector.getInstance(StockDAO.class);
    }


    private DownloadHistoricalData downloadHistoricalData;
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
            Optional<Day> day = stockDAO.findLastStockDay(stock);
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
