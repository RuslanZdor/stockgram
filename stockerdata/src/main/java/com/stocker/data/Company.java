package com.stocker.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.SortedSet;
import java.util.TreeSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Company {

    @Id
    private String id;
    private String name;
    private String symbol;
    private String industry;

    private SortedSet<Day> days = new TreeSet<>();

}
