package com.stocker.yahoo.spring;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.exception.NoDayException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
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

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockYahooConfigurationForTest.class})
public class DownloadHistoricalDataIT {

    @Autowired
    private DownloadHistoricalData downloadHistoricalData;

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
    public void download() throws NoDayException {
        Company company = new Company();
        company.setSymbol("AAPL");
        downloadHistoricalData.download(company);
        assertFalse(company.getDividends().isEmpty());
        assertTrue(company.isSp500Flag());
    }

}