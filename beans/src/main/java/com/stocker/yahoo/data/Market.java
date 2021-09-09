package com.stocker.yahoo.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Market {

    private String symbol;
    private String name;
    private List<Stock> stocks;
    private List<MarketDay> days;
}
