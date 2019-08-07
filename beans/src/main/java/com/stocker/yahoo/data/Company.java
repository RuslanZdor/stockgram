package com.stocker.yahoo.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import yahoofinance.quotes.stock.StockStats;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.NavigableSet;
import java.util.TreeSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Company {

    @Id
    private String id;
    private String name;
    private String symbol;
    private String industry;

    private CompanyStats companyStats;

    private NavigableSet<Day> days = new TreeSet<>();

    /**
     * return day with the same date as in parameter
     * or new day with this date will be created and added to list
     *
     * @param day for search
     * @return founded or created day
     */
    public Day getDay(Day day) {
        Object[] foundDays = days.stream().filter(fDay -> fDay.getDate().equals(day.getDate())).toArray();
        if (foundDays.length > 0) {
            return (Day) foundDays[0];
        }
        return getDays().first();
    }

    /**
     * find max daily volume for this company
     *
     * @return max volume
     */
    public long getMaxDayVolume() {
        long maxVolume = 0;
        for (Day day : getDays()) {
            if (maxVolume < day.getVolume()) {
                maxVolume = day.getVolume();
            }
        }
        return maxVolume;
    }

    public void setCompanyStats(StockStats stats) {
        companyStats = new CompanyStats();
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
    }
}
