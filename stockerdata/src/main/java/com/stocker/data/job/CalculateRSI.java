package com.stocker.data.job;

import com.stocker.yahoo.data.Company;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class CalculateRSI implements ICalculateJob {

    public void calculate(Company company) {
        Objects.requireNonNull(company, "Company cannot be null");

        calculateGainAndLoss(company);
//        company.getDays().stream().filter(day -> !day.isFinished()).filter(day -> day.getRSI200() == 0.0).forEach(day -> {
//            List<Day> tail = new ArrayList<>(company.getDays().headSet(day));
//            tail.add(day);
//            Collections.reverse(tail);
//            day.setRSI5(tail.isEmpty() ? 50 : calculateRSI(day.getRSIGain5(), day.getRSILoss5()));
//            day.setRSI10(tail.isEmpty() ? 50 : calculateRSI(day.getRSIGain10(), day.getRSILoss10()));
//            day.setRSI20(tail.isEmpty() ? 50 : calculateRSI(day.getRSIGain20(), day.getRSILoss20()));
//            day.setRSI50(tail.isEmpty() ? 50 : calculateRSI(day.getRSIGain50(), day.getRSILoss50()));
//            day.setRSI200(tail.isEmpty() ? 50 : calculateRSI(day.getRSIGain200(), day.getRSILoss200()));
//        });
    }

    private void calculateGainAndLoss(Company company) {
//        for (Day day : company.getDays()) {
//            SortedSet<Day> prevDays = company.getDays().headSet(day);
//            if (!prevDays.isEmpty()) {
//                double lossValue = Math.max(prevDays.last().getPrice() - day.getPrice(), 0);
//                double gainValue = Math.max(day.getPrice() - prevDays.last().getPrice(), 0);
//                day.setRSIGain5(CalculationsUtil.calculateEMA(gainValue, prevDays.last().getRSIGain5(), 5));
//                day.setRSILoss5(CalculationsUtil.calculateEMA(lossValue, prevDays.last().getRSILoss5(), 5));
//                day.setRSIGain10(CalculationsUtil.calculateEMA(gainValue, prevDays.last().getRSIGain10(), 10));
//                day.setRSILoss10(CalculationsUtil.calculateEMA(lossValue, prevDays.last().getRSILoss10(), 10));
//                day.setRSIGain20(CalculationsUtil.calculateEMA(gainValue, prevDays.last().getRSIGain20(), 20));
//                day.setRSILoss20(CalculationsUtil.calculateEMA(lossValue, prevDays.last().getRSILoss20(), 20));
//                day.setRSIGain50(CalculationsUtil.calculateEMA(gainValue, prevDays.last().getRSIGain50(), 50));
//                day.setRSILoss50(CalculationsUtil.calculateEMA(lossValue, prevDays.last().getRSILoss50(), 50));
//                day.setRSIGain200(CalculationsUtil.calculateEMA(gainValue, prevDays.last().getRSIGain200(), 200));
//                day.setRSILoss200(CalculationsUtil.calculateEMA(lossValue, prevDays.last().getRSILoss200(), 200));
//            }
//        }
    }
    private double calculateRSI(double gain, double loss) {
        double rs = 0;
        if (loss > 0) {
            rs = gain / loss;
        }
        return loss > 0 ? (100 - 100 / (1 + rs)) / 100 : 1;
    }

}
