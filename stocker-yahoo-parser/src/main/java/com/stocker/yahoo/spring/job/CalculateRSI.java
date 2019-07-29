package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.*;

@Log4j2
@Component
public class CalculateRSI implements ICalculateJob {

    public void calculate(Company company) {
        Objects.requireNonNull(company, "Company cannot be null");

        calculateGainAndLoss(company);
        company.getDays().stream().filter(day -> day.getThirtyRSI() == 0.0).forEach(day -> {
            List<Day> tail = new ArrayList<>(company.getDays().headSet(day));
            tail.add(day);
            Collections.reverse(tail);
            day.setFiveRSI(tail.isEmpty() ? 50 : calculateRSI(day.getFiveRSIGain(), day.getFiveRSILoss()));
            day.setTenRSI(tail.isEmpty() ? 50 : calculateRSI(day.getTenRSIGain(), day.getTenRSILoss()));
            day.setFifteenRSI(tail.isEmpty() ? 50 : calculateRSI(day.getFifteenRSIGain(), day.getFifteenRSILoss()));
            day.setTwentyRSI(tail.isEmpty() ? 50 : calculateRSI(day.getTwentyRSIGain(), day.getTwentyRSILoss()));
            day.setTwentyFiveRSI(tail.isEmpty() ? 50 : calculateRSI(day.getTwentyFiveRSIGain(), day.getTwentyFiveRSILoss()));
            day.setThirtyRSI(tail.isEmpty() ? 50 : calculateRSI(day.getThirtyRSIGain(), day.getThirtyRSILoss()));
        });
    }

    private void calculateGainAndLoss(Company company) {
        for (Day day : company.getDays()) {
            SortedSet<Day> prevDays = company.getDays().headSet(day);
            if (!prevDays.isEmpty()) {
                double lossValue = Math.max(prevDays.last().getPrice() - day.getPrice(), 0);
                double gainValue = Math.max(day.getPrice() - prevDays.last().getPrice(), 0);
                day.setFiveRSIGain(CalculationsUtil.calculateEMA(gainValue, prevDays.last().getFiveRSIGain(), 5));
                day.setFiveRSILoss(CalculationsUtil.calculateEMA(lossValue, prevDays.last().getFiveRSILoss(), 5));
                day.setTenRSIGain(CalculationsUtil.calculateEMA(gainValue, prevDays.last().getTenRSIGain(), 10));
                day.setTenRSILoss(CalculationsUtil.calculateEMA(lossValue, prevDays.last().getTenRSILoss(), 10));
                day.setFifteenRSIGain(CalculationsUtil.calculateEMA(gainValue, prevDays.last().getFifteenRSIGain(), 15));
                day.setFifteenRSILoss(CalculationsUtil.calculateEMA(lossValue, prevDays.last().getFifteenRSILoss(), 15));
                day.setTwentyFiveRSIGain(CalculationsUtil.calculateEMA(gainValue, prevDays.last().getTwentyFiveRSIGain(), 20));
                day.setTwentyFiveRSILoss(CalculationsUtil.calculateEMA(lossValue, prevDays.last().getTwentyFiveRSILoss(), 20));
                day.setTwentyFiveRSIGain(CalculationsUtil.calculateEMA(gainValue, prevDays.last().getTwentyFiveRSIGain(), 25));
                day.setTwentyFiveRSILoss(CalculationsUtil.calculateEMA(lossValue, prevDays.last().getTwentyFiveRSILoss(), 25));
                day.setThirtyRSIGain(CalculationsUtil.calculateEMA(gainValue, prevDays.last().getThirtyRSIGain(), 30));
                day.setThirtyRSILoss(CalculationsUtil.calculateEMA(lossValue, prevDays.last().getThirtyRSILoss(), 30));
            }
        }
    }
    private double calculateRSI(double gain, double loss) {
        double rs = 0;
        if (loss > 0) {
            rs = gain / loss;
        }
        return loss > 0 ? (100 - 100 / (1 + rs)) / 100 : 1;
    }

}
