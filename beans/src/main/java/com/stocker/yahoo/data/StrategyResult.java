package com.stocker.yahoo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "strategyResults")
public class StrategyResult {

    private String strategyName;
    private String symbol;

}
