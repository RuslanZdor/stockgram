package com.stocker.controller.company;

import static com.stocker.ChartjsUtils.*;

import com.stocker.GraphInterval;
import com.stocker.spring.CompanyDataClient;
import com.stocker.data.Company;
import com.stocker.data.Day;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Controller
public class CompanyController {

    @Autowired
    private CompanyDataClient companyDataClient;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd, yyyy, hh:mm:ss a");
    private static final double volumeMultiplicator = 4.0d;

    private static final int GRAPH_SIZE = 200;

    @RequestMapping("/company/{symbol}/")
    public ModelAndView helloWorld(@PathVariable("symbol") String symbol) {
        log.info("loading " + symbol);
        ModelAndView model = new ModelAndView("welcome");
        Company company = companyDataClient.getCompany(symbol).block();

        if (!Objects.isNull(company)) {
            Set<Day> filtered = filterDays(company, GraphInterval.DAILY);
            model.addObject("title", String.format("'%s'", company.getName()));
            model.addAllObjects(prepareSMAData(filtered));
            model.addAllObjects(prepareMACDData(filtered));
            model.addAllObjects(prepareCompanyData(filtered));
            model.addAllObjects(prepareRSI(filtered));
        }
        return model;
    }

    private Set<Day> filterDays(Company company, GraphInterval inGraphnterval) {
        Set<Day> filtered = new TreeSet<>();
        switch (inGraphnterval) {
            case DAILY:
                filtered = new TreeSet<>(company.getDays());
                break;
            case WEEKLY:
                filtered = company.getDays().stream()
                        .filter(day -> day.getDate().getDayOfWeek() == DayOfWeek.MONDAY)
                        .collect(Collectors.toCollection(TreeSet::new));
                break;
            case MONTHLY:
                filtered = company.getDays().stream()
                        .filter(day -> day.getDate().getDayOfMonth() == 1)
                        .collect(Collectors.toCollection(TreeSet::new));
                break;
        }

        if (filtered.size() > GRAPH_SIZE) {
            filtered = filtered.stream().skip(filtered.size() - GRAPH_SIZE).collect(Collectors.toCollection(TreeSet::new));
        }
        return filtered;
    }

    private Map<String, Object> prepareSMAData(Set<Day> days) {
        Map<String, Object> map = new HashMap<>();
        map.put("fiveSMA", hide(createLineChartData(FIVE_SMA_LABEL, PRICE_Y_AXIS,
                days.stream().map(Day::getFiveSMA).collect(Collectors.toList()))));
        map.put("tenSMA", hide(createLineChartData(TEN_SMA_LABEL, PRICE_Y_AXIS,
                days.stream().map(Day::getTenSMA).collect(Collectors.toList()))));
        map.put("fifteenSMA", hide(createLineChartData(FIFTEEN_SMA_LABEL, PRICE_Y_AXIS,
                days.stream().map(Day::getFifteenSMA).collect(Collectors.toList()))));
        map.put("twentySMA", hide(createLineChartData(TWENTY_SMA_LABEL, PRICE_Y_AXIS,
                days.stream().map(Day::getTwentySMA).collect(Collectors.toList()))));
        map.put("twentyFiveSMA", hide(createLineChartData(TWENTY_FVE_SMA_LABEL, PRICE_Y_AXIS,
                days.stream().map(Day::getTwentyFiveSMA).collect(Collectors.toList()))));
        map.put("thirtySMA", hide(createLineChartData(THIRTY_SMA_LABEL, PRICE_Y_AXIS,
                days.stream().map(Day::getThirtySMA).collect(Collectors.toList()))));
        map.put("fiveEMA", hide(createLineChartData(FIVE_EMA_LABEL, PRICE_Y_AXIS,
                days.stream().map(Day::getFiveEMA).collect(Collectors.toList()))));
        map.put("tenEMA", hide(createLineChartData(TEN_EMA_LABEL, PRICE_Y_AXIS,
                days.stream().map(Day::getTenEMA).collect(Collectors.toList()))));
        map.put("fifteenEMA", hide(createLineChartData(FIFTEEN_EMA_LABEL, PRICE_Y_AXIS,
                days.stream().map(Day::getFifteenEMA).collect(Collectors.toList()))));
        map.put("twentyEMA", hide(createLineChartData(TWENTY_EMA_LABEL, PRICE_Y_AXIS,
                days.stream().map(Day::getTwentyEMA).collect(Collectors.toList()))));
        map.put("twentyFiveEMA", hide(createLineChartData(TWENTY_FIVE_EMA_LABEL, PRICE_Y_AXIS,
                days.stream().map(Day::getTwentyFiveEMA).collect(Collectors.toList()))));
        map.put("thirtyEMA", hide(createLineChartData(THIRTY_EMA_LABEL, PRICE_Y_AXIS,
                days.stream().map(Day::getThirtyEMA).collect(Collectors.toList()))));
        return map;
    }

    private Map<String, Object> prepareCompanyData(Set<Day> days) {
        Map<String, Object> map = new HashMap<>();
        JSONArray array = new JSONArray();
        array.addAll(days.stream().map(day -> DATE_FORMAT.format(day.getDate().atStartOfDay())).collect(Collectors.toList()));
        map.put("labels", array);
        map.put("volumeMax", getMaxDayVolume(days));
        map.put("maxPrice", getMaxPrice(days));
        map.put("minPrice", getMinPrice(days));
        map.put("price", createCandleStickChartData(PRICE_LABEL, PRICE_Y_AXIS, days.stream().collect(Collectors.toList())));
        map.put("volume", addColor(createBarChartData(VOLUME_LABEL, VOLUME_Y_AXIS,
                days.stream().map(Day::getVolume).collect(Collectors.toList())),
                days.stream().map(day -> day.isRising() ? "green" : "red").collect(Collectors.toList())));
        map.put("resistance", createDashChartData(RESISTANCE_LABEL, PRICE_Y_AXIS,
                days.stream().map(day -> day.getResistance() > 0 ? day.getResistance() : null).collect(Collectors.toList())));
        map.put("support", createDashChartData(SUPPORT_LABEL, PRICE_Y_AXIS,
                days.stream().map(day -> day.getSupport() > 0 ? day.getSupport() : null).collect(Collectors.toList())));
        return map;
    }

    private Map<String, Object> prepareMACDData(Set<Day> days) {
        Map<String, Object> map = new HashMap<>();
        map.put("MACDLine", addColor(pointRadius(createLineChartData(MACD_LINE, OSCILLATOR_Y_AXIS,
                days.stream().map(Day::getMACDLine).collect(Collectors.toList())), 0), "blue"));
        map.put("MACDSignal", addColor(pointRadius(createLineChartData(MACD_SIGNAL, OSCILLATOR_Y_AXIS,
                days.stream().map(Day::getMACDSignal).collect(Collectors.toList())), 0), "orange"));
        return map;
    }

    private Map<String, Object> prepareRSI(Set<Day> days) {
        Map<String, Object> map = new HashMap<>();
        map.put("RSI30", addColor(pointRadius(createLineChartData(RSI30_LINE, OSCILLATOR_Y_AXIS,
                days.stream().map(Day::getThirtyRSI).collect(Collectors.toList())), 0), "orange"));
        map.put("RSI5", addColor(pointRadius(createLineChartData(RSI5_LINE, OSCILLATOR_Y_AXIS,
                days.stream().map(Day::getFiveRSI).collect(Collectors.toList())), 0), "blue"));
        return map;
    }

    private double getMaxDayVolume(Set<Day> days) {
        return days.stream().map(Day::getVolume).max(Long::compareTo).orElse(0L) * volumeMultiplicator;
    }

    private double getMaxPrice(Set<Day> days) {
        return days.stream().map(Day::getMaxPrice).max(Double::compareTo).orElse(100.0) * 1.05;
    }

    private double getMinPrice(Set<Day> days) {
        return days.stream().map(Day::getMinPrice).min(Double::compareTo).orElse(0.0) * 0.8;
    }
}