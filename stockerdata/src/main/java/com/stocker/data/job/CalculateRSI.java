package com.stocker.data.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class CalculateRSI implements ICalculateJob {

    public void calculate(Company company) {
        Objects.requireNonNull(company, "Company cannot be null");

        calculateGainAndLoss(company);
        for (Day day : company.getDays()) {
            day.setRSI5(calculateRSI(day.getRSIGain5(), day.getRSILoss5()));
            day.setRSI10(calculateRSI(day.getRSIGain10(), day.getRSILoss10()));
            day.setRSI20(calculateRSI(day.getRSIGain20(), day.getRSILoss20()));
            day.setRSI50(calculateRSI(day.getRSIGain50(), day.getRSILoss50()));
            day.setRSI200(calculateRSI(day.getRSIGain200(), day.getRSILoss200()));
        }
    }

    private void calculateGainAndLoss(Company company) {
        CalculationsUtil.calculateRSIMovement(company.getDays(), 5,
                (day, prevDayPrice) -> Math.max(day.getPrice() - prevDayPrice, 0), Day::setRSIGain5);
        CalculationsUtil.calculateRSIMovement(company.getDays(), 5,
                (day, prevDayPrice) -> Math.max(prevDayPrice - day.getPrice(), 0), Day::setRSILoss5);
        CalculationsUtil.calculateRSIMovement(company.getDays(), 10,
                (day, prevDayPrice) -> Math.max(day.getPrice() - prevDayPrice, 0), Day::setRSIGain10);
        CalculationsUtil.calculateRSIMovement(company.getDays(), 10,
                (day, prevDayPrice) -> Math.max(prevDayPrice - day.getPrice(), 0), Day::setRSILoss10);
        CalculationsUtil.calculateRSIMovement(company.getDays(), 20,
                (day, prevDayPrice) -> Math.max(day.getPrice() - prevDayPrice, 0), Day::setRSIGain20);
        CalculationsUtil.calculateRSIMovement(company.getDays(), 20,
                (day, prevDayPrice) -> Math.max(prevDayPrice - day.getPrice(), 0), Day::setRSILoss20);
        CalculationsUtil.calculateRSIMovement(company.getDays(), 50,
                (day, prevDayPrice) -> Math.max(day.getPrice() - prevDayPrice, 0), Day::setRSIGain50);
        CalculationsUtil.calculateRSIMovement(company.getDays(), 50,
                (day, prevDayPrice) -> Math.max(prevDayPrice - day.getPrice(), 0), Day::setRSILoss50);
        CalculationsUtil.calculateRSIMovement(company.getDays(), 200,
                (day, prevDayPrice) -> Math.max(day.getPrice() - prevDayPrice, 0), Day::setRSIGain200);
        CalculationsUtil.calculateRSIMovement(company.getDays(), 200,
                (day, prevDayPrice) -> Math.max(prevDayPrice - day.getPrice(), 0), Day::setRSILoss200);
    }

    private double calculateRSI(double gain, double loss) {
        double rs = 0;
        if (loss > 0) {
            rs = gain / loss;
        }
        return loss > 0 ? (100 - 100 / (1 + rs)) / 100 : 1;
    }

}
