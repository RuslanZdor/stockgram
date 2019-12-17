package com.stocker.yahoo.data;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sp500")
public class CompanyFlag {
    private String symbol;
}
