package com.stocker.yahoo.data.market;

import com.stocker.yahoo.data.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Market {

    private String symbol;
    private String name;
    private List<Stock> stocks = new ArrayList<>();
    private List<MarketDay> days = new ArrayList<>();
}
