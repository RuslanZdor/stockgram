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
}
