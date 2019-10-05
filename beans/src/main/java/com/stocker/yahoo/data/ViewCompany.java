package com.stocker.yahoo.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.NavigableSet;
import java.util.TreeSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "company")
public class ViewCompany {

    @Id
    private String id;
    private String name;
    private String symbol;
    private String industry;

    private CompanyStats companyStats;
}
