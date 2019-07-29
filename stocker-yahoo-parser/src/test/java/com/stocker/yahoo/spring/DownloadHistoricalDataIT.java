package com.stocker.yahoo.spring;

import org.junit.Test;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

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
}