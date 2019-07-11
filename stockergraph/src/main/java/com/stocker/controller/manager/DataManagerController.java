package com.stocker.controller.manager;

import com.stocker.exception.NoCompanyException;
import com.stocker.exception.NoDayException;
import com.stocker.facade.CompanyCacheService;
import com.stocker.job.company.DownloadHistoricalData;
import com.stocker.runner.CalculateDailyStats;
import com.stocker.runner.CalculateMarketStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller
public class DataManagerController {

    private static Logger logger = LoggerFactory.getLogger(DataManagerController.class);

    @Autowired
    private CompanyCacheService companyService;

    @Autowired
    private CalculateDailyStats calculateDailyStats;

    @Autowired
    private CalculateMarketStats calculateMarketStats;

    @Autowired
    private DownloadHistoricalData downloadHistoricalData;

    @RequestMapping("/manager/reloadStocks")
    public ModelAndView reloadStocks(@RequestParam(value = "year", required = false) int year) {
        companyService.allCompanies().stream().forEach(company -> {
            try {
                downloadHistoricalData.download(company, year);
                companyService.save(company);
            } catch (NoDayException e) {
                logger.error("Cannot download historical data", e);
            }
        });
        return new ModelAndView("welcome");
    }

    @RequestMapping("/manager/updateStocks")
    public ModelAndView updateStocks() throws NoCompanyException {
        companyService.allCompanies().forEach(company -> {
            calculateDailyStats.calculate(company);
            companyService.save(company);
        });
        return new ModelAndView("welcome");
    }

    @RequestMapping("/manager/reloadMarket")
    public ModelAndView reloadMarket() {
        calculateMarketStats.main(null);
        return new ModelAndView("welcome");
    }
}
