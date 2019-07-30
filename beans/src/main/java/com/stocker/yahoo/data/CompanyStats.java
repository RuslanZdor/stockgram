package com.stocker.yahoo.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Log4j
@NoArgsConstructor
@Document
public class CompanyStats {

    //
    private double marketCap;
    private long sharesFloat;
    private long sharesOutstanding;
    private long sharesOwned;
    private double eps;
    private double pe;
    private double peg;
    private double epsEstimateCurrentYear;
    private double epsEstimateNextQuarter;
    private double epsEstimateNextYear;
    private double priceBook;
    private double priceSales;
    private double bookValuePerShare;
    private double revenue;
    private double EBITDA;
    private double oneYearTargetPrice;
    private double shortRatio;
    private LocalDate earningsAnnouncement;

}
