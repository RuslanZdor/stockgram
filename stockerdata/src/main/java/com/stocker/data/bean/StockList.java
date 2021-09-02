package com.stocker.data.bean;

import com.stocker.yahoo.data.Stock;
import lombok.Data;

import java.util.List;

/**
 * twitter request object
 */
@Data
public class StockList {
    private List<Stock> stocks;

    public String toString() {
        if (stocks == null) {
            return "null";
        }
        return stocks.toString();
    }
}
