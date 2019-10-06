package com.stocker.yahoo.spring;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.CompanyStats;
import com.stocker.yahoo.data.Day;
import com.stocker.yahoo.data.Dividend;
import com.stocker.yahoo.exception.NoDayException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.histquotes2.HistoricalDividend;
import yahoofinance.quotes.stock.StockStats;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
public class DownloadHistoricalData {

    public void download(Company company) throws NoDayException {
        try {
            Stock companyData = YahooFinance.get(company.getSymbol(), Interval.DAILY);
            Calendar tomorrow = Calendar.getInstance();
            tomorrow.add(Calendar.DATE, 1);

            Calendar yearAgo =  Calendar.getInstance();
            if (company.getDays().isEmpty()) {
                yearAgo.add(Calendar.YEAR, -1);
            } else {
                yearAgo.setTimeInMillis(company.getDays().last().getDate()
                        .minus(1, ChronoUnit.DAYS).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
            }

            List<HistoricalQuote> histQuotes = companyData.getHistory(yearAgo, tomorrow, Interval.DAILY);
            if (histQuotes.stream().anyMatch(data -> data.getDate() == null && data.getClose() == null)) {
                throw new NoDayException(String.format("Stock %s has bad data", company.getSymbol()));
            }

            setCompanyStats(company, companyData.getStats(false));
            setDividendHistory(company, companyData.getDividendHistory());

            log.info("size " + histQuotes.size());

            histQuotes.stream()
                    .filter(data -> data.getDate() != null)
                    .filter(data -> data.getClose() != null)
                    .forEach(data -> {
                        Day day = new Day(data.getDate().getTime().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate());
                        day.setPrice(data.getClose().doubleValue());
                        day.setMinPrice(data.getLow().doubleValue());
                        day.setMaxPrice(data.getHigh().doubleValue());
                        day.setOpenPrice(data.getOpen().doubleValue());
                        day.setClosePrice(data.getClose().doubleValue());
                        day.setVolume(data.getVolume());
                        day.setLastUpdate(LocalDateTime.now());
                        company.getDays().removeIf(day1 -> day1.getDate().equals(day.getDate()));
                        company.getDays().add(day);
                    });

            company.getCompanyStats().setLastDayOpenPrice(company.getDays().last().getOpenPrice());
            company.getCompanyStats().setLastDayClosePrice(company.getDays().last().getClosePrice());
            company.getCompanyStats().setLastPrice(company.getDays().last().getPrice());
            company.getCompanyStats().setLastUpdate(company.getDays().last().getLastUpdate());
        } catch (IOException ex) {
            throw new NoDayException(String.format("Stock %s has bad data", company.getSymbol()), ex);
        }
    }

    private void setCompanyStats(Company company, StockStats stats) {
        CompanyStats companyStats = new CompanyStats();
        companyStats.setSharesFloat(stats.getSharesFloat() == null ? 0 : stats.getSharesFloat());
        companyStats.setSharesOutstanding(stats.getSharesOutstanding() == null ? 0 : stats.getSharesOutstanding());
        companyStats.setSharesOwned(stats.getSharesOwned() == null ? 0 : stats.getSharesOwned());
        companyStats.setEps(stats.getEps() == null ? 0 : stats.getEps().doubleValue());
        companyStats.setPe(stats.getPe() == null ? 0 : stats.getPe().doubleValue());
        companyStats.setPeg(stats.getPeg() == null ? 0 : stats.getPeg().doubleValue());
        companyStats.setEpsEstimateCurrentYear(stats.getEpsEstimateCurrentYear() == null ? 0 : stats.getEpsEstimateCurrentYear().doubleValue());
        companyStats.setEpsEstimateNextQuarter(stats.getEpsEstimateNextQuarter() == null ? 0 : stats.getEpsEstimateNextQuarter().doubleValue());
        companyStats.setEpsEstimateNextYear(stats.getEpsEstimateNextYear() == null ? 0 : stats.getEpsEstimateNextYear().doubleValue());
        companyStats.setPriceBook(stats.getPriceBook() == null ? 0 : stats.getPriceBook().doubleValue());
        companyStats.setPriceSales(stats.getPriceSales() == null ? 0 : stats.getPriceSales().doubleValue());
        companyStats.setBookValuePerShare(stats.getBookValuePerShare() == null ? 0 : stats.getBookValuePerShare().doubleValue());
        companyStats.setRevenue(stats.getRevenue() == null ? 0 : stats.getRevenue().doubleValue());
        companyStats.setEBITDA(stats.getEBITDA() == null ? 0 : stats.getEBITDA().doubleValue());
        companyStats.setOneYearTargetPrice(stats.getOneYearTargetPrice() == null ? 0 : stats.getOneYearTargetPrice().doubleValue());
        companyStats.setShortRatio(stats.getShortRatio() == null ? 0 : stats.getShortRatio().doubleValue());
        if (stats.getEarningsAnnouncement() != null) {
            companyStats.setEarningsAnnouncement(LocalDateTime.ofInstant(stats.getEarningsAnnouncement().toInstant(), ZoneId.systemDefault()).toLocalDate());
        }
        company.setCompanyStats(companyStats);
    }

    private void setDividendHistory(Company company, List<HistoricalDividend> stats) {
        company.getDividends().clear();
        stats.stream().forEach(data -> company.getDividends().add(new Dividend(LocalDate.ofInstant(data.getDate().toInstant(), ZoneId.systemDefault()), data.getAdjDividend().doubleValue())));
    }
}
