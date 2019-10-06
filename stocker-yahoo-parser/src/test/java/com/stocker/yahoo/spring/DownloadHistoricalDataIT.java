package com.stocker.yahoo.spring;

import org.junit.Test;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.histquotes2.HistoricalDividend;
import yahoofinance.quotes.stock.StockDividend;
import yahoofinance.quotes.stock.StockStats;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class DownloadHistoricalDataIT {

    @Test
    public void allDays() throws IOException {
        Stock companyData = YahooFinance.get("T", Interval.DAILY);
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);

        Calendar yearAgo =  Calendar.getInstance();
        yearAgo.add(Calendar.YEAR, -1);
        List<HistoricalQuote> histQuotes = companyData.getHistory(yearAgo, tomorrow, Interval.DAILY);
        assertFalse(histQuotes.isEmpty());
    }

    @Test
    public void allStats() throws IOException {
        Stock companyData = YahooFinance.get("T", Interval.DAILY);
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);

        Calendar yearAgo =  Calendar.getInstance();
        yearAgo.add(Calendar.YEAR, -1);
        StockStats stats = companyData.getStats(true);
        assertTrue(Objects.nonNull(stats));
    }

    @Test
    public void allDivs() throws IOException {
        Stock companyData = YahooFinance.get("T", Interval.DAILY);
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);

        Calendar yearAgo =  Calendar.getInstance();
        yearAgo.add(Calendar.YEAR, -1);
        StockDividend stats = companyData.getDividend(false);
        assertTrue(Objects.nonNull(stats));
    }

    @Test
    public void allDivHistory() throws IOException {
        Stock companyData = YahooFinance.get("T", Interval.DAILY);
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);

        Calendar yearAgo =  Calendar.getInstance();
        yearAgo.add(Calendar.YEAR, -1);
        List<HistoricalDividend> stats = companyData.getDividendHistory(yearAgo);
        assertTrue(Objects.nonNull(stats));
    }
}