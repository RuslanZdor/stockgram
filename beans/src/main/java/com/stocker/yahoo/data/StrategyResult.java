package com.stocker.yahoo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Document(collection = "strategyResults")
public class StrategyResult {

    private String strategyName;
    private String symbol;

}
