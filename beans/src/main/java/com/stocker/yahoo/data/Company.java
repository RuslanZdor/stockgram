package com.stocker.yahoo.data;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.NavigableSet;
import java.util.TreeSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "company")
public class Company {

    @Id
    private String id;
    private String name;
    private String symbol;
    private String industry;
    private boolean sp500Flag;

    private CompanyStats companyStats;

    private NavigableSet<Day> days = new TreeSet<>();
    private NavigableSet<Dividend> dividends = new TreeSet<>();

    /**
     * return day with the same date as in parameter
     * or new day with this date will be created and added to list
     *
     * @param day for search
     * @return founded or created day
     */
    public Day getDay(Day day) {
        return days.stream().filter(fDay -> fDay.getDate().equals(day.getDate())).findFirst().orElse(getDays().first());
    }

    /**
     * find max daily volume for this company
     * zero in case of empty list
     * @return max volume
     */
    public long getMaxDayVolume() {
        return getDays().stream().map(Day::getVolume).max(Long::compare).orElse(0L);
    }
}
