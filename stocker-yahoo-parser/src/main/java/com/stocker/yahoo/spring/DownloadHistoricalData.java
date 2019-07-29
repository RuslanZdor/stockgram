package com.stocker.yahoo.spring;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import com.stocker.yahoo.exception.NoDayException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

@Log4j2
@Component
public class DownloadHistoricalData {

    public void download(Company company) throws NoDayException {
        try {
            Stock companyData = YahooFinance.get(company.getSymbol(), Interval.DAILY);
            Calendar tomorrow = Calendar.getInstance();
            tomorrow.add(Calendar.DATE, 1);

            Calendar yearAgo =  Calendar.getInstance();
            yearAgo.add(Calendar.YEAR, -1);
            List<HistoricalQuote> histQuotes = companyData.getHistory(yearAgo, tomorrow, Interval.DAILY);
            if (histQuotes.stream().anyMatch(data -> data.getDate() == null && data.getClose() == null)) {
                throw new NoDayException(String.format("Stock %s has bad data", company.getSymbol()));
            }
            company.getDays().clear();
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
                        day.setVolume(data.getVolume());
                        company.getDays().add(day);
                    });
        } catch (IOException ex) {
            throw new NoDayException(String.format("Stock %s has bad data", company.getSymbol()), ex);
        }
    }
}
