package com.stocker.controller.company;

import static com.stocker.ChartjsUtils.*;

import com.stocker.Graphnterval;
import com.stocker.exception.NoDayException;
import com.stocker.facade.CompanyCacheService;
import com.stocker.job.company.DownloadHistoricalData;
import com.stocker.runner.CalculateDailyStats;
import com.stocker.symbol.Company;
import com.stocker.symbol.Day;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CompanyController {

    @Autowired
    private CompanyCacheService cacheFacade;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final double volumeMultiplicator = 4.0d;

    private static final int GRAPH_SIZE = 200;

    @Autowired
    private DownloadHistoricalData downloadHistoricalData;
    @Autowired
    private CompanyCacheService companyService;
    @Autowired
    private CalculateDailyStats calculateDailyStats;

    @RequestMapping("/company/{symbol}/")
    public ModelAndView helloWorld(@PathVariable("symbol") String symbol, @RequestParam(value = "interval", defaultValue = "daily") String interval) {
        Company company;
        ModelAndView model = new ModelAndView("welcome");
        company = cacheFacade.read(symbol.toUpperCase());
        model.addObject("title", "\"company name\"");

        Set<Day> filtred = filterDays(company, Graphnterval.of(interval));
        model.addAllObjects(prepareSMAData(filtred));
        model.addAllObjects(prepareMACDData(filtred));
        model.addAllObjects(prepareCompanyData(filtred, company));
        return model;
    }

    @RequestMapping("/company/{symbol}/reload")
    public ModelAndView helloWorld(@PathVariable("symbol") String symbol, @RequestParam(value = "year", required = false, defaultValue = "0") int year) throws NoDayException {
        Company company = cacheFacade.read(symbol.toUpperCase());
        downloadHistoricalData.download(company, year);
        calculateDailyStats.calculate(company);
        companyService.save(company);
        return new ModelAndView("redirect:/company/" + company.getSymbol() + "/");
    }

    private Set<Day> filterDays(Company company, Graphnterval inGraphnterval) {
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

    private Map<String, Object> prepareCompanyData(Set<Day> days, Company company) {
        Map<String, Object> map = new HashMap<>();
        JSONArray array = new JSONArray();
        array.addAll(days.stream().map(day -> DATE_FORMAT.format(day.getDate())).collect(Collectors.toList()));
        map.put("labels", array);
        map.put("volumeMax", getMaxDayVolume(days) * volumeMultiplicator);
        map.put("price", createLineChartData(PRICE_LABEL, PRICE_Y_AXIS,
                days.stream().map(Day::getPrice).collect(Collectors.toList())));
        map.put("minPrice", hide(createLineChartData(MIN_PRICE_LABEL, PRICE_Y_AXIS,
                days.stream().map(Day::getMinPrice).collect(Collectors.toList()))));
        map.put("maxPrice", hide(createLineChartData(MAX_PRICE_LABEL, PRICE_Y_AXIS,
                days.stream().map(Day::getMaxPrice).collect(Collectors.toList()))));
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

    private long getMaxDayVolume(Set<Day> days) {
        long maxVolume = 0L;
        for (Day day : days) {
            if (maxVolume < day.getVolume()) {
                maxVolume = day.getVolume();
            }
        }

        return maxVolume;
    }
}
