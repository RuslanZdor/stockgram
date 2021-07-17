package com.stocker.yahoo.parser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Main Controller for Yahoo parser service
 */
@Slf4j
@AllArgsConstructor
class  YahooParserController {
//
//    private CompanyRepository companyRepository;
//    private DownloadHistoricalData downloadHistoricalData;
//    private ICalculateJob calculateAllFields;
//
////    @GetMapping("/manager/reloadStocks")
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
//    @GetMapping("/manager/refresh/{symbol}/")
//    public Mono<Company> refreshStock(@PathVariable(name="symbol") String symbol) {
//        log.info(String.format("Update all data for company %s", symbol));
//        companyRepository.findFirstBySymbol(Mono.just(symbol.toUpperCase())).subscribe(company -> {
//            try {
//                downloadHistoricalData.download(company);
//                allUpdates(company);
//                companyRepository.save(company).subscribe(company1 -> {
//                    log.info(String.format("id %s", company1.getId()));
//                    log.info(String.format("Saved new value %s", company1.getDays().size()));
//                });
//            } catch (NoDayException e) {
//                log.error("Company hasn't historical information", e);
//            }
//        });
//        return Mono.just(new Company());
//    }
//
//    private void allUpdates(Company company) {
//        log.info(String.format("Calculate new values for %s", company.getName()));
//        calculateAllFields.calculate(company);
//    }
}
