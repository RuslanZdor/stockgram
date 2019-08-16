package com.stocker.yahoo.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}
